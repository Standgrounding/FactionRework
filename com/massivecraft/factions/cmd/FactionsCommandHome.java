package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.req.ReqFactionHomesEnabled;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.Requirement;



public class FactionsCommandHome
  extends FactionsCommand
{
  public FactionsCommandHome()
  {
    addRequirements(new Requirement[] { ReqFactionHomesEnabled.get() });
  }
  





  public Visibility getVisibility()
  {
    return gethomesEnabled ? super.getVisibility() : Visibility.INVISIBLE;
  }
}
