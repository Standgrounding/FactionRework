package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsMembershipChange
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  

  private final MPlayer mplayer;
  
  private final Faction newFaction;
  private final MembershipChangeReason reason;
  public void setCancelled(boolean cancelled)
  {
    if (!reason.isCancellable()) cancelled = false;
    super.setCancelled(cancelled);
  }
  
  public MPlayer getMPlayer() {
    return mplayer;
  }
  
  public Faction getNewFaction() { return newFaction; }
  
  public MembershipChangeReason getReason() {
    return reason;
  }
  



  public EventFactionsMembershipChange(CommandSender sender, MPlayer mplayer, Faction newFaction, MembershipChangeReason reason)
  {
    super(sender);
    this.mplayer = mplayer;
    this.newFaction = newFaction;
    this.reason = reason;
  }
  





  public static enum MembershipChangeReason
  {
    JOIN(true), 
    CREATE(false), 
    
    LEADER(true), 
    
    RANK(true), 
    

    LEAVE(true), 
    
    KICK(true), 
    DISBAND(false);
    
    private final boolean cancellable;
    
    public boolean isCancellable() {
      return cancellable;
    }
    
    private MembershipChangeReason(boolean cancellable) {
      this.cancellable = cancellable;
    }
  }
}
