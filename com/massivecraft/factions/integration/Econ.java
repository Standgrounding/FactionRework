package com.massivecraft.factions.integration;

import com.massivecraft.factions.EconomyParticipator;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.RelationUtil;
import com.massivecraft.massivecore.money.Money;
import java.util.HashSet;
import java.util.Set;




public class Econ
{
  public Econ() {}
  
  public static boolean isEnabled()
  {
    return (geteconEnabled) && (Money.enabled());
  }
  




  public static boolean payForAction(double cost, MPlayer usender, String actionDescription)
  {
    if (!isEnabled()) return true;
    if (cost == 0.0D) { return true;
    }
    if (usender.isOverriding()) { return true;
    }
    Faction usenderFaction = usender.getFaction();
    
    if ((getbankEnabled) && (getbankFactionPaysCosts) && (usenderFaction.isNormal()))
    {
      return modifyMoney(usenderFaction, -cost, actionDescription);
    }
    

    return modifyMoney(usender, -cost, actionDescription);
  }
  





  public static void modifyUniverseMoney(Object universe, double delta)
  {
    if (!isEnabled()) { return;
    }
    if (geteconUniverseAccount == null) return;
    if (geteconUniverseAccount.length() == 0) { return;
    }
    if (!Money.exists(geteconUniverseAccount)) { return;
    }
    Money.spawn(geteconUniverseAccount, null, delta);
  }
  
  public static void sendBalanceInfo(MPlayer to, EconomyParticipator about)
  {
    to.msg("<a>%s's<i> balance is <h>%s<i>.", new Object[] { about.describeTo(to, true), Money.format(Money.get(about)) });
  }
  

  public static boolean isMePermittedYou(EconomyParticipator me, EconomyParticipator you, MPerm mperm)
  {
    if (me == null) { return true;
    }
    
    if (((me instanceof MPlayer)) && (((MPlayer)me).isOverriding())) { return true;
    }
    
    if (me == you) { return true;
    }
    Faction fMe = RelationUtil.getFaction(me);
    Faction fYou = RelationUtil.getFaction(you);
    



    if ((me == fMe) && (fMe == fYou)) { return true;
    }
    
    if ((you instanceof Faction))
    {
      if (((me instanceof Faction)) && (mperm.has((Faction)me, fYou))) return true;
      if (((me instanceof MPlayer)) && (mperm.has((MPlayer)me, fYou, false))) { return true;
      }
    }
    
    return false;
  }
  
  public static boolean transferMoney(EconomyParticipator invoker, EconomyParticipator from, EconomyParticipator to, double amount)
  {
    return transferMoney(from, to, invoker, amount, true);
  }
  
  public static boolean transferMoney(EconomyParticipator from, EconomyParticipator to, EconomyParticipator by, double amount, boolean notify) {
    if (!isEnabled()) { return false;
    }
    

    if (amount < 0.0D)
    {
      amount *= -1.0D;
      EconomyParticipator temp = from;
      from = to;
      to = temp;
    }
    

    if (!isMePermittedYou(by, from, MPerm.getPermWithdraw()))
    {
      by.msg("<h>%s<i> lack permission to withdraw money from <h>%s<i>.", new Object[] { by.describeTo(by, true), from.describeTo(by) });
      return false;
    }
    
    if (!isMePermittedYou(by, to, MPerm.getPermDeposit()))
    {
      by.msg("<h>%s<i> lack permission to deposit money to <h>%s<i>.", new Object[] { by.describeTo(by, true), to.describeTo(by) });
      return false;
    }
    

    if (Money.get(from) < amount)
    {

      if ((by != null) && (notify))
      {
        by.msg("<h>%s<b> can't afford to transfer <h>%s<b> to %s<b>.", new Object[] { from.describeTo(by, true), Money.format(amount), to.describeTo(by) });
      }
      return false;
    }
    

    if (Money.move(from, to, by, amount, "Factions"))
    {
      if (notify)
      {
        sendTransferInfo(by, from, to, amount);
      }
      return true;
    }
    


    if ((by != null) && (notify))
    {
      by.msg("Unable to transfer %s<b> to <h>%s<b> from <h>%s<b>.", new Object[] { Money.format(amount), to.describeTo(by), from.describeTo(by, true) });
    }
    return false;
  }
  

