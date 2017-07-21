package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import org.bukkit.ChatColor;




public class CmdFactionsJoin
  extends FactionsCommand
{
  public CmdFactionsJoin()
  {
    addParameter(TypeFaction.get(), "faction");
    addParameter(TypeMPlayer.get(), "player", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg();
    
    MPlayer mplayer = (MPlayer)readArg(msender);
    Faction mplayerFaction = mplayer.getFaction();
    
    boolean samePlayer = mplayer == msender;
    

    if ((!samePlayer) && (!Perm.JOIN_OTHERS.has(sender, false)))
    {
      msg("<b>You do not have permission to move other players into a faction.");
      return;
    }
    
    if (faction == mplayerFaction)
    {
      String command = getcmdFactionsKick.getCommandLine(new String[] { mplayer.getName() });
      

      Mson alreadyMember = Mson.mson(new Object[] {
        Mson.parse(mplayer.describeTo(msender, true)), 
        mson(new Object[] {(samePlayer ? " are" : " is") + " already a member of " + faction.getName(msender) + "." }).color(ChatColor.YELLOW) });
      

      message(alreadyMember.suggest(command).tooltip(Txt.parse("<i>Click to <c>%s<i>.", new Object[] { command })));
      return;
    }
    
    if ((getfactionMemberLimit > 0) && (faction.getMPlayers().size() >= getfactionMemberLimit))
    {
      msg(" <b>!<white> The faction %s is at the limit of %d members, so %s cannot currently join.", new Object[] { faction.getName(msender), Integer.valueOf(getfactionMemberLimit), mplayer.describeTo(msender, false) });
      return;
    }
    
    if (mplayerFaction.isNormal())
    {
      String command = getcmdFactionsLeave.getCommandLine(new String[] { mplayer.getName() });
      

      Mson leaveFirst = Mson.mson(new Object[] {
        Mson.parse(mplayer.describeTo(msender, true)), 
        mson(new Object[] { " must leave " + (samePlayer ? "your" : "their") + " current faction first." }).color(ChatColor.RED) });
      

      message(leaveFirst.suggest(command).tooltip(Txt.parse("<i>Click to <c>%s<i>.", new Object[] { command })));
      return;
    }
    
    if ((!getcanLeaveWithNegativePower) && (mplayer.getPower() < 0.0D))
    {
      msg("<b>%s cannot join a faction with a negative power level.", new Object[] { mplayer.describeTo(msender, true) });
      return;
    }
    
    if ((!faction.getFlag(MFlag.getFlagOpen())) && (!faction.isInvited(mplayer)) && (!msender.isOverriding()))
    {
      msg("<i>This faction requires invitation.");
      if (samePlayer)
      {
        faction.msg("%s<i> tried to join your faction.", new Object[] { mplayer.describeTo(faction, true) });
      }
      return;
    }
    

    EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(sender, msender, faction, EventFactionsMembershipChange.MembershipChangeReason.JOIN);
    membershipChangeEvent.run();
    if (membershipChangeEvent.isCancelled()) { return;
    }
    
    if (!samePlayer)
    {
      mplayer.msg("<i>%s <i>moved you into the faction %s<i>.", new Object[] { msender.describeTo(mplayer, true), faction.getName(mplayer) });
    }
    faction.msg("<i>%s <i>joined <lime>your faction<i>.", new Object[] { mplayer.describeTo(faction, true) });
    msender.msg("<i>%s <i>successfully joined %s<i>.", new Object[] { mplayer.describeTo(msender, true), faction.getName(msender) });
    

    mplayer.resetFactionData();
    mplayer.setFaction(faction);
    
    faction.uninvite(mplayer);
    

    if (getlogFactionJoin)
    {
      if (samePlayer)
      {
        Factions.get().log(new Object[] { Txt.parse("%s joined the faction %s.", new Object[] { mplayer.getName(), faction.getName() }) });
      }
      else
      {
        Factions.get().log(new Object[] { Txt.parse("%s moved the player %s into the faction %s.", new Object[] { msender.getName(), mplayer.getName(), faction.getName() }) });
      }
    }
  }
}
