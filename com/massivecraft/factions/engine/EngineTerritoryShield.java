package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.MUtil;
import java.text.MessageFormat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;



public class EngineTerritoryShield
  extends Engine
{
  public EngineTerritoryShield() {}
  
  private static EngineTerritoryShield i = new EngineTerritoryShield();
  public static EngineTerritoryShield get() { return i; }
  





  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void territoryShield(EntityDamageByEntityEvent event)
  {
    Entity entity = event.getEntity();
    if (MUtil.isntPlayer(entity)) return;
    Player player = (Player)entity;
    MPlayer mplayer = MPlayer.get(player);
    

    Entity attacker = MUtil.getLiableDamager(event);
    if (!(attacker instanceof Player)) { return;
    }
    
    if (!mplayer.hasFaction()) { return;
    }
    
    if (!mplayer.isInOwnTerritory()) { return;
    }
    
    if (getterritoryShieldFactor <= 0.0D) { return;
    }
    
    double factor = 1.0D - getterritoryShieldFactor;
    MUtil.scaleDamage(event, factor);
    

    String perc = MessageFormat.format("{0,number,#%}", new Object[] { Double.valueOf(getterritoryShieldFactor) });
    mplayer.msg("<i>Enemy damage reduced by <rose>%s<i>.", new Object[] { perc });
  }
}
