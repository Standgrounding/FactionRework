package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsDisband
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
  
  public String getFactionId() { return factionId; }
  


  private final String factionId;
  
  public EventFactionsDisband(CommandSender sender, Faction faction)
  {
    super(sender);
    this.faction = faction;
    factionId = faction.getId();
  }
}
