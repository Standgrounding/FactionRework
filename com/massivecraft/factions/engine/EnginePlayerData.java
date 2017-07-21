package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.Engine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;

public class EnginePlayerData
  extends Engine
{
  public EnginePlayerData() {}
  
  private static EnginePlayerData i = new EnginePlayerData();
  public static EnginePlayerData get() { return i; }
  





  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onPlayerKick(PlayerKickEvent event)
  {
    Player player = event.getPlayer();
    


    if (!player.isBanned()) { return;
    }
    
    if (!getremovePlayerWhenBanned) { return;
    }
    
    MPlayer mplayer = (MPlayer)MPlayerColl.get().get(player, false);
    if (mplayer == null) { return;
    }
    if (mplayer.getRole() == Rel.LEADER)
    {
      mplayer.getFaction().promoteNewLeader();
    }
    
    mplayer.leave();
    mplayer.detach();
  }
}
