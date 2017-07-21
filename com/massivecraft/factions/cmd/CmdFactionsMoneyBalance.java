package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;




public class CmdFactionsMoneyBalance
  extends FactionsCommand
{
  public CmdFactionsMoneyBalance()
  {
    addParameter(TypeFaction.get(), "faction", "you");
    

    addRequirements(new Requirement[] { ReqBankCommandsEnabled.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg(msenderFaction);
    
    if ((faction != msenderFaction) && (!Perm.MONEY_BALANCE_ANY.has(sender, true))) { return;
    }
    Econ.sendBalanceInfo(msender, faction);
  }
}
