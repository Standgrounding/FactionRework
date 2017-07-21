package com.massivecraft.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.collections.MassiveSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;


public class TerritoryAccess
{
  private final String hostFactionId;
  private final boolean hostFactionAllowed;
  private final Set<String> factionIds;
  private final Set<String> playerIds;
  
  public String getHostFactionId()
  {
    return hostFactionId;
  }
  
  public boolean isHostFactionAllowed() {
    return hostFactionAllowed;
  }
  
  public Set<String> getFactionIds() {
    return factionIds;
  }
  
  public Set<String> getPlayerIds() {
    return playerIds;
  }
  




  public TerritoryAccess withHostFactionId(String hostFactionId) { return valueOf(hostFactionId, Boolean.valueOf(hostFactionAllowed), factionIds, playerIds); }
  public TerritoryAccess withHostFactionAllowed(Boolean hostFactionAllowed) { return valueOf(hostFactionId, hostFactionAllowed, factionIds, playerIds); }
  public TerritoryAccess withFactionIds(Collection<String> factionIds) { return valueOf(hostFactionId, Boolean.valueOf(hostFactionAllowed), factionIds, playerIds); }
  public TerritoryAccess withPlayerIds(Collection<String> playerIds) { return valueOf(hostFactionId, Boolean.valueOf(hostFactionAllowed), factionIds, playerIds); }
  

  public TerritoryAccess withFactionId(String factionId, boolean with)
  {
    if (getHostFactionId().equals(factionId))
    {
      return valueOf(hostFactionId, Boolean.valueOf(with), this.factionIds, playerIds);
    }
    
    Set<String> factionIds = new MassiveSet(getFactionIds());
    if (with)
    {
      factionIds.add(factionId);
    }
    else
    {
      factionIds.remove(factionId);
    }
    return valueOf(hostFactionId, Boolean.valueOf(hostFactionAllowed), factionIds, playerIds);
  }
  
  public TerritoryAccess withPlayerId(String playerId, boolean with)
  {
    playerId = playerId.toLowerCase();
    Set<String> playerIds = new MassiveSet(getPlayerIds());
    if (with)
    {
      playerIds.add(playerId);
    }
    else
    {
      playerIds.remove(playerId);
    }
    return valueOf(hostFactionId, Boolean.valueOf(hostFactionAllowed), factionIds, playerIds);
  }
  






  public Faction getHostFaction()
  {
    return Faction.get(getHostFactionId());
  }
  

  public Set<MPlayer> getGrantedMPlayers()
  {
    Set<MPlayer> ret = new MassiveSet();
    

    for (String playerId : getPlayerIds())
    {
      ret.add(MPlayer.get(playerId));
    }
    

    return ret;
  }
  

  public Set<Faction> getGrantedFactions()
  {
    Set<Faction> ret = new MassiveSet();
    

    for (String factionId : getFactionIds())
    {
      Faction faction = Faction.get(factionId);
      if (faction != null) {
        ret.add(faction);
      }
    }
    
    return ret;
  }
  




  private TerritoryAccess(String hostFactionId, Boolean hostFactionAllowed, Collection<String> factionIds, Collection<String> playerIds)
  {
    if (hostFactionId == null) throw new IllegalArgumentException("hostFactionId was null");
    this.hostFactionId = hostFactionId;
    
    Set<String> factionIdsInner = new MassiveSet();
    if (factionIds != null)
    {
      factionIdsInner.addAll(factionIds);
      if (factionIdsInner.remove(hostFactionId))
      {
        hostFactionAllowed = Boolean.valueOf(true);
      }
    }
    this.factionIds = Collections.unmodifiableSet(factionIdsInner);
    
    Set<String> playerIdsInner = new MassiveSet();
    if (playerIds != null)
    {
      for (String playerId : playerIds)
      {
        playerIdsInner.add(playerId.toLowerCase());
      }
    }
    this.playerIds = Collections.unmodifiableSet(playerIdsInner);
    
    this.hostFactionAllowed = ((hostFactionAllowed == null) || (hostFactionAllowed.booleanValue()));
  }
  




  public static TerritoryAccess valueOf(String hostFactionId, Boolean hostFactionAllowed, Collection<String> factionIds, Collection<String> playerIds)
  {
    return new TerritoryAccess(hostFactionId, hostFactionAllowed, factionIds, playerIds);
  }
  
  public static TerritoryAccess valueOf(String hostFactionId)
  {
    return valueOf(hostFactionId, null, null, null);
  }
  




  public boolean isFactionGranted(Faction faction)
  {
    String factionId = faction.getId();
    
    if (getHostFactionId().equals(factionId))
    {
      return isHostFactionAllowed();
    }
    
    return getFactionIds().contains(factionId);
  }
  


  public boolean isMPlayerGranted(MPlayer mplayer)
  {
    String mplayerId = mplayer.getId();
    return getPlayerIds().contains(mplayerId);
  }
  


  public boolean isDefault()
  {
    return (isHostFactionAllowed()) && (getFactionIds().isEmpty()) && (getPlayerIds().isEmpty());
  }
  







  public Boolean hasTerritoryAccess(MPlayer mplayer)
  {
    if (isMPlayerGranted(mplayer)) { return Boolean.valueOf(true);
    }
    String factionId = mplayer.getFaction().getId();
    if (getFactionIds().contains(factionId)) { return Boolean.valueOf(true);
    }
    if ((getHostFactionId().equals(factionId)) && (!isHostFactionAllowed())) { return Boolean.valueOf(false);
    }
    return null;
  }
}
