package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.Txt;





public class CmdFactionsDisband
  extends FactionsCommand
{
  public CmdFactionsDisband()
  {
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg(msenderFaction);
    

    if (!MPerm.getPermDisband().has(msender, faction, true)) { return;
    }
    
    if (faction.getFlag(MFlag.getFlagPermanent()))
    {
      msg("<i>This faction is designated as permanent, so you cannot disband it.");
      return;
    }
    

    EventFactionsDisband event = new EventFactionsDisband(me, faction);
    event.run();
    if (event.isCancelled()) { return;
    }
    


    for (MPlayer mplayer : faction.getMPlayers())
    {
      EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(sender, mplayer, FactionColl.get().getNone(), EventFactionsMembershipChange.MembershipChangeReason.DISBAND);
      membershipChangeEvent.run();
    }
    

    for (MPlayer mplayer : faction.getMPlayersWhereOnline(true))
    {
      mplayer.msg("<h>%s<i> disbanded your faction.", new Object[] { msender.describeTo(mplayer) });
    }
    
    if (msenderFaction != faction)
    {
      msender.msg("<i>You disbanded <h>%s<i>.", new Object[] { faction.describeTo(msender) });
    }
    

    if (getlogFactionDisband)
    {
      Factions.get().log(new Object[] { Txt.parse("<i>The faction <h>%s <i>(<h>%s<i>) was disbanded by <h>%s<i>.", new Object[] { faction.getName(), faction.getId(), msender.getDisplayName(IdUtil.getConsole()) }) });
    }
    

    faction.detach();
  }
}
