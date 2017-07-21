package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;




public class CmdFactionsPowerBoostFaction
  extends CmdFactionsPowerBoostAbstract
{
  public CmdFactionsPowerBoostFaction()
  {
    super(TypeFaction.get(), "faction");
  }
}
