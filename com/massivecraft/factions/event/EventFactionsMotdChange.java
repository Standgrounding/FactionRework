package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsMotdChange
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
  
  public String getNewMotd() { return newMotd; }
  public void setNewMotd(String newMotd) { this.newMotd = newMotd; }
  


  private String newMotd;
  
  public EventFactionsMotdChange(CommandSender sender, Faction faction, String newMotd)
  {
    super(sender);
    this.faction = faction;
    this.newMotd = newMotd;
  }
}
