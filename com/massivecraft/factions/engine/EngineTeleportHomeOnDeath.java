package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;


public class EngineTeleportHomeOnDeath
  extends Engine
{
  public EngineTeleportHomeOnDeath() {}
  
  private static EngineTeleportHomeOnDeath i = new EngineTeleportHomeOnDeath();
  public static EngineTeleportHomeOnDeath get() { return i; }
  





  public void teleportToHomeOnDeath(PlayerRespawnEvent event, EventPriority priority)
  {
    Player player = event.getPlayer();
    if (MUtil.isntPlayer(player)) return;
    MPlayer mplayer = MPlayer.get(player);
    

    if (!gethomesEnabled) return;
    if (!gethomesTeleportToOnDeathActive) return;
    if (gethomesTeleportToOnDeathPriority != priority) { return;
    }
    
    Faction faction = mplayer.getFaction();
    if (faction.isNone()) { return;
    }
    
    PS home = faction.getHome();
    if (home == null) { return;
    }
    
    Location respawnLocation = null;
    try
    {
      respawnLocation = home.asBukkitLocation(true);

    }
    catch (Exception e)
    {
      return;
    }
    

    event.setRespawnLocation(respawnLocation);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void teleportToHomeOnDeathLowest(PlayerRespawnEvent event)
  {
    teleportToHomeOnDeath(event, EventPriority.LOWEST);
  }
  
  @EventHandler(priority=EventPriority.LOW)
  public void teleportToHomeOnDeathLow(PlayerRespawnEvent event)
  {
    teleportToHomeOnDeath(event, EventPriority.LOW);
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void teleportToHomeOnDeathNormal(PlayerRespawnEvent event)
  {
    teleportToHomeOnDeath(event, EventPriority.NORMAL);
  }
  
  @EventHandler(priority=EventPriority.HIGH)
  public void teleportToHomeOnDeathHigh(PlayerRespawnEvent event)
  {
    teleportToHomeOnDeath(event, EventPriority.HIGH);
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void teleportToHomeOnDeathHighest(PlayerRespawnEvent event)
  {
    teleportToHomeOnDeath(event, EventPriority.HIGHEST);
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void teleportToHomeOnDeathMonitor(PlayerRespawnEvent event)
  {
    teleportToHomeOnDeath(event, EventPriority.MONITOR);
  }
}
