package com.massivecraft.factions.integration.herochat;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MConf;
import java.util.EnumSet;
import java.util.Set;
import org.bukkit.ChatColor;

public class ChannelFactionsFaction extends ChannelFactionsAbstract
{
  public ChannelFactionsFaction() {}
  
  public static final Set<Rel> targetRelations = EnumSet.of(Rel.MEMBER, Rel.RECRUIT);
  public Set<Rel> getTargetRelations() { return targetRelations; }
  
  public String getName() { return getherochatFactionName; }
  
  public String getNick() { return getherochatFactionNick; }
  public void setNick(String nick) { getherochatFactionNick = nick; }
  
  public String getFormat() { return getherochatFactionFormat; }
  public void setFormat(String format) { getherochatFactionFormat = format; }
  
  public ChatColor getColor() { return getherochatFactionColor; }
  public void setColor(ChatColor color) { getherochatFactionColor = color; }
  
  public int getDistance() { return getherochatFactionDistance; }
  public void setDistance(int distance) { getherochatFactionDistance = distance; }
  
  public void addWorld(String world) { getherochatFactionWorlds.add(world); }
  public Set<String> getWorlds() { return new java.util.HashSet(getherochatFactionWorlds); }
  public void setWorlds(Set<String> worlds) { getherochatFactionWorlds = worlds; }
  
  public boolean isShortcutAllowed() { return getherochatFactionIsShortcutAllowed; }
  public void setShortcutAllowed(boolean shortcutAllowed) { getherochatFactionIsShortcutAllowed = shortcutAllowed; }
  
  public boolean isCrossWorld() { return getherochatFactionCrossWorld; }
  public void setCrossWorld(boolean crossWorld) { getherochatFactionCrossWorld = crossWorld; }
  
  public boolean isMuted() { return getherochatFactionMuted; }
  public void setMuted(boolean value) { getherochatFactionMuted = value; }
}