  public static Set<MPlayer> getMPlayers(EconomyParticipator ep)
  {
    Set<MPlayer> mplayers = new HashSet();
    
    if (ep != null)
    {


      if ((ep instanceof MPlayer))
      {
        mplayers.add((MPlayer)ep);
      }
      else if ((ep instanceof Faction))
      {
        mplayers.addAll(((Faction)ep).getMPlayers());
      }
    }
    return mplayers;
  }
  
  public static void sendTransferInfo(EconomyParticipator invoker, EconomyParticipator from, EconomyParticipator to, double amount)
  {
    Set<MPlayer> recipients = new HashSet();
    recipients.addAll(getMPlayers(invoker));
    recipients.addAll(getMPlayers(from));
    recipients.addAll(getMPlayers(to));
    
    if (invoker == null)
    {
      for (MPlayer recipient : recipients)
      {
        recipient.msg("<h>%s<i> was transfered from <h>%s<i> to <h>%s<i>.", new Object[] { Money.format(amount), from.describeTo(recipient), to.describeTo(recipient) });
      }
      
    } else if (invoker == from)
    {
      for (MPlayer recipient : recipients)
      {
        recipient.msg("<h>%s<i> <h>gave %s<i> to <h>%s<i>.", new Object[] { from.describeTo(recipient, true), Money.format(amount), to.describeTo(recipient) });
      }
      
    } else if (invoker == to)
    {
      for (MPlayer recipient : recipients)
      {
        recipient.msg("<h>%s<i> <h>took %s<i> from <h>%s<i>.", new Object[] { to.describeTo(recipient, true), Money.format(amount), from.describeTo(recipient) });
      }
      
    }
    else {
      for (MPlayer recipient : recipients)
      {
        recipient.msg("<h>%s<i> transfered <h>%s<i> from <h>%s<i> to <h>%s<i>.", new Object[] { invoker.describeTo(recipient, true), Money.format(amount), from.describeTo(recipient), to.describeTo(recipient) });
      }
    }
  }
  
  public static boolean hasAtLeast(EconomyParticipator ep, double delta, String toDoThis)
  {
    if (!isEnabled()) { return true;
    }
    if (Money.get(ep) < delta)
    {
      if ((toDoThis != null) && (!toDoThis.isEmpty()))
      {
        ep.msg("<h>%s<i> can't afford <h>%s<i> %s.", new Object[] { ep.describeTo(ep, true), Money.format(delta), toDoThis });
      }
      return false;
    }
    return true;
  }
  
  public static boolean modifyMoney(EconomyParticipator ep, double delta, String actionDescription)
  {
    if (!isEnabled()) return false;
    if (delta == 0.0D) { return true;
    }
    String You = ep.describeTo(ep, true);
    String you = ep.describeTo(ep, false);
    
    boolean hasActionDesctription = (actionDescription != null) && (!actionDescription.isEmpty());
    
    if (Money.spawn(ep, null, delta, "Factions"))
    {
      modifyUniverseMoney(ep, -delta);
      
      if (hasActionDesctription)
      {
        if (delta > 0.0D)
        {
          ep.msg("<h>%s<i> gained <h>%s<i> since %s did %s.", new Object[] { You, Money.format(delta), you, actionDescription });
        }
        else
        {
          ep.msg("<h>%s<i> lost <h>%s<i> since %s did %s.", new Object[] { You, Money.format(-delta), you, actionDescription });
        }
      }
      return true;
    }
    

    if (hasActionDesctription)
    {
      if (delta > 0.0D)
      {
        ep.msg("<h>%s<i> would have gained <h>%s<i> since %s did %s, but the deposit failed.", new Object[] { You, Money.format(delta), you, actionDescription });
      }
      else
      {
        ep.msg("<h>%s<i> can't afford <h>%s<i> to %s.", new Object[] { You, Money.format(-delta), actionDescription });
      }
    }
    return false;
  }
}
