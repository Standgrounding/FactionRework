package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsNameChange
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
  
  public String getNewName() { return newName; }
  public void setNewName(String newName) { this.newName = newName; }
  


  private String newName;
  
  public EventFactionsNameChange(CommandSender sender, Faction faction, String newName)
  {
    super(sender);
    this.faction = faction;
    this.newName = newName;
  }
}
