package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.LinkedHashSet;
import java.util.Set;
import org.bukkit.entity.Player;





public class CmdFactionsSetFill
  extends CmdFactionsSetXSimple
{
  public CmdFactionsSetFill(boolean claim)
  {
    super(claim);
    

    addAliases(new String[] { "fill" });
    

    setFormatOne("<h>%s<i> %s <h>%d <i>chunk %s<i> using fill.");
    setFormatMany("<h>%s<i> %s <h>%d <i>chunks near %s<i> using fill.");
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
    Perm perm = claim ? Perm.CLAIM_FILL : Perm.UNCLAIM_FILL;
    addRequirements(new Requirement[] { RequirementHasPerm.get(perm) });
  }
  






  public Set<PS> getChunks()
  {
    PS chunk = PS.valueOf(me.getLocation()).getChunk(true);
    Set<PS> chunks = new LinkedHashSet();
    


    Faction color = BoardColl.get().getFactionAt(chunk);
    

    chunks.add(chunk);
    

    int max = getsetFillMax;
    floodSearch(chunks, color, max);
    

    if (chunks.size() >= max)
    {
      msg("<b>Fill limit of <h>%d <b>reached.", new Object[] { Integer.valueOf(max) });
      return null;
    }
    

    return chunks;
  }
  





  public static void floodSearch(Set<PS> set, Faction color, int max)
  {
    if (set == null) throw new NullPointerException("set");
    if (color == null) { throw new NullPointerException("color");
    }
    
    Set<PS> expansion = new LinkedHashSet();
    for (PS chunk : set)
    {
      Set<PS> neighbours = MUtil.set(new PS[] {chunk
        .withChunkX(Integer.valueOf(chunk.getChunkX().intValue() + 1)), chunk
        .withChunkX(Integer.valueOf(chunk.getChunkX().intValue() - 1)), chunk
        .withChunkZ(Integer.valueOf(chunk.getChunkZ().intValue() + 1)), chunk
        .withChunkZ(Integer.valueOf(chunk.getChunkZ().intValue() - 1)) });
      

      for (PS neighbour : neighbours)
      {
        if (!set.contains(neighbour)) {
          Faction faction = BoardColl.get().getFactionAt(neighbour);
          if ((faction != null) && 
            (faction == color))
            expansion.add(neighbour);
        } }
    }
    set.addAll(expansion);
    

    if (expansion.isEmpty()) { return;
    }
    
    if (set.size() >= max) { return;
    }
    
    floodSearch(set, color, max);
  }
}
