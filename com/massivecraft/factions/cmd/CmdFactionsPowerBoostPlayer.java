package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeMPlayer;




public class CmdFactionsPowerBoostPlayer
  extends CmdFactionsPowerBoostAbstract
{
  public CmdFactionsPowerBoostPlayer()
  {
    super(TypeMPlayer.get(), "player");
  }
}
