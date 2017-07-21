package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class EngineLastActivity
  extends Engine
{
  public EngineLastActivity() {}
  
  private static EngineLastActivity i = new EngineLastActivity();
  public static EngineLastActivity get() { return i; }
  




  public static void updateLastActivity(CommandSender sender)
  {
    if (sender == null) throw new RuntimeException("sender");
    if (MUtil.isntSender(sender)) { return;
    }
    MPlayer mplayer = MPlayer.get(sender);
    mplayer.setLastActivityMillis();
  }
  
  public static void updateLastActivitySoon(CommandSender sender)
  {
    if (sender == null) throw new RuntimeException("sender");
    Bukkit.getScheduler().scheduleSyncDelayedTask(Factions.get(), new Runnable()
    {

      public void run()
      {
        EngineLastActivity.updateLastActivity(val$sender);
      }
    });
  }
  




  @EventHandler(priority=EventPriority.LOWEST)
  public void updateLastActivity(PlayerJoinEvent event)
  {
    updateLastActivitySoon(event.getPlayer());
  }
  



  @EventHandler(priority=EventPriority.LOWEST)
  public void updateLastActivity(EventMassiveCorePlayerLeave event)
  {
    updateLastActivity(event.getPlayer());
  }
}
