package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;


public class EngineFlagEndergrief
  extends Engine
{
  public EngineFlagEndergrief() {}
  
  private static EngineFlagEndergrief i = new EngineFlagEndergrief();
  public static EngineFlagEndergrief get() { return i; }
  





  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockEndergrief(EntityChangeBlockEvent event)
  {
    Entity entity = event.getEntity();
    if (!(entity instanceof Enderman)) { return;
    }
    
    PS ps = PS.valueOf(event.getBlock());
    Faction faction = BoardColl.get().getFactionAt(ps);
    if (faction.getFlag(MFlag.getFlagEndergrief())) { return;
    }
    
    event.setCancelled(true);
  }
}
