package com.massivecraft.factions.event;

import org.bukkit.event.HandlerList;





public class EventFactionsCreatePerms
  extends EventFactionsAbstract
{
  public EventFactionsCreatePerms() {}
  
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
}
