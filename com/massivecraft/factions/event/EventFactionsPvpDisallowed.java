package com.massivecraft.factions.event;

import com.massivecraft.factions.engine.DisallowCause;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;











public class EventFactionsPvpDisallowed
  extends EventFactionsAbstract
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  
  private final Player attacker;
  private final Player defender;
  private final DisallowCause cause;
  private final EntityDamageByEntityEvent event;
  public Player getAttacker() {
    return attacker; }
  public MPlayer getMAttacker() { return attacker == null ? null : MPlayer.get(attacker); }
  

  public Player getDefender() { return defender; }
  public MPlayer getMDefender() { return defender == null ? null : MPlayer.get(defender); }
  
  public DisallowCause getCause() {
    return cause;
  }
  
  public EntityDamageByEntityEvent getEvent() { return event; }
  




  public EventFactionsPvpDisallowed(Player attacker, Player defender, DisallowCause cause, EntityDamageByEntityEvent event)
  {
    this.attacker = attacker;
    this.defender = defender;
    this.cause = cause;
    this.event = event;
  }
}
