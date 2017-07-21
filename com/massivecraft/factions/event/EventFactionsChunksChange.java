package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;





public class EventFactionsChunksChange
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  
  private final Set<PS> chunks;
  private final Faction newFaction;
  private final Map<PS, Faction> oldChunkFaction;
  public Set<PS> getChunks()
  {
    return chunks;
  }
  
  public Faction getNewFaction() { return newFaction; }
  
  public Map<PS, Faction> getOldChunkFaction() {
    return oldChunkFaction;
  }
  
  public Map<Faction, Set<PS>> getOldFactionChunks() { return oldFactionChunks; }
  
  public Map<PS, EventFactionsChunkChangeType> getChunkType() {
    return chunkType;
  }
  
  public Map<EventFactionsChunkChangeType, Set<PS>> getTypeChunks() { return typeChunks; }
  

  private final Map<Faction, Set<PS>> oldFactionChunks;
  private final Map<PS, EventFactionsChunkChangeType> chunkType;
  private final Map<EventFactionsChunkChangeType, Set<PS>> typeChunks;
  public EventFactionsChunksChange(CommandSender sender, Set<PS> chunks, Faction newFaction)
  {
    super(sender);
    chunks = PS.getDistinctChunks(chunks);
    this.chunks = Collections.unmodifiableSet(chunks);
    this.newFaction = newFaction;
    oldChunkFaction = Collections.unmodifiableMap(BoardColl.getChunkFaction(chunks));
    oldFactionChunks = Collections.unmodifiableMap(MUtil.reverseIndex(oldChunkFaction));
    
    MPlayer msender = getMPlayer();
    Faction self = null;
    if (msender != null) self = msender.getFaction();
    Map<PS, EventFactionsChunkChangeType> currentChunkType = new LinkedHashMap();
    for (Map.Entry<PS, Faction> entry : oldChunkFaction.entrySet())
    {
      PS chunk = (PS)entry.getKey();
      Faction from = (Faction)entry.getValue();
      currentChunkType.put(chunk, EventFactionsChunkChangeType.get(from, newFaction, self));
    }
    
    chunkType = Collections.unmodifiableMap(currentChunkType);
    typeChunks = Collections.unmodifiableMap(MUtil.reverseIndex(chunkType));
  }
}
