package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;




public class CmdFactionsMoneyWithdraw
  extends FactionsCommand
{
  public CmdFactionsMoneyWithdraw()
  {
    addParameter(TypeDouble.get(), "amount");
    addParameter(TypeFaction.get(), "faction", "you");
    

    addRequirements(new Requirement[] { ReqBankCommandsEnabled.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    Double amount = (Double)readArg();
    Faction from = (Faction)readArg(msenderFaction);
    
    MPlayer to = msender;
    
    boolean success = Econ.transferMoney(msender, from, to, amount.doubleValue());
    
    if ((success) && (getlogMoneyTransactions))
    {
      Factions.get().log(new Object[] { ChatColor.stripColor(Txt.parse("%s withdrew %s from the faction bank: %s", new Object[] { msender.getName(), Money.format(amount.doubleValue()), from.describeTo(null) })) });
    }
  }
}
