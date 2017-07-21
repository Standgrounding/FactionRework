package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.mixin.MixinTeleport;
import com.massivecraft.massivecore.mixin.TeleporterException;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.teleport.Destination;
import com.massivecraft.massivecore.teleport.DestinationSimple;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;




public class CmdFactionsUnstuck
  extends FactionsCommand
{
  public CmdFactionsUnstuck()
  {
    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  





  public void perform()
    throws MassiveException
  {
    PS center = PS.valueOf(me.getLocation().getChunk());
    

    if (isFree(msender, center))
    {
      msg("<b>You don't seem to be stuck.");
      return;
    }
    

    Location location = getNearestFreeTopLocation(msender, center);
    if (location == null)
    {
      msg("<b>No nearby chunk with %s<b> or build rights found.", new Object[] { FactionColl.get().getNone().describeTo(msender) });
      return;
    }
    

    Destination destination = new DestinationSimple(PS.valueOf(location));
    try
    {
      MixinTeleport.get().teleport(me, destination, getunstuckSeconds);
    }
    catch (TeleporterException e)
    {
      msg("<b>%s", new Object[] { e.getMessage() });
    }
  }
  

  public static Location getNearestFreeTopLocation(MPlayer mplayer, PS ps)
  {
    List<PS> chunks = getChunkSpiral(ps);
    for (PS chunk : chunks)
    {
      if (isFree(mplayer, chunk)) {
        Location ret = getTopLocation(chunk);
        if (ret != null)
          return ret;
      } }
    return null;
  }
  

  public static Location getTopLocation(PS ps)
  {
    Location ret = null;
    try
    {
      World world = ps.asBukkitWorld();
      
      int blockX = ps.getBlockX(true).intValue();
      int blockZ = ps.getBlockZ(true).intValue();
      int blockY = world.getHighestBlockYAt(blockX, blockZ);
      

      if (blockY <= 1) { return null;
      }
      double locationX = blockX + 0.5D;
      double locationY = blockY + 0.5D;
      double locationZ = blockZ + 0.5D;
      
      ret = new Location(world, locationX, locationY, locationZ);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return ret;
  }
  

  public static boolean isFree(MPlayer mplayer, PS ps)
  {
    Faction faction = BoardColl.get().getFactionAt(ps);
    if (faction.isNone()) return true;
    return MPerm.getPermBuild().has(mplayer, ps, false);
  }
  


  public static List<PS> getChunkSpiral(PS center)
  {
    List<PS> ret = new ArrayList();
    

    center = center.getChunk(true);
    int centerX = center.getChunkX().intValue();
    int centerZ = center.getChunkZ().intValue();
    
    for (int delta = 1; delta <= getunstuckChunkRadius; delta++)
    {
      int minX = centerX - delta;
      int maxX = centerX + delta;
      int minZ = centerZ - delta;
      int maxZ = centerZ + delta;
      
      for (int x = minX; x <= maxX; x++)
      {
        ret.add(center.withChunkX(Integer.valueOf(x)).withChunkZ(Integer.valueOf(minZ)));
        ret.add(center.withChunkX(Integer.valueOf(x)).withChunkZ(Integer.valueOf(maxZ)));
      }
      
      for (int z = minZ + 1; z <= maxZ - 1; z++)
      {
        ret.add(center.withChunkX(Integer.valueOf(minX)).withChunkZ(Integer.valueOf(z)));
        ret.add(center.withChunkX(Integer.valueOf(maxX)).withChunkZ(Integer.valueOf(z)));
      }
    }
    

    return ret;
  }
}
