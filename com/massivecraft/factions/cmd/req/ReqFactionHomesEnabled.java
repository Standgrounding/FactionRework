package com.massivecraft.factions.cmd.req;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementAbstract;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;






public class ReqFactionHomesEnabled
  extends RequirementAbstract
{
  private static final long serialVersionUID = 1L;
  
  public ReqFactionHomesEnabled() {}
  
  private static ReqFactionHomesEnabled i = new ReqFactionHomesEnabled();
  public static ReqFactionHomesEnabled get() { return i; }
  





  public boolean apply(CommandSender sender, MassiveCommand command)
  {
    return gethomesEnabled;
  }
  

  public String createErrorMessage(CommandSender sender, MassiveCommand command)
  {
    return Txt.parse("<b>Homes must be enabled on the server to %s.", new Object[] { getDesc(command) });
  }
}
