package com.massivecraft.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.collections.MassiveSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;













public class FactionsIndex
{
  private static FactionsIndex i = new FactionsIndex();
  public static FactionsIndex get() { return i; }
  



  private final Map<MPlayer, Faction> mplayer2faction;
  


  private final Map<Faction, Set<MPlayer>> faction2mplayers;
  


  private FactionsIndex()
  {
    mplayer2faction = new WeakHashMap();
    faction2mplayers = new WeakHashMapCreativeImpl(null);
  }
  




  private boolean isConnected(MPlayer mplayer, Faction faction)
  {
    if (mplayer == null) throw new NullPointerException("mplayer");
    if (faction == null) { throw new NullPointerException("faction");
    }
    return mplayer.getFaction() == faction;
  }
  




  public synchronized Faction getFaction(MPlayer mplayer)
  {
    return (Faction)mplayer2faction.get(mplayer);
  }
  
  public synchronized Set<MPlayer> getMPlayers(Faction faction)
  {
    return new MassiveSet((Collection)faction2mplayers.get(faction));
  }
  




  public synchronized void updateAll()
  {
    if (!MPlayerColl.get().isActive()) { throw new IllegalStateException("The MPlayerColl is not yet fully activated.");
    }
    for (MPlayer mplayer : MPlayerColl.get().getAll())
    {
      update(mplayer);
    }
  }
  
  public synchronized void update(MPlayer mplayer)
  {
    if (mplayer == null) throw new NullPointerException("mplayer");
    if (!FactionColl.get().isActive()) throw new IllegalStateException("The FactionColl is not yet fully activated.");
    if (!mplayer.attached()) { return;
    }
    Faction factionActual = mplayer.getFaction();
    Faction factionIndexed = getFaction(mplayer);
    
    Set<Faction> factions = new MassiveSet();
    if (factionActual != null) factions.add(factionActual);
    if (factionIndexed != null) { factions.add(factionIndexed);
    }
    for (Faction faction : factions)
    {
      boolean connected = isConnected(mplayer, faction);
      if (connected)
      {
        ((Set)faction2mplayers.get(faction)).add(mplayer);
      }
      else
      {
        ((Set)faction2mplayers.get(faction)).remove(mplayer);
      }
    }
    
    mplayer2faction.put(mplayer, factionActual);
  }
  
  public synchronized void update(Faction faction)
  {
    if (faction == null) { throw new NullPointerException("faction");
    }
    for (MPlayer mplayer : getMPlayers(faction))
    {
      update(mplayer);
    }
  }
  


  private static abstract class WeakHashMapCreative<K, V>
    extends WeakHashMap<K, V>
  {
    private WeakHashMapCreative() {}
    

    public V get(Object key)
    {
      V ret = super.get(key);
      
      if (ret == null)
      {
        ret = createValue();
        put(key, ret);
      }
      
      return ret;
    }
    
    public abstract V createValue();
  }
  
  private static class WeakHashMapCreativeImpl extends FactionsIndex.WeakHashMapCreative<Faction, Set<MPlayer>> { private WeakHashMapCreativeImpl() { super(); }
    

    public Set<MPlayer> createValue()
    {
      return Collections.newSetFromMap(new WeakHashMap());
    }
  }
}
