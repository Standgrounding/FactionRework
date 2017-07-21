package com.massivecraft.factions.entity;

import com.massivecraft.factions.Const;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.util.AsciiCompass;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import org.bukkit.ChatColor;

public class Board
  extends Entity<Board> implements BoardInterface
{
  public static final transient Type MAP_TYPE = new TypeToken() {}.getType();
  

  private ConcurrentSkipListMap<PS, TerritoryAccess> map;
  

  public static Board get(Object oid)
  {
    return (Board)BoardColl.get().get(oid);
  }
  





  public Board load(Board that)
  {
    map = map;
    
    return this;
  }
  

  public boolean isDefault()
  {
    if (map == null) return true;
    if (map.isEmpty()) return true;
    return false;
  }
  







  public Map<PS, TerritoryAccess> getMap() { return Collections.unmodifiableMap(map); }
  public Map<PS, TerritoryAccess> getMapRaw() { return map; }
  




  public Board()
  {
    map = new ConcurrentSkipListMap();
  }
  
  public Board(Map<PS, TerritoryAccess> map)
  {
    this.map = new ConcurrentSkipListMap(map);
  }
  







  public TerritoryAccess getTerritoryAccessAt(PS ps)
  {
    if (ps == null) return null;
    ps = ps.getChunkCoords(true);
    TerritoryAccess ret = (TerritoryAccess)map.get(ps);
    if ((ret == null) || (ret.getHostFaction() == null)) ret = TerritoryAccess.valueOf("none");
    return ret;
  }
  

  public Faction getFactionAt(PS ps)
  {
    if (ps == null) return null;
    TerritoryAccess ta = getTerritoryAccessAt(ps);
    return ta.getHostFaction();
  }
  



  public void setTerritoryAccessAt(PS ps, TerritoryAccess territoryAccess)
  {
    ps = ps.getChunkCoords(true);
    
    if ((territoryAccess == null) || ((territoryAccess.getHostFactionId().equals("none")) && (territoryAccess.isDefault())))
    {
      map.remove(ps);
    }
    else
    {
      map.put(ps, territoryAccess);
    }
    
    changed();
  }
  

  public void setFactionAt(PS ps, Faction faction)
  {
    TerritoryAccess territoryAccess = null;
    if (faction != null)
    {
      territoryAccess = TerritoryAccess.valueOf(faction.getId());
    }
    setTerritoryAccessAt(ps, territoryAccess);
  }
  



  public void removeAt(PS ps)
  {
    setTerritoryAccessAt(ps, null);
  }
  

  public void removeAll(Faction faction)
  {
    String factionId = faction.getId();
    
    for (Map.Entry<PS, TerritoryAccess> entry : map.entrySet())
    {
      TerritoryAccess territoryAccess = (TerritoryAccess)entry.getValue();
      if (territoryAccess.getHostFactionId().equals(factionId))
      {
        PS ps = (PS)entry.getKey();
        removeAt(ps);
      }
    }
  }
  


  public Set<PS> getChunks(Faction faction)
  {
    return getChunks(faction.getId());
  }
  

  public Set<PS> getChunks(String factionId)
  {
    Set<PS> ret = new HashSet();
    for (Map.Entry<PS, TerritoryAccess> entry : map.entrySet())
    {
      TerritoryAccess ta = (TerritoryAccess)entry.getValue();
      if (ta.getHostFactionId().equals(factionId))
      {
        PS ps = (PS)entry.getKey();
        ps = ps.withWorld(getId());
        ret.add(ps);
      } }
    return ret;
  }
  

  public Map<Faction, Set<PS>> getFactionToChunks()
  {
    Map<Faction, Set<PS>> ret = new MassiveMap();
    
    for (Map.Entry<PS, TerritoryAccess> entry : map.entrySet())
    {

      TerritoryAccess ta = (TerritoryAccess)entry.getValue();
      Faction faction = ta.getHostFaction();
      if (faction != null)
      {

        Set<PS> chunks = (Set)ret.get(faction);
        if (chunks == null)
        {
          chunks = new MassiveSet();
          ret.put(faction, chunks);
        }
        

        PS chunk = (PS)entry.getKey();
        chunk = chunk.withWorld(getId());
        chunks.add(chunk);
      }
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
    for (TerritoryAccess ta : map.values())
    {
      if (ta.getHostFactionId().equals(factionId))
        ret++;
    }
    return ret;
  }
  

  public Map<Faction, Integer> getFactionToCount()
  {
    Map<Faction, Integer> ret = new MassiveMap();
    
    for (Map.Entry<PS, TerritoryAccess> entry : map.entrySet())
    {

      TerritoryAccess ta = (TerritoryAccess)entry.getValue();
      Faction faction = ta.getHostFaction();
      if (faction != null)
      {

        Integer count = (Integer)ret.get(faction);
        if (count == null)
        {
          count = Integer.valueOf(0);
        }
        

        ret.put(faction, Integer.valueOf(count.intValue() + 1));
      }
    }
    return ret;
  }
  



  public boolean hasClaimed(Faction faction)
  {
    return hasClaimed(faction.getId());
  }
  

  public boolean hasClaimed(String factionId)
  {
    for (TerritoryAccess ta : map.values())
    {
      if (ta.getHostFactionId().equals(factionId))
        return true;
    }
    return false;
  }
  





  public boolean isBorderPs(PS ps)
  {
    ps = ps.getChunk(true);
    
    PS nearby = null;
    Faction faction = getFactionAt(ps);
    
    nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX().intValue() + 1));
    if (faction != getFactionAt(nearby)) { return true;
    }
    nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX().intValue() - 1));
    if (faction != getFactionAt(nearby)) { return true;
    }
    nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ().intValue() + 1));
    if (faction != getFactionAt(nearby)) { return true;
    }
    nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ().intValue() - 1));
    if (faction != getFactionAt(nearby)) { return true;
    }
    return false;
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
    ps = ps.getChunk(true);
    
    PS nearby = null;
    
    nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX().intValue() + 1));
    if (faction == getFactionAt(nearby)) { return true;
    }
    nearby = ps.withChunkX(Integer.valueOf(ps.getChunkX().intValue() - 1));
    if (faction == getFactionAt(nearby)) { return true;
    }
    nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ().intValue() + 1));
    if (faction == getFactionAt(nearby)) { return true;
    }
    nearby = ps.withChunkZ(Integer.valueOf(ps.getChunkZ().intValue() - 1));
    if (faction == getFactionAt(nearby)) { return true;
    }
    return false;
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
    centerPs = centerPs.getChunkCoords(true);
    
    List<Object> ret = new ArrayList();
    Faction centerFaction = getFactionAt(centerPs);
    
    ret.add(Txt.titleize("(" + centerPs.getChunkX() + "," + centerPs.getChunkZ() + ") " + centerFaction.getName(observer)));
    
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    width = halfWidth * 2 + 1;
    height = halfHeight * 2 + 1;
    
    PS topLeftPs = centerPs.plusChunkCoords(-halfWidth, -halfHeight);
    

    List<String> asciiCompass = AsciiCompass.getAsciiCompass(inDegrees);
    

    height--;
    
    Map<Faction, Character> fList = new HashMap();
    int chrIdx = 0;
    boolean overflown = false;
    
    StringBuilder row;
    for (int dz = 0; dz < height; dz++)
    {

      row = new StringBuilder();
      for (int dx = 0; dx < width; dx++)
      {
        if ((dx == halfWidth) && (dz == halfHeight))
        {
          row.append(Const.MAP_KEY_SEPARATOR);
        }
        else
        {
          if ((!overflown) && (chrIdx >= Const.MAP_KEY_CHARS.length)) { overflown = true;
          }
          PS herePs = topLeftPs.plusChunkCoords(dx, dz);
          Faction hereFaction = getFactionAt(herePs);
          boolean contains = fList.containsKey(hereFaction);
          if (hereFaction.isNone())
          {
            row.append(Const.MAP_KEY_WILDERNESS);
          }
          else if ((!contains) && (overflown))
          {
            row.append(Const.MAP_KEY_OVERFLOW);
          }
          else
          {
            if (!contains) fList.put(hereFaction, Character.valueOf(Const.MAP_KEY_CHARS[(chrIdx++)]));
            char fchar = ((Character)fList.get(hereFaction)).charValue();
            row.append(hereFaction.getColorTo(observer).toString()).append(fchar);
          }
        }
      }
      String line = row.toString();
      

      if (dz == 0) line = (String)asciiCompass.get(0) + line.substring(9);
      if (dz == 1) line = (String)asciiCompass.get(1) + line.substring(9);
      if (dz == 2) { line = (String)asciiCompass.get(2) + line.substring(9);
      }
      ret.add(line);
    }
    
    String fRow = "";
    for (Faction keyfaction : fList.keySet())
    {
      fRow = fRow + keyfaction.getColorTo(observer).toString() + fList.get(keyfaction) + ": " + keyfaction.getName() + " ";
    }
    if (overflown) fRow = fRow + Const.MAP_OVERFLOW_MESSAGE;
    fRow = fRow.trim();
    ret.add(fRow);
    
    return ret;
  }
}
