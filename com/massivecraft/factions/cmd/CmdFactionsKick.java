package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.ChatColor;




public class CmdFactionsKick
  extends FactionsCommand
{
  public CmdFactionsKick()
  {
    addParameter(TypeMPlayer.get(), "player");
  }
  





  public void perform()
    throws MassiveException
  {
    MPlayer mplayer = (MPlayer)readArg();
    

    if (msender == mplayer)
    {
      msg("<b>You can't kick yourself.");
      message(mson(new Object[] { mson(new Object[] { "You might want to: " }).color(ChatColor.YELLOW), getcmdFactionsLeave.getTemplate(false) }));
      return;
    }
    
    if ((mplayer.getRole() == Rel.LEADER) && (!msender.isOverriding()))
    {
      throw new MassiveException().addMsg("<b>The leader cannot be kicked.");
    }
    
    if ((mplayer.getRole().isMoreThan(msender.getRole())) && (!msender.isOverriding()))
    {
      throw new MassiveException().addMsg("<b>You can't kick people of higher rank than yourself.");
    }
    
    if ((mplayer.getRole() == msender.getRole()) && (!msender.isOverriding()))
    {
      throw new MassiveException().addMsg("<b>You can't kick people of the same rank as yourself.");
    }
    
    if ((!getcanLeaveWithNegativePower) && (mplayer.getPower() < 0.0D) && (!msender.isOverriding()))
    {
      msg("<b>You can't kick that person until their power is positive.");
      return;
    }
    

    Faction mplayerFaction = mplayer.getFaction();
    if (!MPerm.getPermKick().has(msender, mplayerFaction, true)) { return;
    }
    
    EventFactionsMembershipChange event = new EventFactionsMembershipChange(sender, mplayer, FactionColl.get().getNone(), EventFactionsMembershipChange.MembershipChangeReason.KICK);
    event.run();
    if (event.isCancelled()) { return;
    }
    
    mplayerFaction.msg("%s<i> kicked %s<i> from the faction! :O", new Object[] { msender.describeTo(mplayerFaction, true), mplayer.describeTo(mplayerFaction, true) });
    mplayer.msg("%s<i> kicked you from %s<i>! :O", new Object[] { msender.describeTo(mplayer, true), mplayerFaction.describeTo(mplayer) });
    if (mplayerFaction != msenderFaction)
    {
      msender.msg("<i>You kicked %s<i> from the faction %s<i>!", new Object[] { mplayer.describeTo(msender), mplayerFaction.describeTo(msender) });
    }
    
    if (getlogFactionKick)
    {
      Factions.get().log(new Object[] { msender.getDisplayName(IdUtil.getConsole()) + " kicked " + mplayer.getName() + " from the faction " + mplayerFaction.getName() });
    }
    

    if (mplayer.getRole() == Rel.LEADER)
    {
      mplayerFaction.promoteNewLeader();
    }
    mplayerFaction.uninvite(mplayer);
    mplayer.resetFactionData();
  }
}
