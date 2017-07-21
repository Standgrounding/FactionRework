package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsNameChange;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import java.util.ArrayList;





public class CmdFactionsName
  extends FactionsCommand
{
  public CmdFactionsName()
  {
    addParameter(TypeString.get(), "new name");
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    String newName = (String)readArg();
    Faction faction = (Faction)readArg(msenderFaction);
    

    if (!MPerm.getPermName().has(msender, faction, true)) { return;
    }
    
    if ((FactionColl.get().isNameTaken(newName)) && (!MiscUtil.getComparisonString(newName).equals(faction.getComparisonName())))
    {
      msg("<b>That name is already taken");
      return;
    }
    
    ArrayList<String> errors = new ArrayList();
    errors.addAll(FactionColl.get().validateName(newName));
    if (errors.size() > 0)
    {
      message(errors);
      return;
    }
    

    EventFactionsNameChange event = new EventFactionsNameChange(sender, faction, newName);
    event.run();
    if (event.isCancelled()) return;
    newName = event.getNewName();
    

    faction.setName(newName);
    

    faction.msg("%s<i> changed your faction name to %s", new Object[] { msender.describeTo(faction, true), faction.getName(faction) });
    if (msenderFaction != faction)
    {
      msg("<i>You changed the faction name to %s", new Object[] { faction.getName(msender) });
    }
  }
}
