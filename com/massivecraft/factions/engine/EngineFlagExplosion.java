package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.util.EnumerationUtil;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;


public class EngineFlagExplosion
  extends Engine
{
  public EngineFlagExplosion() {}
  
  private static EngineFlagExplosion i = new EngineFlagExplosion();
  public static EngineFlagExplosion get() { return i; }
  




  protected Set<EntityDamageEvent.DamageCause> DAMAGE_CAUSE_EXPLOSIONS = EnumSet.of(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
  

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockExplosion(HangingBreakEvent event)
  {
    if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) return;
    Entity entity = event.getEntity();
    

    Faction faction = BoardColl.get().getFactionAt(PS.valueOf(entity.getLocation()));
    if (faction.isExplosionsAllowed()) { return;
    }
    
    event.setCancelled(true);
  }
  

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockExplosion(EntityDamageEvent event)
  {
    if (!DAMAGE_CAUSE_EXPLOSIONS.contains(event.getCause())) { return;
    }
    
    if (!EnumerationUtil.isEntityTypeEditOnDamage(event.getEntityType())) { return;
    }
    
    if (BoardColl.get().getFactionAt(PS.valueOf(event.getEntity())).isExplosionsAllowed()) { return;
    }
    
    event.setCancelled(true);
  }
  


  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockExplosion(EntityExplodeEvent event)
  {
    Faction faction = null;
    
    Boolean allowed = Boolean.valueOf(true);
    
    Map<Faction, Boolean> faction2allowed = new HashMap();
    

    Location location = event.getLocation();
    

    faction = BoardColl.get().getFactionAt(PS.valueOf(location));
    allowed = Boolean.valueOf(faction.isExplosionsAllowed());
    if (!allowed.booleanValue())
    {
      event.setCancelled(true);
      return;
    }
    faction2allowed.put(faction, allowed);
    

    Iterator<Block> iter = event.blockList().iterator();
    while (iter.hasNext())
    {
      Block block = (Block)iter.next();
      faction = BoardColl.get().getFactionAt(PS.valueOf(block));
      allowed = (Boolean)faction2allowed.get(faction);
      if (allowed == null)
      {
        allowed = Boolean.valueOf(faction.isExplosionsAllowed());
        faction2allowed.put(faction, allowed);
      }
      
      if (!allowed.booleanValue()) { iter.remove();
      }
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockExplosion(EntityChangeBlockEvent event)
  {
    Entity entity = event.getEntity();
    if (!(entity instanceof Wither)) { return;
    }
    
    PS ps = PS.valueOf(event.getBlock());
    Faction faction = BoardColl.get().getFactionAt(ps);
    
    if (faction.isExplosionsAllowed()) { return;
    }
    
    event.setCancelled(true);
  }
}
