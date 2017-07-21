package com.massivecraft.factions.event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;





public class EventFactionsRelationChange
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
  
  public Faction getOtherFaction() { return otherFaction; }
  

  public Rel getNewRelation() { return newRelation; }
  public void setNewRelation(Rel newRelation) { this.newRelation = newRelation; }
  

  private final Faction otherFaction;
  
  private Rel newRelation;
  public EventFactionsRelationChange(CommandSender sender, Faction faction, Faction otherFaction, Rel newRelation)
  {
    super(sender);
    this.faction = faction;
    this.otherFaction = otherFaction;
    this.newRelation = newRelation;
  }
}
