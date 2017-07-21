package com.massivecraft.factions.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsCreate
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  

  private final String factionId;
  
  public final String getFactionId()
  {
    return factionId;
  }
  
  public final String getFactionName() { return factionName; }
  


  private final String factionName;
  
  public EventFactionsCreate(CommandSender sender, String factionId, String factionName)
  {
    super(sender);
    this.factionId = factionId;
    this.factionName = factionName;
  }
}
