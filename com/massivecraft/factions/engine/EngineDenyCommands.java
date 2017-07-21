package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EngineDenyCommands extends Engine
{
  public EngineDenyCommands() {}
  
  private static EngineDenyCommands i = new EngineDenyCommands();
  public static EngineDenyCommands get() { return i; }
  





  @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
  public void denyCommands(PlayerCommandPreprocessEvent event)
  {
    Player player = event.getPlayer();
    if (MUtil.isntPlayer(player)) return;
    MPlayer mplayer = MPlayer.get(player);
    

    if (mplayer.isOverriding()) { return;
    }
    
    String command = event.getMessage();
    command = Txt.removeLeadingCommandDust(command);
    command = command.toLowerCase();
    command = command.trim();
    

    if ((mplayer.hasFaction()) && (mplayer.getFaction().getFlag(MFlag.getFlagPermanent())) && (MUtil.containsCommand(command, getdenyCommandsPermanentFactionMember)))
    {
      mplayer.msg("<b>You can't use \"<h>/%s<b>\" as member of a permanent faction.", new Object[] { command });
      event.setCancelled(true);
      return;
    }
    

    PS ps = PS.valueOf(player.getLocation()).getChunk(true);
    Faction factionAtPs = BoardColl.get().getFactionAt(ps);
    Rel factionAtRel = null;
    
    if ((factionAtPs != null) && (!factionAtPs.isNone()))
    {
      factionAtRel = factionAtPs.getRelationTo(mplayer);
    }
    

    if ((getdenyCommandsDistance > -1.0D) && (!getdenyCommandsDistanceBypassIn.contains(factionAtRel)))
    {
      for (Player otherplayer : player.getWorld().getPlayers())
      {
        MPlayer othermplayer = MPlayer.get(otherplayer);
        if (othermplayer != mplayer)
        {
          double distance = player.getLocation().distance(otherplayer.getLocation());
          if (getdenyCommandsDistance <= distance)
          {
            Rel playerRel = mplayer.getRelationTo(othermplayer);
            if (getdenyCommandsDistanceRelation.containsKey(playerRel))
            {
              String desc = playerRel.getDescPlayerOne();
              
              mplayer.msg("<b>You can't use \"<h>/%s<b>\" as there is <h>%s<b> nearby.", new Object[] { command, desc });
              event.setCancelled(true);
              return;
            }
          }
        }
      } }
    if (factionAtRel == null) { return;
    }
    Object deniedCommands = (List)getdenyCommandsTerritoryRelation.get(factionAtRel);
    if (deniedCommands == null) return;
    if (!MUtil.containsCommand(command, (Iterable)deniedCommands)) { return;
    }
    mplayer.msg("<b>You can't use \"<h>/%s<b>\" in %s territory.", new Object[] { command, Txt.getNicedEnum(factionAtRel) });
    event.setCancelled(true);
  }
}
