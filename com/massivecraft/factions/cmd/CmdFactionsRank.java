package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.cmd.type.TypeRank;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.factions.event.EventFactionsRankChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.util.Txt;
import java.util.HashSet;
import java.util.List;
import java.util.Set;






public class CmdFactionsRank
  extends FactionsCommand
{
  static final Rel rankReq = Rel.OFFICER;
  






  private Faction targetFaction = null;
  private MPlayer target = null;
  

  private Faction endFaction = null;
  private boolean factionChange = false;
  

  private Rel senderRank = null;
  private Rel targetRank = null;
  private Rel rank = null;
  





  public CmdFactionsRank()
  {
    addParameter(TypeMPlayer.get(), "player");
    addParameter(TypeRank.get(), "action", "show");
    addParameter(TypeFaction.get(), "faction", "their");
  }
  





  public void perform()
    throws MassiveException
  {
    registerFields();
    

    if (!argIsSet(1))
    {
      if (!Perm.RANK_SHOW.has(sender, true)) return;
      showRank();
      return;
    }
    

    if (!Perm.RANK_ACTION.has(sender, true)) { return;
    }
    
    ensureAllowed();
    
    if (factionChange)
    {
      changeFaction();
    }
    

    ensureMakesSense();
    

    EventFactionsRankChange event = new EventFactionsRankChange(sender, target, rank);
    event.run();
    if (event.isCancelled()) return;
    rank = event.getNewRank();
    

    changeRank();
  }
  


  public void senderFields(boolean set)
  {
    super.senderFields(set);
    
    if (!set)
    {
      unregisterFields();
    }
  }
  




  private void registerFields()
    throws MassiveException
  {
    target = ((MPlayer)readArg(msender));
    targetFaction = target.getFaction();
    


    senderRank = msender.getRole();
    targetRank = target.getRole();
    

    if (argIsSet(1))
    {
      setParameterType(1, TypeRank.get(targetRank));
      rank = ((Rel)readArg());
    }
    

    endFaction = ((Faction)readArgAt(2, targetFaction));
    factionChange = (endFaction != targetFaction);
  }
  

  private void unregisterFields()
  {
    targetFaction = null;
    target = null;
    
    senderRank = null;
    targetRank = null;
    rank = null;
  }
  




  private void ensureAllowed()
    throws MassiveException
  {
    if (msender.isOverriding()) { return;
    }
    

    if (endFaction.isNone())
    {
      throw new MassiveException().addMsg("%s <b>doesn't use ranks sorry :(", new Object[] { targetFaction.getName() });
    }
    
    if (target == msender)
    {

      throw new MassiveException().addMsg("<b>The target player mustn't be yourself.");
    }
    
    if (targetFaction != msenderFaction)
    {

      throw new MassiveException().addMsg("%s <b>is not in the same faction as you.", new Object[] { target.describeTo(msender, true) });
    }
    
    if (factionChange)
    {

      throw new MassiveException().addMsg("<b>You can't change %s's <b>faction.", new Object[] { target.describeTo(msender) });
    }
    
    if (senderRank.isLessThan(rankReq))
    {

      throw new MassiveException().addMsg("<b>You must be <h>%s <b>or higher to change ranks.", new Object[] { Txt.getNicedEnum(rankReq).toLowerCase() });
    }
    


    if (senderRank == targetRank)
    {

      throw new MassiveException().addMsg("<h>%s <b>can't manage eachother.", new Object[] { Txt.getNicedEnum(rankReq) + "s" });
    }
    
    if (senderRank.isLessThan(targetRank))
    {

      throw new MassiveException().addMsg("<b>You can't manage people of higher rank.");
    }
    


    if ((senderRank == rank) && (senderRank != Rel.LEADER))
    {

      throw new MassiveException().addMsg("<b>You can't set ranks equal to your own.");
    }
    
    if (senderRank.isLessThan(rank))
    {

      throw new MassiveException().addMsg("<b>You can't set ranks higher than your own.");
    }
  }
  
  private void ensureMakesSense()
    throws MassiveException
  {
    if (target.getRole() == rank)
    {
      throw new MassiveException().addMsg("%s <b>is already %s.", new Object[] { target.describeTo(msender), rank.getDescPlayerOne() });
    }
  }
  





  private void showRank()
  {
    String targetName = target.describeTo(msender, true);
    String isAre = target == msender ? "are" : "is";
    String theAan = targetRank == Rel.LEADER ? "the" : Txt.aan(targetRank.name());
    String rankName = Txt.getNicedEnum(targetRank).toLowerCase();
    String ofIn = targetRank == Rel.LEADER ? "of" : "in";
    String factionName = targetFaction.describeTo(msender, true);
    if (targetFaction == msenderFaction)
    {

      factionName = factionName.toLowerCase();
    }
    if (targetFaction.isNone())
    {

      msg("%s <i>%s factionless", new Object[] { targetName, isAre });

    }
    else
    {
      msg("%s <i>%s %s <h>%s <i>%s %s<i>.", new Object[] { targetName, isAre, theAan, rankName, ofIn, factionName });
    }
  }
  




  private void changeFaction()
    throws MassiveException
  {
    if (targetRank == Rel.LEADER)
    {
      throw new MassiveException().addMsg("<b>You cannot remove the present leader. Demote them first.");
    }
    

    EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(sender, msender, endFaction, EventFactionsMembershipChange.MembershipChangeReason.RANK);
    membershipChangeEvent.run();
    if (membershipChangeEvent.isCancelled()) { throw new MassiveException();
    }
    
    target.resetFactionData();
    target.setFaction(endFaction);
    

    endFaction.uninvite(target);
    

    Set<MPlayer> recipients = new HashSet();
    recipients.addAll(targetFaction.getMPlayersWhereOnline(true));
    recipients.addAll(endFaction.getMPlayersWhereOnline(true));
    recipients.add(msender);
    

    for (MPlayer recipient : recipients)
    {
      recipient.msg("%s <i>was moved from <i>%s to <i>%s<i>.", new Object[] { target.describeTo(recipient), targetFaction.describeTo(recipient), endFaction.describeTo(recipient) });
    }
    

    if (getlogFactionJoin)
    {
      Factions.get().log(new Object[] { Txt.parse("%s moved %s from %s to %s.", new Object[] { msender.getName(), target.getName(), targetFaction.getName(), endFaction.getName() }) });
    }
    

    targetFaction = target.getFaction();
    targetRank = target.getRole();
    senderRank = msender.getRole();
  }
  




  private void changeRank()
    throws MassiveException
  {
    if (rank == Rel.LEADER)
    {
      changeRankLeader();
    }
    else
    {
      changeRankOther();
    }
  }
  

  private void changeRankLeader()
  {
    MPlayer targetFactionCurrentLeader = targetFaction.getLeader();
    if (targetFactionCurrentLeader != null)
    {

      targetFactionCurrentLeader.setRole(Rel.OFFICER);
      if (targetFactionCurrentLeader != msender)
      {

        targetFactionCurrentLeader.msg("<i>You have been demoted from the position of faction leader by %s<i>.", new Object[] { msender.describeTo(targetFactionCurrentLeader, true) });
      }
    }
    

    target.setRole(Rel.LEADER);
    

    for (MPlayer recipient : MPlayerColl.get().getAllOnline())
    {
      String changerName = senderIsConsole ? "A server admin" : msender.describeTo(recipient);
      recipient.msg("%s<i> gave %s<i> the leadership of %s<i>.", new Object[] { changerName, target.describeTo(recipient), targetFaction.describeTo(recipient) });
    }
  }
  

  private void changeRankOther()
    throws MassiveException
  {
    if ((targetRank == Rel.LEADER) && ((!getpermanentFactionsDisableLeaderPromotion) || (!targetFaction.getFlag("permanent"))) && (targetFaction.getMPlayersWhereRole(Rel.LEADER).size() == 1))
    {

      targetFaction.promoteNewLeader();
      

      if (targetFaction.detached())
      {

        target.resetFactionData();
        throw new MassiveException().addMsg("<i>The target was a leader and got demoted. The faction disbanded and no rank was set.");
      }
    }
    

    Set<MPlayer> recipients = new HashSet();
    recipients.addAll(targetFaction.getMPlayers());
    recipients.add(msender);
    

    String change = rank.isLessThan(targetRank) ? "demoted" : "promoted";
    

    target.setRole(rank);
    String oldRankName = Txt.getNicedEnum(targetRank).toLowerCase();
    String rankName = Txt.getNicedEnum(rank).toLowerCase();
    

    for (MPlayer recipient : recipients)
    {
      String targetName = target.describeTo(recipient, true);
      String wasWere = recipient == target ? "were" : "was";
      recipient.msg("%s<i> %s %s from %s to <h>%s <i>in %s<i>.", new Object[] { targetName, wasWere, change, oldRankName, rankName, targetFaction.describeTo(msender) });
    }
  }
}
