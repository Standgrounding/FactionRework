package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsHomeTeleport;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.mixin.MixinTeleport;
import com.massivecraft.massivecore.mixin.TeleporterException;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.teleport.Destination;
import com.massivecraft.massivecore.teleport.DestinationSimple;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;



public class CmdFactionsHome
  extends FactionsCommandHome
{
  public CmdFactionsHome()
  {
    addParameter(TypeFaction.get(), "faction", "you");
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    if (!gethomesTeleportCommandEnabled)
    {
      msender.msg("<b>Sorry, the ability to teleport to Faction homes is disabled on this server.");
      return;
    }
    

    Faction faction = (Faction)readArg(msenderFaction);
    PS home = faction.getHome();
    String homeDesc = "home for " + faction.describeTo(msender, false);
    

    if (!MPerm.getPermHome().has(msender, faction, true)) { return;
    }
    if (home == null)
    {
      msender.msg("<b>%s <b>does not have a home.", new Object[] { faction.describeTo(msender, true) });
      
      if (MPerm.getPermSethome().has(msender, faction, false))
      {
        msender.msg("<i>You should:");
        msender.message(getcmdFactionsSethome.getTemplate());
      }
      
      return;
    }
    
    if ((!gethomesTeleportAllowedFromEnemyTerritory) && (msender.isInEnemyTerritory()))
    {
      msender.msg("<b>You cannot teleport to %s <b>while in the territory of an enemy faction.", new Object[] { homeDesc });
      return;
    }
    
    if ((!gethomesTeleportAllowedFromDifferentWorld) && (!me.getWorld().getName().equalsIgnoreCase(home.getWorld())))
    {
      msender.msg("<b>You cannot teleport to %s <b>while in a different world.", new Object[] { homeDesc });
      return;
    }
    

    Faction factionHere = BoardColl.get().getFactionAt(PS.valueOf(me.getLocation()));
    Location locationHere = me.getLocation().clone();
    World w;
    double x;
    double y;
    double z;
    if (gethomesTeleportAllowedEnemyDistance > 0.0D)
    {
      if (factionHere.getFlag(MFlag.getFlagPvp()))
      {

        if (msender.isInOwnTerritory())
        {

          if (msender.isInOwnTerritory())
          {
            if (gethomesTeleportIgnoreEnemiesIfInOwnTerritory) {}
          }
        }
        else
        {
          w = locationHere.getWorld();
          x = locationHere.getX();
          y = locationHere.getY();
          z = locationHere.getZ();
          
          for (Player p : me.getServer().getOnlinePlayers())
          {
            if ((!MUtil.isntPlayer(p)) && 
            
              (p != null) && (p.isOnline()) && (!p.isDead()) && (p != me) && (p.getWorld() == w))
            {

              MPlayer fp = MPlayer.get(p);
              if (msender.getRelationTo(fp) == Rel.ENEMY)
              {

                Location l = p.getLocation();
                double dx = Math.abs(x - l.getX());
                double dy = Math.abs(y - l.getY());
                double dz = Math.abs(z - l.getZ());
                double max = gethomesTeleportAllowedEnemyDistance;
                

                if ((dx <= max) && (dy <= max) && (dz <= max))
                {

                  msender.msg("<b>You cannot teleport to %s <b>while an enemy is within %f blocks of you.", new Object[] { homeDesc, Double.valueOf(gethomesTeleportAllowedEnemyDistance) });
                  return;
                }
              }
            } }
        } } }
    EventFactionsHomeTeleport event = new EventFactionsHomeTeleport(sender);
    event.run();
    if (event.isCancelled()) { return;
    }
    
    try
    {
      Destination destination = new DestinationSimple(home, homeDesc);
      MixinTeleport.get().teleport(me, destination, sender);
    }
    catch (TeleporterException e)
    {
      String message = e.getMessage();
      MixinMessage.get().messageOne(me, message);
    }
  }
}
