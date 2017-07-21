package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave;
import com.massivecraft.massivecore.particleeffect.ParticleEffect;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.PeriodUtil;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;



public class EngineSeeChunk
  extends Engine
{
  private static EngineSeeChunk i = new EngineSeeChunk();
  public static EngineSeeChunk get() { return i; }
  
  public EngineSeeChunk() {
    setPeriod(Long.valueOf(1L));
  }
  




  public static void leaveAndWorldChangeRemoval(Player player)
  {
    if (MUtil.isntPlayer(player)) { return;
    }
    MPlayer mplayer = MPlayer.get(player);
    mplayer.setSeeingChunk(false);
  }
  

  @EventHandler(priority=EventPriority.NORMAL)
  public void leaveAndWorldChangeRemoval(EventMassiveCorePlayerLeave event)
  {
    leaveAndWorldChangeRemoval(event.getPlayer());
  }
  

  @EventHandler(priority=EventPriority.NORMAL)
  public void leaveAndWorldChangeRemoval(PlayerChangedWorldEvent event)
  {
    leaveAndWorldChangeRemoval(event.getPlayer());
  }
  






  public void run()
  {
    long now = System.currentTimeMillis();
    long length = getseeChunkPeriodMillis;
    if (!PeriodUtil.isNewPeriod(this, length, now)) { return;
    }
    
    long period = PeriodUtil.getPeriod(length, now);
    

    int steps = getseeChunkSteps;
    int step = (int)(period % steps);
    

    float offsetX = 0.0F;
    float offsetY = getseeChunkParticleOffsetY;
    float offsetZ = 0.0F;
    float speed = 0.0F;
    int amount = getseeChunkParticleAmount;
    

    for (Iterator localIterator1 = MUtil.getOnlinePlayers().iterator(); localIterator1.hasNext();) { player = (Player)localIterator1.next();
      

      if (!player.isDead())
      {

        MPlayer mplayer = MPlayer.get(player);
        if (mplayer.isSeeingChunk())
        {

          List<Location> locations = getLocations(player, steps, step);
          for (Location location : locations)
          {
            ParticleEffect.EXPLOSION_NORMAL.display(location, 0.0F, offsetY, 0.0F, 0.0F, amount, new Player[] { player }); }
        }
      }
    }
    Player player;
  }
  
  public static List<Location> getLocations(Player player, int steps, int step) {
    if (player == null) throw new NullPointerException("player");
    if (steps < 1) throw new InvalidParameterException("steps must be larger than 0");
    if (step < 0) throw new InvalidParameterException("step must at least be 0");
    if (step >= steps) { throw new InvalidParameterException("step must be less than steps");
    }
    
    List<Location> ret = new ArrayList();
    
    Location location = player.getLocation();
    World world = location.getWorld();
    PS chunk = PS.valueOf(location).getChunk(true);
    
    int xmin = chunk.getChunkX().intValue() * 16;
    int xmax = xmin + 15;
    double y = location.getBlockY() + getseeChunkParticleDeltaY;
    int zmin = chunk.getChunkZ().intValue() * 16;
    int zmax = zmin + 15;
    
    int keepEvery = getseeChunkKeepEvery;
    if (keepEvery <= 0) { keepEvery = Integer.MAX_VALUE;
    }
    int skipEvery = getseeChunkSkipEvery;
    if (skipEvery <= 0) { skipEvery = Integer.MAX_VALUE;
    }
    int x = xmin;
    int z = zmin;
    int i = 0;
    for (; 
        
        x + 1 <= xmax; 
        


        ret.add(new Location(world, x + 0.5D, y + 0.5D, z + 0.5D)))
    {
      label193:
      x++;
      i++;
      if ((i % steps != step) || (i % keepEvery != 0) || (i % skipEvery == 0)) {
        break label193;
      }
    }
    for (; z + 1 <= zmax; 
        


        ret.add(new Location(world, x + 0.5D, y + 0.5D, z + 0.5D)))
    {
      label271:
      z++;
      i++;
      if ((i % steps != step) || (i % keepEvery != 0) || (i % skipEvery == 0)) {
        break label271;
      }
    }
    for (; x - 1 >= xmin; 
        


        ret.add(new Location(world, x + 0.5D, y + 0.5D, z + 0.5D)))
    {
      label349:
      x--;
      i++;
      if ((i % steps != step) || (i % keepEvery != 0) || (i % skipEvery == 0)) {
        break label349;
      }
    }
    for (; z - 1 >= zmin; 
        


        ret.add(new Location(world, x + 0.5D, y + 0.5D, z + 0.5D)))
    {
      label427:
      z--;
      i++;
      if ((i % steps != step) || (i % keepEvery != 0) || (i % skipEvery == 0)) {
        break label427;
      }
    }
    return ret;
  }
}
