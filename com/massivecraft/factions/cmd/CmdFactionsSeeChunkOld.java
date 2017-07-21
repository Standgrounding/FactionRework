package com.massivecraft.factions.cmd;

import com.massivecraft.factions.util.VisualizeUtil;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSFormatHumanSpace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;



public class CmdFactionsSeeChunkOld
  extends FactionsCommand
{
  public CmdFactionsSeeChunkOld()
  {
    addAliases(new String[] { "sco" });
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  






  public void perform()
  {
    World world = me.getWorld();
    PS chunk = PS.valueOf(me.getLocation()).getChunk(true);
    int chunkX = chunk.getChunkX().intValue();
    int chunkZ = chunk.getChunkZ().intValue();
    




    int blockX = chunkX * 16;
    int blockZ = chunkZ * 16;
    showPillar(me, world, blockX, blockZ);
    
    blockX = chunkX * 16 + 15;
    blockZ = chunkZ * 16;
    showPillar(me, world, blockX, blockZ);
    
    blockX = chunkX * 16;
    blockZ = chunkZ * 16 + 15;
    showPillar(me, world, blockX, blockZ);
    
    blockX = chunkX * 16 + 15;
    blockZ = chunkZ * 16 + 15;
    showPillar(me, world, blockX, blockZ);
    

    msg("<i>Visualized %s", new Object[] { chunk.toString(PSFormatHumanSpace.get()) });
  }
  

  public static void showPillar(Player player, World world, int blockX, int blockZ)
  {
    for (int blockY = 0; blockY < world.getMaxHeight(); blockY++)
    {
      Location loc = new Location(world, blockX, blockY, blockZ);
      if (loc.getBlock().getType() == Material.AIR) {
        int typeId = blockY % 5 == 0 ? Material.GLOWSTONE.getId() : Material.GLASS.getId();
        VisualizeUtil.addLocation(player, loc, typeId);
      }
    }
  }
}
