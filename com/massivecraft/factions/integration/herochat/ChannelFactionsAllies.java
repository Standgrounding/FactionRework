package com.massivecraft.factions.integration.herochat;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MConf;
import java.util.Set;
import org.bukkit.ChatColor;

public class ChannelFactionsAllies extends ChannelFactionsAbstract
{
  public ChannelFactionsAllies() {}
  
  public static final Set<Rel> targetRelations = java.util.EnumSet.of(Rel.MEMBER, Rel.RECRUIT, Rel.ALLY);
  public Set<Rel> getTargetRelations() { return targetRelations; }
  
  public String getName() { return getherochatAlliesName; }
  
  public String getNick() { return getherochatAlliesNick; }
  public void setNick(String nick) { getherochatAlliesNick = nick; }
  
  public String getFormat() { return getherochatAlliesFormat; }
  public void setFormat(String format) { getherochatAlliesFormat = format; }
  
  public ChatColor getColor() { return getherochatAlliesColor; }
  public void setColor(ChatColor color) { getherochatAlliesColor = color; }
  
  public int getDistance() { return getherochatAlliesDistance; }
  public void setDistance(int distance) { getherochatAlliesDistance = distance; }
  
  public void addWorld(String world) { getherochatAlliesWorlds.add(world); }
  public Set<String> getWorlds() { return getherochatAlliesWorlds; }
  public void setWorlds(Set<String> worlds) { getherochatAlliesWorlds = worlds; }
  
  public boolean isShortcutAllowed() { return getherochatAlliesIsShortcutAllowed; }
  public void setShortcutAllowed(boolean shortcutAllowed) { getherochatAlliesIsShortcutAllowed = shortcutAllowed; }
  
  public boolean isCrossWorld() { return getherochatAlliesCrossWorld; }
  public void setCrossWorld(boolean crossWorld) { getherochatAlliesCrossWorld = crossWorld; }
  
  public boolean isMuted() { return getherochatAlliesMuted; }
  public void setMuted(boolean value) { getherochatAlliesMuted = value; }
}
