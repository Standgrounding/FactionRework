package com.massivecraft.factions.integration.V19;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.engine.EngineCanCombatHappen;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.util.MUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;

public class EngineV19 extends Engine
{
  public EngineV19() {}
  
  private static EngineV19 i = new EngineV19();
  public static EngineV19 get() { return i; }
  

  public MassivePlugin getActivePlugin()
  {
    return Factions.get();
  }
  






  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void canCombatDamageHappen(AreaEffectCloudApplyEvent event)
  {
    if (!MUtil.isHarmfulPotion(event.getEntity().getBasePotionData().getType().getEffectType())) { return;
    }
    ProjectileSource projectileSource = event.getEntity().getSource();
    if (!(projectileSource instanceof Entity)) { return;
    }
    Entity thrower = (Entity)projectileSource;
    

    List<LivingEntity> affectedList = new ArrayList();
    

    for (LivingEntity affectedEntity : event.getAffectedEntities())
    {
      EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(thrower, affectedEntity, EntityDamageEvent.DamageCause.CUSTOM, 0.0D);
      
      if (!EngineCanCombatHappen.get().canCombatDamageHappen(sub, false))
      {
        affectedList.add(affectedEntity);
      }
    }
    
    event.getAffectedEntities().removeAll(affectedList);
  }
}
