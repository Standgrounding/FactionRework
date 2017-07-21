package com.massivecraft.factions.cmd;

import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;




public class CmdFactionsAccessFaction
  extends CmdFactionsAccessAbstract
{
  public CmdFactionsAccessFaction()
  {
    addParameter(TypeFaction.get(), "faction");
    addParameter(TypeBooleanYes.get(), "yes/no", "toggle");
  }
  





  public void innerPerform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg();
    boolean newValue = ((Boolean)readArg(Boolean.valueOf(!ta.isFactionGranted(faction)))).booleanValue();
    

    if (!MPerm.getPermAccess().has(msender, hostFaction, true)) { return;
    }
    
    ta = ta.withFactionId(faction.getId(), newValue);
    BoardColl.get().setTerritoryAccessAt(chunk, ta);
    

    sendAccessInfo();
  }
}
