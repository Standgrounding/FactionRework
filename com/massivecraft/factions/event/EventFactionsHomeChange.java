package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsHomeChange
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
  
  public PS getNewHome() { return newHome; }
  public void setNewHome(PS newHome) { this.newHome = newHome; }
  


  private PS newHome;
  
  public EventFactionsHomeChange(CommandSender sender, Faction faction, PS newHome)
  {
    super(sender);
    this.faction = faction;
    this.newHome = newHome;
  }
}
