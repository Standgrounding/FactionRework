package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import java.util.Collections;
import java.util.Set;
import org.bukkit.entity.Player;





public class CmdFactionsSetOne
  extends CmdFactionsSetXSimple
{
  public CmdFactionsSetOne(boolean claim)
  {
    super(claim);
    

    addAliases(new String[] { "one" });
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
    Perm perm = claim ? Perm.CLAIM_ONE : Perm.UNCLAIM_ONE;
    addRequirements(new Requirement[] { RequirementHasPerm.get(perm) });
  }
  





  public Set<PS> getChunks()
  {
    PS chunk = PS.valueOf(me.getLocation()).getChunk(true);
    Set<PS> chunks = Collections.singleton(chunk);
    return chunks;
  }
}
