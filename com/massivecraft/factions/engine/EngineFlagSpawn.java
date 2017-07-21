package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.util.EnumerationUtil;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.collections.BackstringSet;
import com.massivecraft.massivecore.ps.PS;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;



public class EngineFlagSpawn
  extends Engine
{
  public EngineFlagSpawn() {}
  
  private static EngineFlagSpawn i = new EngineFlagSpawn();
  public static EngineFlagSpawn get() { return i; }
  




  public static final Set<CreatureSpawnEvent.SpawnReason> NATURAL_SPAWN_REASONS = new BackstringSet(CreatureSpawnEvent.SpawnReason.class, new Object[] { "NATURAL", "JOCKEY", "CHUNK_GEN", "OCELOT_BABY", "NETHER_PORTAL", "MOUNT", "REINFORCEMENTS", "VILLAGE_INVASION" });
  














  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockMonstersAndAnimals(CreatureSpawnEvent event)
  {
    if (!NATURAL_SPAWN_REASONS.contains(event.getSpawnReason())) { return;
    }
    
    Location location = event.getLocation();
    if (location == null) return;
    PS ps = PS.valueOf(location);
    

    Faction faction = BoardColl.get().getFactionAt(ps);
    if (faction == null) { return;
    }
    
    EntityType type = event.getEntityType();
    

    if (canSpawn(faction, type)) { return;
    }
    
    event.setCancelled(true);
  }
  
  public static boolean canSpawn(Faction faction, EntityType type)
  {
    if (EnumerationUtil.isEntityTypeMonster(type))
    {

      return faction.getFlag(MFlag.getFlagMonsters());
    }
    if (EnumerationUtil.isEntityTypeAnimal(type))
    {

      return faction.getFlag(MFlag.getFlagAnimals());
    }
    


    return true;
  }
}
