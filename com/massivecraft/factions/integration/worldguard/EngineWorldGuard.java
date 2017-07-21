package com.massivecraft.factions.integration.worldguard;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineWorldGuard extends Engine
{
  public EngineWorldGuard() {}
  
  private static EngineWorldGuard i = new EngineWorldGuard();
  public static EngineWorldGuard get() { return i; }
  





  protected WorldGuardPlugin worldGuard;
  




  public void setActiveInner(boolean active)
  {
    if (active)
    {
      worldGuard = WGBukkit.getPlugin();
    }
    else
    {
      worldGuard = null;
    }
  }
  





  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void checkForRegion(EventFactionsChunksChange event)
  {
    if (!getworldguardCheckEnabled) { return;
    }
    
    if (event.getNewFaction().getFlag("permanent")) { return;
    }
    MPlayer mplayer = event.getMPlayer();
    Player player = mplayer.getPlayer();
    

    if (player == null) { return;
    }
    LocalPlayer wrapperPlayer = worldGuard.wrapPlayer(player);
    
    if (!getworldguardCheckWorldsEnabled.contains(player)) { return;
    }
    
    if (mplayer.isOverriding()) { return;
    }
    for (Iterator localIterator1 = event.getChunks().iterator(); localIterator1.hasNext();) { chunk = (PS)localIterator1.next();
      

      List<ProtectedRegion> regions = getProtectedRegionsFor(chunk);
      

      if ((regions != null) && (!regions.isEmpty()))
      {
        for (ProtectedRegion region : regions)
        {

          if ((!(region instanceof GlobalProtectedRegion)) && (!region.isMember(wrapperPlayer)) && 
          

            (!player.hasPermission("factions.allowregionclaim." + region.getId())))
          {

            mplayer.msg("<b>You cannot claim the chunk at %s, %s as there is a region in the way.", new Object[] { chunk.getChunkX(), chunk.getChunkZ() });
            
            event.setCancelled(true); return;
          }
        }
      }
    }
    

    PS chunk;
  }
  

  public List<ProtectedRegion> getProtectedRegionsFor(PS ps)
  {
    int minChunkX = ps.getChunkX().intValue() << 4;
    int minChunkZ = ps.getChunkZ().intValue() << 4;
    int maxChunkX = minChunkX + 15;
    int maxChunkZ = minChunkZ + 15;
    
    int worldHeight = ps.asBukkitWorld().getMaxHeight();
    
    BlockVector minChunk = new BlockVector(minChunkX, 0, minChunkZ);
    BlockVector maxChunk = new BlockVector(maxChunkX, worldHeight, maxChunkZ);
    
    RegionManager regionManager = worldGuard.getRegionManager(ps.asBukkitWorld());
    
    String regionName = "factions_temp";
    ProtectedCuboidRegion region = new ProtectedCuboidRegion(regionName, minChunk, maxChunk);
    
    Map<String, ProtectedRegion> regionMap = regionManager.getRegions();
    List<ProtectedRegion> regionList = new ArrayList(regionMap.values());
    

    List<ProtectedRegion> overlapRegions = region.getIntersectingRegions(regionList);
    
    return overlapRegions;
  }
}
