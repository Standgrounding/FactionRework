package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockSpreadEvent;

public class EngineFlagFireSpread
  extends Engine
{
  public EngineFlagFireSpread() {}
  
  private static EngineFlagFireSpread i = new EngineFlagFireSpread();
  public static EngineFlagFireSpread get() { return i; }
  





  public void blockFireSpread(Block block, Cancellable cancellable)
  {
    PS ps = PS.valueOf(block);
    Faction faction = BoardColl.get().getFactionAt(ps);
    
    if (faction.getFlag(MFlag.getFlagFirespread())) { return;
    }
    
    cancellable.setCancelled(true);
  }
  

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockFireSpread(BlockIgniteEvent event)
  {
    if ((event.getCause() != BlockIgniteEvent.IgniteCause.SPREAD) && (event.getCause() != BlockIgniteEvent.IgniteCause.LAVA)) { return;
    }
    
    blockFireSpread(event.getBlock(), event);
  }
  


  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockFireSpread(BlockSpreadEvent event)
  {
    if (event.getNewState().getType() != Material.FIRE) { return;
    }
    
    blockFireSpread(event.getBlock(), event);
  }
  



  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockFireSpread(BlockBurnEvent event)
  {
    blockFireSpread(event.getBlock(), event);
  }
}
