package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPvpDisallowed;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Set;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EngineCanCombatHappen extends com.massivecraft.massivecore.Engine
{
  public EngineCanCombatHappen() {}
  
  private static EngineCanCombatHappen i = new EngineCanCombatHappen();
  public static EngineCanCombatHappen get() { return i; }
  




  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void canCombatDamageHappen(EntityDamageByEntityEvent event)
  {
    if (canCombatDamageHappen(event, true)) return;
    event.setCancelled(true);
    
    Entity damager = event.getDamager();
    if (!(damager instanceof Arrow)) { return;
    }
    damager.remove();
  }
  


  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void canCombatDamageHappen(EntityCombustByEntityEvent event)
  {
    EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(event.getCombuster(), event.getEntity(), EntityDamageEvent.DamageCause.FIRE, 0.0D);
    if (canCombatDamageHappen(sub, false)) return;
    event.setCancelled(true);
  }
  


  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void canCombatDamageHappen(PotionSplashEvent event)
  {
    if (!MUtil.isHarmfulPotion(event.getPotion())) { return;
    }
    ProjectileSource projectileSource = event.getPotion().getShooter();
    if (!(projectileSource instanceof Entity)) { return;
    }
    Entity thrower = (Entity)projectileSource;
    

    for (LivingEntity affectedEntity : event.getAffectedEntities())
    {
      EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(thrower, affectedEntity, EntityDamageEvent.DamageCause.CUSTOM, 0.0D);
      if (!canCombatDamageHappen(sub, true))
      {

        event.setIntensity(affectedEntity, 0.0D);
      }
    }
  }
  
  public static boolean falseUnlessDisallowedPvpEventCancelled(Player attacker, Player defender, DisallowCause reason, EntityDamageByEntityEvent event)
  {
    EventFactionsPvpDisallowed dpe = new EventFactionsPvpDisallowed(attacker, defender, reason, event);
    dpe.run();
    return dpe.isCancelled();
  }
  
  public boolean canCombatDamageHappen(EntityDamageByEntityEvent event, boolean notify)
  {
    boolean ret = true;
    

    Entity edefender = event.getEntity();
    if (MUtil.isntPlayer(edefender)) return true;
    Player defender = (Player)edefender;
    MPlayer mdefender = MPlayer.get(edefender);
    

    Entity eattacker = MUtil.getLiableDamager(event);
    


    if ((eattacker != null) && (eattacker.equals(edefender))) { return true;
    }
    
    PS defenderPs = PS.valueOf(defender.getLocation());
    Faction defenderPsFaction = BoardColl.get().getFactionAt(defenderPs);
    

    MPlayer mplayer = MPlayer.get(eattacker);
    if ((mplayer != null) && (mplayer.isOverriding())) { return true;
    }
    
    if (!defenderPsFaction.getFlag(MFlag.getFlagPvp()))
    {
      if (eattacker == null)
      {


        return falseUnlessDisallowedPvpEventCancelled(null, defender, DisallowCause.PEACEFUL_LAND, event);
      }
      if (MUtil.isPlayer(eattacker))
      {
        ret = falseUnlessDisallowedPvpEventCancelled((Player)eattacker, defender, DisallowCause.PEACEFUL_LAND, event);
        if ((!ret) && (notify))
        {
          MPlayer attacker = MPlayer.get(eattacker);
          attacker.msg("<i>PVP is disabled in %s.", new Object[] { defenderPsFaction.describeTo(attacker) });
        }
        return ret;
      }
      return defenderPsFaction.getFlag(MFlag.getFlagMonsters());
    }
    

    if (MUtil.isntPlayer(eattacker)) return true;
    Player attacker = (Player)eattacker;
    MPlayer uattacker = MPlayer.get(attacker);
    

    if (getplayersWhoBypassAllProtection.contains(attacker.getName())) { return true;
    }
    
    PS attackerPs = PS.valueOf(attacker.getLocation());
    Faction attackerPsFaction = BoardColl.get().getFactionAt(attackerPs);
    



    if (!attackerPsFaction.getFlag(MFlag.getFlagPvp()))
    {
      ret = falseUnlessDisallowedPvpEventCancelled(attacker, defender, DisallowCause.PEACEFUL_LAND, event);
      if ((!ret) && (notify)) uattacker.msg("<i>PVP is disabled in %s.", new Object[] { attackerPsFaction.describeTo(uattacker) });
      return ret;
    }
    

    if (!getworldsPvpRulesEnabled.contains(defenderPs.getWorld())) { return true;
    }
    Faction defendFaction = mdefender.getFaction();
    Faction attackFaction = uattacker.getFaction();
    
    if ((attackFaction.isNone()) && (getdisablePVPForFactionlessPlayers))
    {
      ret = falseUnlessDisallowedPvpEventCancelled(attacker, defender, DisallowCause.FACTIONLESS, event);
      if ((!ret) && (notify)) uattacker.msg("<i>You can't hurt other players until you join a faction.");
      return ret;
    }
    if (defendFaction.isNone())
    {
      if ((defenderPsFaction == attackFaction) && (getenablePVPAgainstFactionlessInAttackersLand))
      {

        return true;
      }
      if (getdisablePVPForFactionlessPlayers)
      {
        ret = falseUnlessDisallowedPvpEventCancelled(attacker, defender, DisallowCause.FACTIONLESS, event);
        if ((!ret) && (notify)) uattacker.msg("<i>You can't hurt players who are not currently in a faction.");
        return ret;
      }
      if ((attackFaction.isNone()) && (getenablePVPBetweenFactionlessPlayers))
      {

        return true;
      }
    }
    
    Rel relation = defendFaction.getRelationTo(attackFaction);
    

    if ((relation.isFriend()) && (!defenderPsFaction.getFlag(MFlag.getFlagFriendlyire())))
    {
      ret = falseUnlessDisallowedPvpEventCancelled(attacker, defender, DisallowCause.FRIENDLYFIRE, event);
      if ((!ret) && (notify)) uattacker.msg("<i>You can't hurt %s<i>.", new Object[] { relation.getDescPlayerMany() });
      return ret;
    }
    

    boolean ownTerritory = mdefender.isInOwnTerritory();
    
    if ((mdefender.hasFaction()) && (ownTerritory) && (relation == Rel.NEUTRAL))
    {
      ret = falseUnlessDisallowedPvpEventCancelled(attacker, defender, DisallowCause.OWN_TERRITORY, event);
      if ((!ret) && (notify))
      {
        uattacker.msg("<i>You can't hurt %s<i> in their own territory unless you declare them as an enemy.", new Object[] { mdefender.describeTo(uattacker) });
        mdefender.msg("%s<i> tried to hurt you.", new Object[] { uattacker.describeTo(mdefender, true) });
      }
      return ret;
    }
    
    return true;
  }
}
