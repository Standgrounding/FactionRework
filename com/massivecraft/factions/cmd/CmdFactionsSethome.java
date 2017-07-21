package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsHomeChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.entity.Player;


public class CmdFactionsSethome
  extends FactionsCommandHome
{
  public CmdFactionsSethome()
  {
    addAliases(new String[] { "sethome" });
    

    addParameter(TypeFaction.get(), "faction", "you");
    

    addRequirements(new Requirement[] { RequirementHasPerm.get(Perm.SETHOME) });
    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  





  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg(msenderFaction);
    
    PS newHome = PS.valueOf(me.getLocation());
    

    if (!MPerm.getPermSethome().has(msender, faction, true)) { return;
    }
    
    if ((!msender.isOverriding()) && (!faction.isValidHome(newHome)))
    {
      msender.msg("<b>Sorry, your faction home can only be set inside your own claimed territory.");
      return;
    }
    

    EventFactionsHomeChange event = new EventFactionsHomeChange(sender, faction, newHome);
    event.run();
    if (event.isCancelled()) return;
    newHome = event.getNewHome();
    

    faction.setHome(newHome);
    

    faction.msg("%s<i> set the home for your faction. You can now use:", new Object[] { msender.describeTo(msenderFaction, true) });
    faction.sendMessage(getcmdFactionsHome.getTemplate());
    if (faction != msenderFaction)
    {
      msender.msg("<i>You have set the home for " + faction.getName(msender) + "<i>.");
    }
  }
}
