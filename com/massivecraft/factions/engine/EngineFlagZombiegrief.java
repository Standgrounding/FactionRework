package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityBreakDoorEvent;


public class EngineFlagZombiegrief
  extends Engine
{
  public EngineFlagZombiegrief() {}
  
  private static EngineFlagZombiegrief i = new EngineFlagZombiegrief();
  public static EngineFlagZombiegrief get() { return i; }
  





  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void denyZombieGrief(EntityBreakDoorEvent event)
  {
    Entity entity = event.getEntity();
    if (!(entity instanceof Zombie)) { return;
    }
    
    PS ps = PS.valueOf(event.getBlock());
    Faction faction = BoardColl.get().getFactionAt(ps);
    if (faction.getFlag(MFlag.getFlagZombiegrief())) { return;
    }
    
    event.setCancelled(true);
  }
}
