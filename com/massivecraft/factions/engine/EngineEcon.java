package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.factions.cmd.CmdFactionsCreate;
import com.massivecraft.factions.cmd.CmdFactionsInvite;
import com.massivecraft.factions.cmd.CmdFactionsRelationSet;
import com.massivecraft.factions.cmd.CmdFactionsTitle;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsAbstractSender;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDescriptionChange;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsFlagChange;
import com.massivecraft.factions.event.EventFactionsHomeChange;
import com.massivecraft.factions.event.EventFactionsHomeTeleport;
import com.massivecraft.factions.event.EventFactionsInvitedChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.factions.event.EventFactionsNameChange;
import com.massivecraft.factions.event.EventFactionsRelationChange;
import com.massivecraft.factions.event.EventFactionsTitleChange;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.ps.PS;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineEcon extends com.massivecraft.massivecore.Engine
{
  public EngineEcon() {}
  
  private static EngineEcon i = new EngineEcon();
  public static EngineEcon get() { return i; }
  





  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void takeOnLeave(EventFactionsMembershipChange event)
  {
    if (event.getReason() != EventFactionsMembershipChange.MembershipChangeReason.LEAVE) { return;
    }
    
    MPlayer mplayer = event.getMPlayer();
    Faction oldFaction = mplayer.getFaction();
    if (oldFaction.getMPlayers().size() > 1) { return;
    }
    
    double money = Money.get(oldFaction);
    if (money == 0.0D) return;
    Econ.transferMoney(mplayer, oldFaction, mplayer, money);
  }
  





  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void takeOnDisband(EventFactionsDisband event)
  {
    MPlayer mplayer = event.getMPlayer();
    if (mplayer == null) { return;
    }
    
    if (!Econ.isEnabled()) { return;
    }
    
    Faction faction = event.getFaction();
    
    double amount = Money.get(faction);
    

    if (amount == 0.0D) { return;
    }
    String amountString = Money.format(amount);
    
    Econ.transferMoney(faction, mplayer, mplayer, amount, true);
    
    mplayer.msg("<i>You have been given the disbanded faction's bank, totaling %s.", new Object[] { amountString });
    Factions.get().log(new Object[] { mplayer.getName() + " has been given bank holdings of " + amountString + " from disbanding " + faction.getName() + "." });
  }
  





  public static void payForAction(EventFactionsAbstractSender event, Double cost, String desc)
  {
    MPlayer mplayer = event.getMPlayer();
    if (mplayer == null) { return;
    }
    
    if (cost == null) return;
    if (cost.doubleValue() == 0.0D) { return;
    }
    
    if (Econ.payForAction(cost.doubleValue(), mplayer, desc)) { return;
    }
    
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForAction(EventFactionsChunksChange event)
  {
    double cost = 0.0D;
    List<String> typeNames = new ArrayList();
    
    for (Map.Entry<EventFactionsChunkChangeType, Set<PS>> typeChunks : event.getTypeChunks().entrySet())
    {
      EventFactionsChunkChangeType type = (EventFactionsChunkChangeType)typeChunks.getKey();
      Set<PS> chunks = (Set)typeChunks.getValue();
      
      Double typeCost = (Double)geteconChunkCost.get(type);
      if ((typeCost != null) && 
        (typeCost.doubleValue() != 0.0D))
      {
        typeCost = Double.valueOf(typeCost.doubleValue() * chunks.size());
        cost += typeCost.doubleValue();
        
        typeNames.add(now);
      }
    }
    String desc = com.massivecraft.massivecore.util.Txt.implodeCommaAnd(typeNames) + " this land";
    payForAction(event, Double.valueOf(cost), desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForAction(EventFactionsMembershipChange event)
  {
    Double cost = null;
    String desc = null;
    
    if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.JOIN)
    {
      cost = Double.valueOf(geteconCostJoin);
      desc = "join a faction";
    }
    else if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.LEAVE)
    {
      cost = Double.valueOf(geteconCostLeave);
      desc = "leave a faction";
    }
    else if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.KICK)
    {
      cost = Double.valueOf(geteconCostKick);
      desc = "kick someone from a faction";
    }
    else
    {
      return;
    }
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsRelationChange event)
  {
    Double cost = (Double)geteconRelCost.get(event.getNewRelation());
    String desc = getcmdFactionsRelation.cmdFactionsRelationSet.getDesc();
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsHomeChange event)
  {
    Double cost = Double.valueOf(geteconCostSethome);
    String desc = getcmdFactionsSethome.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsCreate event)
  {
    Double cost = Double.valueOf(geteconCostCreate);
    String desc = getcmdFactionsCreate.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsDescriptionChange event)
  {
    Double cost = Double.valueOf(geteconCostDescription);
    String desc = getcmdFactionsDescription.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsNameChange event)
  {
    Double cost = Double.valueOf(geteconCostName);
    String desc = getcmdFactionsName.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsTitleChange event)
  {
    Double cost = Double.valueOf(geteconCostTitle);
    String desc = getcmdFactionsTitle.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsFlagChange event)
  {
    Double cost = Double.valueOf(geteconCostFlag);
    String desc = getcmdFactionsFlag.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsInvitedChange event)
  {
    Double cost = Double.valueOf(event.isNewInvited() ? geteconCostInvite : geteconCostDeinvite);
    String desc = getcmdFactionsInvite.getDesc();
    
    payForAction(event, cost, desc);
  }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void payForCommand(EventFactionsHomeTeleport event)
  {
    Double cost = Double.valueOf(geteconCostHome);
    String desc = getcmdFactionsHome.getDesc();
    
    payForAction(event, cost, desc);
  }
}
