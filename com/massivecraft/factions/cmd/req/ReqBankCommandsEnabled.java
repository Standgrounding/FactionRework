package com.massivecraft.factions.cmd.req;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementAbstract;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;






public class ReqBankCommandsEnabled
  extends RequirementAbstract
{
  private static final long serialVersionUID = 1L;
  
  public ReqBankCommandsEnabled() {}
  
  private static ReqBankCommandsEnabled i = new ReqBankCommandsEnabled();
  public static ReqBankCommandsEnabled get() { return i; }
  





  public boolean apply(CommandSender sender, MassiveCommand command)
  {
    return (getbankEnabled) && (Econ.isEnabled());
  }
  

  public String createErrorMessage(CommandSender sender, MassiveCommand command)
  {
    String what = !getbankEnabled ? "banks" : "economy features";
    return Txt.parse("<b>Faction %s are disabled.", new Object[] { what });
  }
}
