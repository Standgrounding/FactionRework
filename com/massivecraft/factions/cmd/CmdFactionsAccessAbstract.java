package com.massivecraft.factions.cmd;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSFormatHumanSpace;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.entity.Player;









public abstract class CmdFactionsAccessAbstract
  extends FactionsCommand
{
  public PS chunk;
  public TerritoryAccess ta;
  public Faction hostFaction;
  
  public CmdFactionsAccessAbstract()
  {
    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    chunk = PS.valueOf(me.getLocation()).getChunk(true);
    ta = BoardColl.get().getTerritoryAccessAt(chunk);
    hostFaction = ta.getHostFaction();
    
    innerPerform();
  }
  
  public abstract void innerPerform() throws MassiveException;
  
  public void sendAccessInfo()
  {
    Object title = "Access at " + chunk.toString(PSFormatHumanSpace.get());
    title = Txt.titleize(title);
    message(title);
    
    msg("<k>Host Faction: %s", new Object[] { hostFaction.describeTo(msender, true) });
    msg("<k>Host Faction Allowed: %s", new Object[] { ta.isHostFactionAllowed() ? Txt.parse("<lime>TRUE") : Txt.parse("<rose>FALSE") });
    msg("<k>Granted Players: %s", new Object[] { describeRelationParticipators(ta.getGrantedMPlayers(), msender) });
    msg("<k>Granted Factions: %s", new Object[] { describeRelationParticipators(ta.getGrantedFactions(), msender) });
  }
  
  public static String describeRelationParticipators(Collection<? extends RelationParticipator> relationParticipators, RelationParticipator observer)
  {
    if (relationParticipators.size() == 0) return Txt.parse("<silver><em>none");
    List<String> descriptions = new ArrayList();
    for (RelationParticipator relationParticipator : relationParticipators)
    {
      descriptions.add(relationParticipator.describeTo(observer));
    }
    return Txt.implodeCommaAnd(descriptions, Txt.parse("<i>, "), Txt.parse(" <i>and "));
  }
}
