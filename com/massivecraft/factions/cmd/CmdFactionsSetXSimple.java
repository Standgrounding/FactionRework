package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;





public abstract class CmdFactionsSetXSimple
  extends CmdFactionsSetX
{
  public CmdFactionsSetXSimple(boolean claim)
  {
    super(claim);
    

    if (claim)
    {
      addParameter(TypeFaction.get(), "faction", "you");
      setFactionArgIndex(0);
    }
  }
}
