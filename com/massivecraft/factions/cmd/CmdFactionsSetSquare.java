package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import java.util.LinkedHashSet;
import java.util.Set;
import org.bukkit.entity.Player;





public class CmdFactionsSetSquare
  extends CmdFactionsSetXRadius
{
  public CmdFactionsSetSquare(boolean claim)
  {
    super(claim);
    

    addAliases(new String[] { "square" });
    

    setFormatOne("<h>%s<i> %s <h>%d <i>chunk %s<i> using square.");
    setFormatMany("<h>%s<i> %s <h>%d <i>chunks near %s<i> using square.");
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
    Perm perm = claim ? Perm.CLAIM_SQUARE : Perm.UNCLAIM_SQUARE;
    addRequirements(new Requirement[] { RequirementHasPerm.get(perm) });
  }
  





  public Set<PS> getChunks()
    throws MassiveException
  {
    PS chunk = PS.valueOf(me.getLocation()).getChunk(true);
    Set<PS> chunks = new LinkedHashSet();
    
    chunks.add(chunk);
    
    Integer radiusZero = getRadiusZero();
    
    for (int dx = -radiusZero.intValue(); dx <= radiusZero.intValue(); dx++)
    {
      for (int dz = -radiusZero.intValue(); dz <= radiusZero.intValue(); dz++)
      {
        int x = chunk.getChunkX().intValue() + dx;
        int z = chunk.getChunkZ().intValue() + dz;
        
        chunks.add(chunk.withChunkX(Integer.valueOf(x)).withChunkZ(Integer.valueOf(z)));
      }
    }
    
    return chunks;
  }
}
