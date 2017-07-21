package com.massivecraft.factions.cmd;

import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;



public class CmdFactionsAccess
  extends FactionsCommand
{
  public CmdFactionsAccessView cmdFactionsAccessView = new CmdFactionsAccessView();
  public CmdFactionsAccessPlayer cmdFactionsAccessPlayer = new CmdFactionsAccessPlayer();
  public CmdFactionsAccessFaction cmdFactionsAccessFaction = new CmdFactionsAccessFaction();
  





  public CmdFactionsAccess()
  {
    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
}
