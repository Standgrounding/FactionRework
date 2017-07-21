package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsInvitedChange
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  

  private final MPlayer mplayer;
  
  public MPlayer getMPlayer()
  {
    return mplayer;
  }
  
  public Faction getFaction() { return faction; }
  

  public boolean isNewInvited() { return newInvited; }
  public void setNewInvited(boolean newInvited) { this.newInvited = newInvited; }
  

  private final Faction faction;
  
  private boolean newInvited;
  public EventFactionsInvitedChange(CommandSender sender, MPlayer mplayer, Faction faction, boolean newInvited)
  {
    super(sender);
    this.mplayer = mplayer;
    this.faction = faction;
    this.newInvited = newInvited;
  }
}
