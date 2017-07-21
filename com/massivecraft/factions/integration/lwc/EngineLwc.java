package com.massivecraft.factions.integration.lwc;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import com.griefcraft.scripting.ModuleLoader;
import com.griefcraft.sql.PhysDB;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.IdUtil;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.scheduler.BukkitScheduler;


public class EngineLwc
  extends Engine
{
  public EngineLwc() {}
  
  private static EngineLwc i = new EngineLwc();
  public static EngineLwc get() { return i; }
  





  public void setActiveInner(boolean active)
  {
    if (active)
    {
      LWC.getInstance().getModuleLoader().registerModule(Factions.get(), new FactionsLwcModule(Factions.get()));
    }
    else
    {
      LWC.getInstance().getModuleLoader().removeModules(Factions.get());
    }
  }
  





  public void removeProtectionsOnChunkChange(Faction newFaction, EventFactionsChunkChangeType type, Set<PS> chunks)
  {
    Boolean remove = (Boolean)getlwcRemoveOnChange.get(type);
    if (remove == null) return;
    if (!remove.booleanValue()) { return;
    }
    


    for (PS chunk : chunks)
    {
      removeAlienProtectionsAsyncNextTick(chunk, newFaction);
    }
  }
  
  public void removeProtectionsOnChunkChange(Faction newFaction, Map<EventFactionsChunkChangeType, Set<PS>> typeChunks)
  {
    for (Map.Entry<EventFactionsChunkChangeType, Set<PS>> typeChunk : typeChunks.entrySet())
    {
      EventFactionsChunkChangeType type = (EventFactionsChunkChangeType)typeChunk.getKey();
      Set<PS> chunks = (Set)typeChunk.getValue();
      removeProtectionsOnChunkChange(newFaction, type, chunks);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void removeProtectionsOnChunkChange(EventFactionsChunksChange event)
  {
    removeProtectionsOnChunkChange(event.getNewFaction(), event.getTypeChunks());
  }
  







  public static List<Protection> getProtectionsInChunk(PS chunkPs)
  {
    int xmin = chunkPs.getChunkX().intValue() * 16;
    int xmax = xmin + 15;
    
    int ymin = 0;
    int ymax = 255;
    
    int zmin = chunkPs.getChunkZ().intValue() * 16;
    int zmax = zmin + 15;
    
    PhysDB db = LWC.getInstance().getPhysicalDatabase();
    return db.loadProtections(chunkPs.getWorld(), xmin, xmax, 0, 255, zmin, zmax);
  }
  

  public static void removeAlienProtectionsRaw(PS chunkPs, Faction faction)
  {
    List<MPlayer> nonAliens = faction.getMPlayers();
    for (Protection protection : getProtectionsInChunk(chunkPs))
    {

      String ownerName = protection.getOwner();
      String ownerId = IdUtil.getId(ownerName);
      MPlayer owner = MPlayer.get(ownerId);
      if (!nonAliens.contains(owner)) {
        protection.remove();
      }
    }
  }
  
  public static void removeAlienProtectionsAsync(PS chunkPs, final Faction faction) {
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {
        EngineLwc.removeAlienProtectionsRaw(val$chunkPs, faction);
      }
    });
  }
  
  public static void removeAlienProtectionsAsyncNextTick(PS chunkPs, final Faction faction)
  {
    Bukkit.getScheduler().runTaskLater(Factions.get(), new Runnable()
    {


      public void run() {
        EngineLwc.removeAlienProtectionsAsync(val$chunkPs, faction); } }, 0L);
  }
}
