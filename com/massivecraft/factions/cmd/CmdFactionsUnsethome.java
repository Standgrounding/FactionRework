package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsHomeChange;
import com.massivecraft.massivecore.MassiveException;




public class CmdFactionsUnsethome
  extends FactionsCommandHome
{
  public CmdFactionsUnsethome()
  {
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg(msenderFaction);
    

    if (!MPerm.getPermSethome().has(msender, faction, true)) { return;
    }
    
    if (!faction.hasHome())
    {
      msender.msg("<i>%s <i>does already not have a home.", new Object[] { faction.describeTo(msender) });
      return;
    }
    

    EventFactionsHomeChange event = new EventFactionsHomeChange(sender, faction, null);
    event.run();
    if (event.isCancelled()) { return;
    }
    
    faction.setHome(null);
    

    faction.msg("%s<i> unset the home for your faction.", new Object[] { msender.describeTo(msenderFaction, true) });
    if (faction != msenderFaction)
    {
      msender.msg("<i>You have unset the home for " + faction.getName(msender) + "<i>.");
    }
  }
}
