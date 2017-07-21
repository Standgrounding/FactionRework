package com.massivecraft.factions.cmd;

import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;




public class CmdFactionsAccessPlayer
  extends CmdFactionsAccessAbstract
{
  public CmdFactionsAccessPlayer()
  {
    addParameter(TypeMPlayer.get(), "player");
    addParameter(TypeBooleanYes.get(), "yes/no", "toggle");
  }
  





  public void innerPerform()
    throws MassiveException
  {
    MPlayer mplayer = (MPlayer)readArg();
    boolean newValue = ((Boolean)readArg(Boolean.valueOf(!ta.isMPlayerGranted(mplayer)))).booleanValue();
    

    if (!MPerm.getPermAccess().has(msender, hostFaction, true)) { return;
    }
    
    ta = ta.withPlayerId(mplayer.getId(), newValue);
    BoardColl.get().setTerritoryAccessAt(chunk, ta);
    

    sendAccessInfo();
  }
}
