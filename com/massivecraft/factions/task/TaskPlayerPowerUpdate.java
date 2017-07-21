package com.massivecraft.factions.task;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import com.massivecraft.factions.event.EventFactionsPowerChange.PowerChangeReason;
import com.massivecraft.massivecore.ModuloRepeatTask;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.entity.Player;


public class TaskPlayerPowerUpdate
  extends ModuloRepeatTask
{
  public TaskPlayerPowerUpdate() {}
  
  private static TaskPlayerPowerUpdate i = new TaskPlayerPowerUpdate();
  public static TaskPlayerPowerUpdate get() { return i; }
  






  public long getDelayMillis()
  {
    return (gettaskPlayerPowerUpdateMinutes * 60000.0D);
  }
  

  public void invoke(long now)
  {
    long millis = getDelayMillis();
    MFlag flagPowerGain = MFlag.getFlagPowergain();
    

    for (Player player : MUtil.getOnlinePlayers())
    {

      if ((!MUtil.isntPlayer(player)) && 
        (!player.isDead()))
      {

        Faction faction = BoardColl.get().getFactionAt(PS.valueOf(player));
        if (!faction.getFlag(flagPowerGain)) { return;
        }
        
        if (!getworldsPowerGainEnabled.contains(player)) { return;
        }
        MPlayer mplayer = MPlayer.get(player);
        

        double newPower = mplayer.getPower() + mplayer.getPowerPerHour() * millis / 3600000.0D;
        

        EventFactionsPowerChange event = new EventFactionsPowerChange(null, mplayer, EventFactionsPowerChange.PowerChangeReason.TIME, newPower);
        event.run();
        if (!event.isCancelled())
        {

          newPower = event.getNewPower();
          mplayer.setPower(Double.valueOf(newPower));
        }
      }
    }
  }
}
