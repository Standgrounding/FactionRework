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



public class CmdFactionsMoneyDeposit
  extends FactionsCommand
{
  public CmdFactionsMoneyDeposit()
  {
    addParameter(TypeDouble.get(), "amount");
    addParameter(TypeFaction.get(), "faction", "you");
    

    addRequirements(new Requirement[] { ReqBankCommandsEnabled.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    double amount = ((Double)readArg()).doubleValue();
    
    Faction faction = (Faction)readArg(msenderFaction);
    
    boolean success = Econ.transferMoney(msender, msender, faction, amount);
    
    if ((success) && (getlogMoneyTransactions))
    {
      Factions.get().log(new Object[] { ChatColor.stripColor(Txt.parse("%s deposited %s in the faction bank: %s", new Object[] { msender.getName(), Money.format(amount), faction.describeTo(null) })) });
    }
  }
}
