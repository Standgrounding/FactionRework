package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import java.util.Collections;
import java.util.Set;
import org.bukkit.entity.Player;



public class CmdFactionsSetAuto
  extends FactionsCommand
{
  private boolean claim = true;
  public boolean isClaim() { return claim; }
  public void setClaim(boolean claim) { this.claim = claim; }
  





  public CmdFactionsSetAuto(boolean claim)
  {
    setClaim(claim);
    setSetupEnabled(false);
    

    addAliases(new String[] { "auto" });
    

    if (claim)
    {
      addParameter(TypeFaction.get(), "faction", "you");
    }
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
    Perm perm = claim ? Perm.CLAIM_AUTO : Perm.UNCLAIM_AUTO;
    addRequirements(new Requirement[] { RequirementHasPerm.get(perm) });
  }
  


  public void perform()
    throws MassiveException
  {
    Faction newFaction;
    
    Faction newFaction;
    
    if (claim)
    {
      newFaction = (Faction)readArg(msenderFaction);
    }
    else
    {
      newFaction = FactionColl.get().getNone();
    }
    

    if ((newFaction == null) || (newFaction == msender.getAutoClaimFaction()))
    {
      msender.setAutoClaimFaction(null);
      msg("<i>Disabled auto-setting as you walk around.");
      return;
    }
    

    if ((newFaction.isNormal()) && (!MPerm.getPermTerritory().has(msender, newFaction, true))) { return;
    }
    
    msender.setAutoClaimFaction(newFaction);
    msg("<i>Now auto-setting <h>%s<i> land.", new Object[] { newFaction.describeTo(msender) });
    

    PS chunk = PS.valueOf(me.getLocation()).getChunk(true);
    Set<PS> chunks = Collections.singleton(chunk);
    

    msender.tryClaim(newFaction, chunks);
  }
}
