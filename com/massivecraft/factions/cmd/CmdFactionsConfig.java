package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.command.editor.CommandEditSingleton;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;



public class CmdFactionsConfig
  extends CommandEditSingleton<MConf>
{
  public CmdFactionsConfig()
  {
    super(MConf.get());
    

    addRequirements(new Requirement[] { RequirementHasPerm.get(Perm.CONFIG) });
  }
}
