package com.massivecraft.factions.event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;




public class EventFactionsRankChange
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
  
  public Rel getNewRank() { return newRank; }
  public void setNewRank(Rel newRole) { newRank = newRole; }
  


  private Rel newRank;
  
  public EventFactionsRankChange(CommandSender sender, MPlayer mplayer, Rel newRank)
  {
    super(sender);
    this.mplayer = mplayer;
    this.newRank = newRank;
  }
}
