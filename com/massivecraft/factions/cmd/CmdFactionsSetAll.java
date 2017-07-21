package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.mixin.MixinWorld;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.bukkit.World;
import org.bukkit.entity.Player;



public class CmdFactionsSetAll
  extends CmdFactionsSetXAll
{
  public static final List<String> LIST_ALL = Collections.unmodifiableList(MUtil.list(new String[] { "a", "al", "all" }));
  public static final List<String> LIST_MAP = Collections.singletonList("map");
  





  public CmdFactionsSetAll(boolean claim)
  {
    super(claim);
    

    addAliases(new String[] { "all" });
    

    Perm perm = claim ? Perm.CLAIM_ALL : Perm.UNCLAIM_ALL;
    addRequirements(new Requirement[] { RequirementHasPerm.get(perm) });
  }
  





  public Set<PS> getChunks()
    throws MassiveException
  {
    String word = isClaim() ? "claim" : "unclaim";
    

    Set<PS> chunks = null;
    

    Faction oldFaction = getOldFaction();
    
    if (LIST_ALL.contains(argAt(0).toLowerCase()))
    {
      chunks = BoardColl.get().getChunks(oldFaction);
      setFormatOne("<h>%s<i> %s <h>%d <i>chunk using " + word + " all.");
      setFormatMany("<h>%s<i> %s <h>%d <i>chunks using " + word + " all.");
    }
    else
    {
      String worldId = null;
      if (LIST_MAP.contains(argAt(0).toLowerCase()))
      {
        if (me != null)
        {
          worldId = me.getWorld().getName();
        }
        else
        {
          msg("<b>You must specify which map from console.");
          return null;
        }
      }
      else
      {
        worldId = argAt(0);
        if (worldId == null) return null;
      }
      Board board = (Board)BoardColl.get().get(worldId);
      chunks = board.getChunks(oldFaction);
      String worldDisplayName = MixinWorld.get().getWorldDisplayName(worldId);
      setFormatOne("<h>%s<i> %s <h>%d <i>chunk using " + word + " <h>" + worldDisplayName + "<i>.");
      setFormatMany("<h>%s<i> %s <h>%d <i>chunks using " + word + " <h>" + worldDisplayName + "<i>.");
    }
    

    return chunks;
  }
}
