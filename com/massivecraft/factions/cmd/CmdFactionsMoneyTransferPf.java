package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;




public class CmdFactionsMoneyTransferPf
  extends FactionsCommand
{
  public CmdFactionsMoneyTransferPf()
  {
    setSetupEnabled(false);
    

    addAliases(new String[] { "pf" });
    

    addParameter(TypeDouble.get(), "amount");
    addParameter(TypeMPlayer.get(), "player");
    addParameter(TypeFaction.get(), "faction");
    

    addRequirements(new Requirement[] { RequirementHasPerm.get(Perm.MONEY_P2F) });
    addRequirements(new Requirement[] { ReqBankCommandsEnabled.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    double amount = ((Double)readArg()).doubleValue();
    MPlayer from = (MPlayer)readArg();
    Faction to = (Faction)readArg();
    
    boolean success = Econ.transferMoney(msender, from, to, amount);
    
    if ((success) && (getlogMoneyTransactions))
    {
      Factions.get().log(new Object[] { ChatColor.stripColor(Txt.parse("%s transferred %s from the player \"%s\" to the faction \"%s\"", new Object[] { msender.getName(), Money.format(amount), from.describeTo(null), to.describeTo(null) })) });
    }
  }
}
