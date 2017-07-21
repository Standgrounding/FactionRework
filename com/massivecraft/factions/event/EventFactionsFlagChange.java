package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsFlagChange
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  

  private final Faction faction;
  
  public Faction getFaction()
  {
    return faction;
  }
  
  public MFlag getFlag() { return flag; }
  

  public boolean isNewValue() { return newValue; }
  public void setNewValue(boolean newValue) { this.newValue = newValue; }
  

  private final MFlag flag;
  
  private boolean newValue;
  public EventFactionsFlagChange(CommandSender sender, Faction faction, MFlag flag, boolean newValue)
  {
    super(sender);
    this.faction = faction;
    this.flag = flag;
    this.newValue = newValue;
  }
}
