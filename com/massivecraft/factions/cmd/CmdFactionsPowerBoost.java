package com.massivecraft.factions.cmd;




public class CmdFactionsPowerBoost
  extends FactionsCommand
{
  public CmdFactionsPowerBoostPlayer cmdFactionsPowerBoostPlayer = new CmdFactionsPowerBoostPlayer();
  public CmdFactionsPowerBoostFaction cmdFactionsPowerBoostFaction = new CmdFactionsPowerBoostFaction();
  





  public CmdFactionsPowerBoost()
  {
    addChild(cmdFactionsPowerBoostPlayer);
    addChild(cmdFactionsPowerBoostFaction);
  }
}
