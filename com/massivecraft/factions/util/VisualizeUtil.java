package com.massivecraft.factions.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;



public class VisualizeUtil
{
  public VisualizeUtil() {}
  
  protected static Map<UUID, Set<Location>> playerLocations = new HashMap();
  
  public static Set<Location> getPlayerLocations(Player player) {
    return getPlayerLocations(player.getUniqueId());
  }
  
  public static Set<Location> getPlayerLocations(UUID uuid) {
    Set<Location> ret = (Set)playerLocations.get(uuid);
    if (ret == null)
    {
      ret = new HashSet();
      playerLocations.put(uuid, ret);
    }
    return ret;
  }
  





  public static void addLocation(Player player, Location location, int typeId, byte data)
  {
    getPlayerLocations(player).add(location);
    player.sendBlockChange(location, typeId, data);
  }
  

  public static void addLocation(Player player, Location location, int typeId)
  {
    getPlayerLocations(player).add(location);
    player.sendBlockChange(location, typeId, (byte)0);
  }
  





  public static void addLocations(Player player, Map<Location, Integer> locationMaterialIds)
  {
    Set<Location> ploc = getPlayerLocations(player);
    for (Map.Entry<Location, Integer> entry : locationMaterialIds.entrySet())
    {
      ploc.add(entry.getKey());
      player.sendBlockChange((Location)entry.getKey(), ((Integer)entry.getValue()).intValue(), (byte)0);
    }
  }
  

  public static void addLocations(Player player, Collection<Location> locations, int typeId)
  {
    Set<Location> ploc = getPlayerLocations(player);
    for (Location location : locations)
    {
      ploc.add(location);
      player.sendBlockChange(location, typeId, (byte)0);
    }
  }
  

  public static void addBlocks(Player player, Collection<Block> blocks, int typeId)
  {
    Set<Location> ploc = getPlayerLocations(player);
    for (Block block : blocks)
    {
      Location location = block.getLocation();
      ploc.add(location);
      player.sendBlockChange(location, typeId, (byte)0);
    }
  }
  





  public static void clear(Player player)
  {
    Set<Location> locations = getPlayerLocations(player);
    if (locations == null) return;
    for (Location location : locations)
    {
      Block block = location.getWorld().getBlockAt(location);
      player.sendBlockChange(location, block.getTypeId(), block.getData());
    }
    locations.clear();
  }
}
