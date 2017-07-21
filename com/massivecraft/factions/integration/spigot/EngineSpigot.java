package com.massivecraft.factions.integration.spigot;

import com.massivecraft.factions.engine.EnginePermBuild;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;




public class EngineSpigot
  extends Engine
{
  public EngineSpigot() {}
  
  private static EngineSpigot i = new EngineSpigot();
  public static EngineSpigot get() { return i; }
  









  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event)
  {
    if (isOffHand(event)) { return;
    }
    
    Player player = event.getPlayer();
    if (MUtil.isntPlayer(player)) return;
    Entity entity = event.getRightClicked();
    boolean verboose = true;
    

    if (entity.getType() != EntityType.ARMOR_STAND) { return;
    }
    
    if (EnginePermBuild.canPlayerUseEntity(player, entity, true)) { return;
    }
    
    event.setCancelled(true);
  }
  








  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(BlockPistonExtendEvent event)
  {
    if (!gethandlePistonProtectionThroughDenyBuild) { return;
    }
    Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf(event.getBlock()));
    
    List<Block> blocks = event.getBlocks();
    

    for (Block block : blocks)
    {

      Block targetBlock = block.getRelative(event.getDirection());
      

      Faction targetFaction = BoardColl.get().getFactionAt(PS.valueOf(targetBlock));
      if ((targetFaction != pistonFaction) && 
      

        (!MPerm.getPermBuild().has(pistonFaction, targetFaction)))
      {
        event.setCancelled(true);
        return;
      }
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(BlockPistonRetractEvent event)
  {
    if (!gethandlePistonProtectionThroughDenyBuild) { return;
    }
    Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf(event.getBlock()));
    
    List<Block> blocks = event.getBlocks();
    

    for (Block block : blocks)
    {

      if ((block.isEmpty()) || (block.isLiquid())) { return;
      }
      
      Faction targetFaction = BoardColl.get().getFactionAt(PS.valueOf(block));
      if ((targetFaction != pistonFaction) && 
      

        (!MPerm.getPermBuild().has(pistonFaction, targetFaction)))
      {
        event.setCancelled(true);
        return;
      }
    }
  }
}
