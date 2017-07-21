package com.massivecraft.factions.entity;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;




public class BoardColl
  extends Coll<Board>
  implements BoardInterface
{
  private static BoardColl i = new BoardColl();
  public static BoardColl get() { return i; }
  
  private BoardColl() {
    setCreative(true);
    setLowercasing(true);
  }
  





  public void onTick()
  {
    super.onTick();
  }
  





  public String fixId(Object oid)
  {
    if (oid == null) return null;
    if ((oid instanceof String)) return (String)oid;
    if ((oid instanceof Board)) { return ((Board)oid).getId();
    }
    return (String)MUtil.extract(String.class, "worldName", oid);
  }
  





  public TerritoryAccess getTerritoryAccessAt(PS ps)
  {
    if (ps == null) return null;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return null;
    return board.getTerritoryAccessAt(ps);
  }
  

  public Faction getFactionAt(PS ps)
  {
    if (ps == null) return null;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return null;
    return board.getFactionAt(ps);
  }
  



  public void setTerritoryAccessAt(PS ps, TerritoryAccess territoryAccess)
  {
    if (ps == null) return;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return;
    board.setTerritoryAccessAt(ps, territoryAccess);
  }
  

  public void setFactionAt(PS ps, Faction faction)
  {
    if (ps == null) return;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return;
    board.setFactionAt(ps, faction);
  }
  



  public void removeAt(PS ps)
  {
    if (ps == null) return;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return;
    board.removeAt(ps);
  }
  

  public void removeAll(Faction faction)
  {
    for (Board board : getAll())
    {
      board.removeAll(faction);
    }
  }
  




  public Set<PS> getChunks(Faction faction)
  {
    Set<PS> ret = new HashSet();
    

    for (Board board : getAll())
    {
      ret.addAll(board.getChunks(faction));
    }
    

    return ret;
  }
  


  public Set<PS> getChunks(String factionId)
  {
    Set<PS> ret = new HashSet();
    

    for (Board board : getAll())
    {
      ret.addAll(board.getChunks(factionId));
    }
    

    return ret;
  }
  


  public Map<Faction, Set<PS>> getFactionToChunks()
  {
    Map<Faction, Set<PS>> ret = null;
    

    for (Board board : getAll())
    {

      Map<Faction, Set<PS>> factionToChunks = board.getFactionToChunks();
      if (ret == null)
      {
        ret = factionToChunks;

      }
      else
      {
        for (Map.Entry<Faction, Set<PS>> entry : factionToChunks.entrySet())
        {
          Faction faction = (Faction)entry.getKey();
          Set<PS> chunks = (Set)ret.get(faction);
          if (chunks == null)
          {
            ret.put(faction, entry.getValue());
          }
          else
          {
            chunks.addAll((Collection)entry.getValue());
          }
        }
      }
    }
    
    if (ret == null) { ret = new MassiveMap();
    }
    
    return ret;
  }
  



  public int getCount(Faction faction)
  {
    return getCount(faction.getId());
  }
  

  public int getCount(String factionId)
  {
    int ret = 0;
    for (Board board : getAll())
    {
      ret += board.getCount(factionId);
    }
    return ret;
  }
  

  public Map<Faction, Integer> getFactionToCount()
  {
    Map<Faction, Integer> ret = null;
    for (Board board : getAll())
    {

      Map<Faction, Integer> factionToCount = board.getFactionToCount();
      if (ret == null)
      {
        ret = factionToCount;

      }
      else
      {
        for (Map.Entry<Faction, Integer> entry : factionToCount.entrySet())
        {
          Faction faction = (Faction)entry.getKey();
          Integer count = (Integer)ret.get(faction);
          if (count == null)
          {
            ret.put(faction, entry.getValue());
          }
          else
          {
            ret.put(faction, Integer.valueOf(count.intValue() + ((Integer)entry.getValue()).intValue()));
          }
        }
      }
    }
    if (ret == null) ret = new MassiveMap();
    return ret;
  }
  



  public boolean hasClaimed(Faction faction)
  {
    return hasClaimed(faction.getId());
  }
  

  public boolean hasClaimed(String factionId)
  {
    for (Board board : getAll())
    {
      if (board.hasClaimed(factionId)) return true;
    }
    return false;
  }
  



  public boolean isBorderPs(PS ps)
  {
    if (ps == null) return false;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return false;
    return board.isBorderPs(ps);
  }
  

  public boolean isAnyBorderPs(Set<PS> pss)
  {
    for (PS ps : pss)
    {
      if (isBorderPs(ps)) return true;
    }
    return false;
  }
  

  public boolean isConnectedPs(PS ps, Faction faction)
  {
    if (ps == null) return false;
    Board board = (Board)get(ps.getWorld());
    if (board == null) return false;
    return board.isConnectedPs(ps, faction);
  }
  

  public boolean isAnyConnectedPs(Set<PS> pss, Faction faction)
  {
    for (PS ps : pss)
    {
      if (isConnectedPs(ps, faction)) return true;
    }
    return false;
  }
  



  public List<Object> getMap(RelationParticipator observer, PS centerPs, double inDegrees, int width, int height)
  {
    if (centerPs == null) return null;
    Board board = (Board)get(centerPs.getWorld());
    if (board == null) return null;
    return board.getMap(observer, centerPs, inDegrees, width, height);
  }
  




  public Set<String> getClaimedWorlds(Faction faction)
  {
    return getClaimedWorlds(faction.getId());
  }
  

  public Set<String> getClaimedWorlds(String factionId)
  {
    Set<String> ret = new MassiveSet();
    

    for (Board board : getAll())
    {
      if (board.hasClaimed(factionId)) { ret.add(board.getId());
      }
    }
    
    return ret;
  }
  








  public static Set<PS> getNearbyChunks(PS psChunk, int distance)
  {
    if (psChunk == null) throw new NullPointerException("psChunk");
    psChunk = psChunk.getChunk(true);
    

    Set<PS> ret = new LinkedHashSet();
    if (distance < 0) { return ret;
    }
    
    int chunkX = psChunk.getChunkX().intValue();
    int xmin = chunkX - distance;
    int xmax = chunkX + distance;
    
    int chunkZ = psChunk.getChunkZ().intValue();
    int zmin = chunkZ - distance;
    int zmax = chunkZ + distance;
    
    for (int x = xmin; x <= xmax; x++)
    {
      PS psChunkX = psChunk.withChunkX(Integer.valueOf(x));
      for (int z = zmin; z <= zmax; z++)
      {
        ret.add(psChunkX.withChunkZ(Integer.valueOf(z)));
      }
    }
    

    return ret;
  }
  

  public static Set<PS> getNearbyChunks(Collection<PS> chunks, int distance)
  {
    if (chunks == null) { throw new NullPointerException("chunks");
    }
    
    Set<PS> ret = new LinkedHashSet();
    
    if (distance < 0) { return ret;
    }
    
    for (PS chunk : chunks)
    {
      ret.addAll(getNearbyChunks(chunk, distance));
    }
    

    return ret;
  }
  

  public static Set<Faction> getDistinctFactions(Collection<PS> chunks)
  {
    if (chunks == null) { throw new NullPointerException("chunks");
    }
    
    Set<Faction> ret = new LinkedHashSet();
    

    for (PS chunk : chunks)
    {
      Faction faction = get().getFactionAt(chunk);
      if (faction != null) {
        ret.add(faction);
      }
    }
    
    return ret;
  }
  

  public static Map<PS, Faction> getChunkFaction(Collection<PS> chunks)
  {
    Map<PS, Faction> ret = new LinkedHashMap();
    

    Faction none = FactionColl.get().getNone();
    for (PS chunk : chunks)
    {
      chunk = chunk.getChunk(true);
      Faction faction = get().getFactionAt(chunk);
      if (faction == null) faction = none;
      ret.put(chunk, faction);
    }
    

    return ret;
  }
}
