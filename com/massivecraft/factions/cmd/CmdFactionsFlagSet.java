package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMFlag;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsFlagChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;




public class CmdFactionsFlagSet
  extends FactionsCommand
{
  public CmdFactionsFlagSet()
  {
    addParameter(TypeMFlag.get(), "flag");
    addParameter(TypeBooleanYes.get(), "yes/no");
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    MFlag flag = (MFlag)readArg();
    boolean value = ((Boolean)readArg()).booleanValue();
    Faction faction = (Faction)readArg(msenderFaction);
    

    if (!MPerm.getPermFlags().has(msender, faction, true)) { return;
    }
    
    if ((!msender.isOverriding()) && (!flag.isEditable()))
    {
      msg("<b>The flag <h>%s <b>is not editable.", new Object[] { flag.getName() });
      return;
    }
    

    EventFactionsFlagChange event = new EventFactionsFlagChange(sender, faction, flag, value);
    event.run();
    if (event.isCancelled()) return;
    value = event.isNewValue();
    

    if (faction.getFlag(flag) == value)
    {
      msg("%s <i>already has %s <i>set to %s<i>.", new Object[] { faction.describeTo(msender), flag.getStateDesc(value, false, true, true, false, true), flag.getStateDesc(value, true, true, false, false, false) });
      return;
    }
    

    faction.setFlag(flag, value);
    

    String stateInfo = flag.getStateDesc(faction.getFlag(flag), true, false, true, true, true);
    if (msender.getFaction() != faction)
    {

      msg("<h>%s <i>set a flag for <h>%s<i>.", new Object[] { msender.describeTo(msender, true), faction.describeTo(msender, true) });
      message(stateInfo);
    }
    faction.msg("<h>%s <i>set a flag for <h>%s<i>.", new Object[] { msender.describeTo(faction, true), faction.describeTo(faction, true) });
    faction.sendMessage(stateInfo);
  }
}
