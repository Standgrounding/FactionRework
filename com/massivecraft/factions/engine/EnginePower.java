package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import com.massivecraft.factions.event.EventFactionsPowerChange.PowerChangeReason;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EnginePower
  extends Engine
{
  public EnginePower() {}
  
  private static EnginePower i = new EnginePower();
  public static EnginePower get() { return i; }
  





  @EventHandler(priority=EventPriority.NORMAL)
  public void powerLossOnDeath(PlayerDeathEvent event)
  {
    Player player = event.getEntity();
    if (MUtil.isntPlayer(player)) { return;
    }
    

    if (PlayerUtil.isDuplicateDeathEvent(event)) { return;
    }
    MPlayer mplayer = MPlayer.get(player);
    

    Faction faction = BoardColl.get().getFactionAt(PS.valueOf(player.getLocation()));
    
    if (!faction.getFlag(MFlag.getFlagPowerloss()))
    {
      mplayer.msg("<i>You didn't lose any power since the territory you died in works that way.");
      return;
    }
    
    if (!getworldsPowerLossEnabled.contains(player.getWorld()))
    {
      mplayer.msg("<i>You didn't lose any power due to the world you died in.");
      return;
    }
    

    double newPower = mplayer.getPower() + mplayer.getPowerPerDeath();
    
    EventFactionsPowerChange powerChangeEvent = new EventFactionsPowerChange(null, mplayer, EventFactionsPowerChange.PowerChangeReason.DEATH, newPower);
    powerChangeEvent.run();
    if (powerChangeEvent.isCancelled()) return;
    newPower = powerChangeEvent.getNewPower();
    
    mplayer.setPower(Double.valueOf(newPower));
    


    mplayer.msg("<i>Your power is now <h>%.2f / %.2f", new Object[] { Double.valueOf(newPower), Double.valueOf(mplayer.getPowerMax()) });
  }
}
