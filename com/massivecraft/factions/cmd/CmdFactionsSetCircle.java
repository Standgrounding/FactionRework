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





public class CmdFactionsSetCircle
  extends CmdFactionsSetXRadius
{
  public CmdFactionsSetCircle(boolean claim)
  {
    super(claim);
    

    addAliases(new String[] { "circle" });
    

    setFormatOne("<h>%s<i> %s <h>%d <i>chunk %s<i> using circle.");
    setFormatMany("<h>%s<i> %s <h>%d <i>chunks near %s<i> using circle.");
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
    Perm perm = claim ? Perm.CLAIM_CIRCLE : Perm.UNCLAIM_CIRCLE;
    addRequirements(new Requirement[] { RequirementHasPerm.get(perm) });
  }
  





  public Set<PS> getChunks()
    throws MassiveException
  {
    PS chunk = PS.valueOf(me.getLocation()).getChunk(true);
    Set<PS> chunks = new LinkedHashSet();
    
    chunks.add(chunk);
    
    Integer radiusZero = getRadiusZero();
    double radiusSquared = radiusZero.intValue() * radiusZero.intValue();
    
    for (int dx = -radiusZero.intValue(); dx <= radiusZero.intValue(); dx++)
    {
      for (int dz = -radiusZero.intValue(); dz <= radiusZero.intValue(); dz++)
      {
        if (dx * dx + dz * dz <= radiusSquared)
        {
          int x = chunk.getChunkX().intValue() + dx;
          int z = chunk.getChunkZ().intValue() + dz;
          
          chunks.add(chunk.withChunkX(Integer.valueOf(x)).withChunkZ(Integer.valueOf(z)));
        }
      }
    }
    return chunks;
  }
}
