package com.massivecraft.factions.integration.herochat;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.ChannelManager;
import com.dthielke.herochat.ChannelStorage;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.ChatterManager;
import com.dthielke.herochat.Herochat;
import com.dthielke.herochat.MessageFormatSupplier;
import com.dthielke.herochat.MessageNotFoundException;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public abstract class ChannelFactionsAbstract implements Channel
{
  private static final Pattern msgPattern = Pattern.compile("(.*)<(.*)%1\\$s(.*)> %2\\$s");
  private final ChannelStorage storage = Herochat.getChannelManager().getStorage();
  private final MessageFormatSupplier formatSupplier = Herochat.getChannelManager();
  
  public ChannelFactionsAbstract() {}
  
  public boolean addMember(Chatter chatter, boolean announce, boolean flagUpdate) {
    if (chatter.hasChannel(this)) { return false;
    }
    if ((announce) && (isVerbose()))
    {
      try
      {
        announce(Herochat.getMessage("channel_join").replace("$1", chatter.getPlayer().getDisplayName()));
      }
      catch (MessageNotFoundException e)
      {
        Herochat.severe("Messages.properties is missing: channel_join");
      }
    }
    
    chatter.addChannel(this, announce, flagUpdate);
    return true;
  }
  

  public boolean kickMember(Chatter chatter, boolean announce)
  {
    if (!chatter.hasChannel(this)) return false;
    removeMember(chatter, false, true);
    
    if (announce)
    {
      try
      {
        announce(Herochat.getMessage("channel_kick").replace("$1", chatter.getPlayer().getDisplayName()));
      }
      catch (MessageNotFoundException e)
      {
        Herochat.severe("Messages.properties is missing: channel_kick");
      }
    }
    
    return true;
  }
  

  public boolean removeMember(Chatter chatter, boolean announce, boolean flagUpdate)
  {
    if (!chatter.hasChannel(this)) return false;
    chatter.removeChannel(this, announce, flagUpdate);
    
    if ((announce) && (isVerbose()))
    {
      try
      {
        announce(Herochat.getMessage("channel_leave").replace("$1", chatter.getPlayer().getDisplayName()));
      }
      catch (MessageNotFoundException e)
      {
        Herochat.severe("Messages.properties is missing: channel_leave");
      }
    }
    
    return true;
  }
  

  public Set<Chatter> getMembers()
  {
    Set<Chatter> ret = new HashSet();
    for (Chatter chatter : Herochat.getChatterManager().getChatters())
    {
      if (chatter.hasChannel(this)) ret.add(chatter);
    }
    return ret;
  }
  

  public void announce(String message)
  {
    String colorized = message.replaceAll("(?i)&([a-fklmno0-9])", "§$1");
    message = applyFormat(getFormatSupplier().getAnnounceFormat(), "").replace("%2$s", colorized);
    for (Chatter member : getMembers())
    {
      Player player = member.getPlayer();
      MixinMessage.get().messageOne(player, message);
    }
    Herochat.logChat(ChatColor.stripColor(message));
  }
  

  public String applyFormat(String format, String originalFormat)
  {
    format = format.replace("{default}", getFormatSupplier().getStandardFormat());
    format = format.replace("{name}", getName());
    format = format.replace("{nick}", getNick());
    format = format.replace("{color}", getColor().toString());
    format = format.replace("{msg}", "%2$s");
    
    Matcher matcher = msgPattern.matcher(originalFormat);
    if ((matcher.matches()) && (matcher.groupCount() == 3))
    {
      format = format.replace("{sender}", matcher.group(1) + matcher.group(2) + "%1$s" + matcher.group(3));
    }
    else
    {
      format = format.replace("{sender}", "%1$s");
    }
    
    format = format.replaceAll("(?i)&([a-fklmnor0-9])", "§$1");
    return format;
  }
  

  public String applyFormat(String format, String originalFormat, Player sender)
  {
    format = applyFormat(format, originalFormat);
    format = format.replace("{plainsender}", sender.getName());
    format = format.replace("{world}", sender.getWorld().getName());
    Chat chat = Herochat.getChatService();
    if (chat != null)
    {
      try
      {
        String prefix = chat.getPlayerPrefix(sender);
        if ((prefix == null) || (prefix == ""))
        {
          prefix = chat.getPlayerPrefix((String)null, sender.getName());
        }
        String suffix = chat.getPlayerSuffix(sender);
        if ((suffix == null) || (suffix == ""))
        {
          suffix = chat.getPlayerSuffix((String)null, sender.getName());
        }
        String group = chat.getPrimaryGroup(sender);
        String groupPrefix = group == null ? "" : chat.getGroupPrefix(sender.getWorld(), group);
        if ((group != null) && ((groupPrefix == null) || (groupPrefix == "")))
        {
          groupPrefix = chat.getGroupPrefix((String)null, group);
        }
        String groupSuffix = group == null ? "" : chat.getGroupSuffix(sender.getWorld(), group);
        if ((group != null) && ((groupSuffix == null) || (groupSuffix == "")))
        {
          groupSuffix = chat.getGroupSuffix((String)null, group);
        }
        format = format.replace("{prefix}", prefix == null ? "" : prefix.replace("%", "%%"));
        format = format.replace("{suffix}", suffix == null ? "" : suffix.replace("%", "%%"));
        format = format.replace("{group}", group == null ? "" : group.replace("%", "%%"));
        format = format.replace("{groupprefix}", groupPrefix == null ? "" : groupPrefix.replace("%", "%%"));
        format = format.replace("{groupsuffix}", groupSuffix == null ? "" : groupSuffix.replace("%", "%%"));

      }
      catch (UnsupportedOperationException localUnsupportedOperationException) {}

    }
    else
    {

      format = format.replace("{prefix}", "");
      format = format.replace("{suffix}", "");
      format = format.replace("{group}", "");
      format = format.replace("{groupprefix}", "");
      format = format.replace("{groupsuffix}", "");
    }
    format = format.replaceAll("(?i)&([a-fklmno0-9])", "§$1");
    return format;
  }
  

  public void emote(Chatter sender, String message)
  {
    message = applyFormat(getFormatSupplier().getEmoteFormat(), "").replace("%2$s", message);
    
    Set<Player> recipients = new HashSet();
    for (Chatter member : getMembers())
    {
      recipients.add(member.getPlayer());
    }
    
    trimRecipients(recipients, sender);
    
    for (Player recipient : recipients)
    {
      MixinMessage.get().messageOne(recipient, message);
    }
  }
  

  public boolean isMuted(String name)
  {
    if (isMuted()) return true;
    return getMutes().contains(name.toLowerCase());
  }
  
  public abstract Set<Rel> getTargetRelations();
  
  public Set<Player> getRecipients(Player sender)
  {
    Set<Player> ret = new HashSet();
    
    MPlayer fsender = MPlayer.get(sender);
    Faction faction = fsender.getFaction();
    
    for (Player player : MUtil.getOnlinePlayers())
    {
      MPlayer frecipient = MPlayer.get(player);
      if (getTargetRelations().contains(faction.getRelationTo(frecipient))) {
        ret.add(player);
      }
    }
    return ret;
  }
  

  public void processChat(ChannelChatEvent event)
  {
    Player player = event.getSender().getPlayer();
    
    String format = applyFormat(event.getFormat(), event.getBukkitFormat(), player);
    
    Chatter sender = Herochat.getChatterManager().getChatter(player);
    

    Set<Player> recipients = getRecipients(player);
    
    trimRecipients(recipients, sender);
    String msg = String.format(format, new Object[] { player.getDisplayName(), event.getMessage() });
    for (Player recipient : recipients)
    {
      MixinMessage.get().messageOne(recipient, msg);
    }
    
    org.bukkit.Bukkit.getPluginManager().callEvent(new com.dthielke.herochat.ChatCompleteEvent(sender, this, msg));
    Herochat.logChat(msg);
  }
  
  public boolean isMessageHeard(Set<Player> recipients, Chatter sender)
  {
    if (!isLocal()) { return true;
    }
    Player senderPlayer = sender.getPlayer();
    for (Player recipient : recipients)
    {
      if ((!recipient.equals(senderPlayer)) && 
        (!recipient.hasPermission("herochat.admin.stealth"))) {
        return true;
      }
    }
    return false;
  }
  
  public void trimRecipients(Set<Player> recipients, Chatter sender)
  {
    World world = sender.getPlayer().getWorld();
    
    Set<Chatter> members = getMembers();
    Iterator<Player> iterator = recipients.iterator();
    while (iterator.hasNext())
    {
      Chatter recipient = Herochat.getChatterManager().getChatter((Player)iterator.next());
      if (recipient != null) {
        World recipientWorld = recipient.getPlayer().getWorld();
        
        if (!members.contains(recipient)) {
          iterator.remove();
        } else if ((isLocal()) && (!sender.isInRange(recipient, getDistance()))) {
          iterator.remove();
        } else if (!hasWorld(recipientWorld)) {
          iterator.remove();
        } else if (recipient.isIgnoring(sender)) {
          iterator.remove();
        } else if ((!isCrossWorld()) && (!world.equals(recipientWorld)))
          iterator.remove();
      }
    }
  }
  
  public boolean equals(Object other) {
    if (other == this) return true;
    if (other == null) return false;
    if (!(other instanceof Channel)) return false;
    Channel channel = (Channel)other;
    return (getName().equalsIgnoreCase(channel.getName())) || (getName().equalsIgnoreCase(channel.getNick()));
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = prime * result + (getName() == null ? 0 : getName().toLowerCase().hashCode());
    result = prime * result + (getNick() == null ? 0 : getNick().toLowerCase().hashCode());
    return result;
  }
  
  public boolean isTransient() { return false; }
  public String getPassword() { return ""; }
  public void setPassword(String password) {}
  public boolean isVerbose() { return false; }
  public void setVerbose(boolean verbose) {}
  public boolean isHidden() { return false; }
  public boolean isLocal() { return getDistance() != 0; }
  public void attachStorage(ChannelStorage storage) {}
  public boolean banMember(Chatter chatter, boolean announce) { return false; }
  public Set<String> getBans() { return Collections.emptySet(); }
  public Set<String> getModerators() { return Collections.emptySet(); }
  public Set<String> getMutes() { return Collections.emptySet(); }
  public ChannelStorage getStorage() { return storage; }
  public boolean hasWorld(String world) { return (getWorlds().isEmpty()) || (getWorlds().contains(world)); }
  public boolean hasWorld(World world) { return hasWorld(world.getName()); }
  public boolean isBanned(String name) { return getBans().contains(name.toLowerCase()); }
  public boolean isMember(Chatter chatter) { return getMembers().contains(chatter); }
  public boolean isModerator(String name) { return getModerators().contains(name.toLowerCase()); }
  
  public void onFocusGain(Chatter chatter) {}
  
  public void onFocusLoss(Chatter chatter) {}
  public void removeWorld(String world) { getWorlds().remove(world); }
  
  public void setBanned(String name, boolean banned) {}
  
  public void setBans(Set<String> bans) {}
  
  public void setModerator(String name, boolean moderator) {}
  
  public void setModerators(Set<String> moderators) {}
  public void setMuted(String name, boolean muted) {}
  public void setMutes(Set<String> mutes) {}
  public MessageFormatSupplier getFormatSupplier() { return formatSupplier; }
  


  public void sendRawMessage(String rawMessage)
  {
    for (Chatter member : getMembers())
    {
      Player player = member.getPlayer();
      MixinMessage.get().messageOne(player, rawMessage);
    }
  }
}
