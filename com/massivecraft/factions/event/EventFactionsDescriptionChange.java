package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsDescriptionChange
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
  
  public String getNewDescription() { return newDescription; }
  public void setNewDescription(String newDescription) { this.newDescription = newDescription; }
  


  private String newDescription;
  
  public EventFactionsDescriptionChange(CommandSender sender, Faction faction, String newDescription)
  {
    super(sender);
    this.faction = faction;
    this.newDescription = newDescription;
  }
}
