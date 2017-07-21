package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinActual;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.MUtil;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;


public class EngineMotd
  extends Engine
{
  public EngineMotd() {}
  
  private static EngineMotd i = new EngineMotd();
  public static EngineMotd get() { return i; }
  





  public static void motd(PlayerJoinEvent event, EventPriority currentPriority)
  {
    Player player = event.getPlayer();
    if (MUtil.isntPlayer(player)) return;
    MPlayer mplayer = MPlayer.get(player);
    Faction faction = mplayer.getFaction();
    

    if (!faction.hasMotd()) { return;
    }
    
    if (currentPriority != getmotdPriority) { return;
    }
    
    if (!MixinActual.get().isActualJoin(event)) { return;
    }
    
    final List<Object> messages = faction.getMotdMessages();
    

    if (getmotdDelayTicks < 0)
    {
      MixinMessage.get().messageOne(player, messages);
    }
    else
    {
      Bukkit.getScheduler().scheduleSyncDelayedTask(Factions.get(), new Runnable()
      {

        public void run()
        {
          MixinMessage.get().messageOne(val$player, messages);
        }
      }, getmotdDelayTicks);
    }
  }
  

  @EventHandler(priority=EventPriority.LOWEST)
  public void motdLowest(PlayerJoinEvent event)
  {
    motd(event, EventPriority.LOWEST);
  }
  

  @EventHandler(priority=EventPriority.LOW)
  public void motdLow(PlayerJoinEvent event)
  {
    motd(event, EventPriority.LOW);
  }
  

  @EventHandler(priority=EventPriority.NORMAL)
  public void motdNormal(PlayerJoinEvent event)
  {
    motd(event, EventPriority.NORMAL);
  }
  

  @EventHandler(priority=EventPriority.HIGH)
  public void motdHigh(PlayerJoinEvent event)
  {
    motd(event, EventPriority.HIGH);
  }
  

  @EventHandler(priority=EventPriority.HIGHEST)
  public void motdHighest(PlayerJoinEvent event)
  {
    motd(event, EventPriority.HIGHEST);
  }
  

  @EventHandler(priority=EventPriority.MONITOR)
  public void motdMonitor(PlayerJoinEvent event)
  {
    motd(event, EventPriority.MONITOR);
  }
}
