package com.massivecraft.factions.entity;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.massivecore.ps.PS;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface BoardInterface
{
  public abstract TerritoryAccess getTerritoryAccessAt(PS paramPS);
  
  public abstract Faction getFactionAt(PS paramPS);
  
  public abstract void setTerritoryAccessAt(PS paramPS, TerritoryAccess paramTerritoryAccess);
  
  public abstract void setFactionAt(PS paramPS, Faction paramFaction);
  
  public abstract void removeAt(PS paramPS);
  
  public abstract void removeAll(Faction paramFaction);
  
  public abstract Set<PS> getChunks(Faction paramFaction);
  
  public abstract Set<PS> getChunks(String paramString);
  
  public abstract Map<Faction, Set<PS>> getFactionToChunks();
  
  public abstract int getCount(Faction paramFaction);
  
  public abstract int getCount(String paramString);
  
  public abstract Map<Faction, Integer> getFactionToCount();
  
  public abstract boolean hasClaimed(Faction paramFaction);
  
  public abstract boolean hasClaimed(String paramString);
  
  public abstract boolean isBorderPs(PS paramPS);
  
  public abstract boolean isAnyBorderPs(Set<PS> paramSet);
  
  public abstract boolean isConnectedPs(PS paramPS, Faction paramFaction);
  
  public abstract boolean isAnyConnectedPs(Set<PS> paramSet, Faction paramFaction);
  
  public abstract List<Object> getMap(RelationParticipator paramRelationParticipator, PS paramPS, double paramDouble, int paramInt1, int paramInt2);
}
