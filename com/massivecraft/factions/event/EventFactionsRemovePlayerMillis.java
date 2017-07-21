package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.event.EventMassiveCore;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.event.HandlerList;





public class EventFactionsRemovePlayerMillis
  extends EventMassiveCore
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  

  private final MPlayer mplayer;
  private long millis;
  public MPlayer getMPlayer()
  {
    return mplayer;
  }
  
  public long getMillis() { return millis; }
  public void setMillis(long millis) { this.millis = millis; }
  
  private Map<String, Long> causeMillis = new LinkedHashMap();
  public Map<String, Long> getCauseMillis() { return causeMillis; }
  




  public EventFactionsRemovePlayerMillis(boolean async, MPlayer mplayer)
  {
    super(async);
    
    this.mplayer = mplayer;
    millis = getremovePlayerMillisDefault;
    

    causeMillis.put("Default", Long.valueOf(getremovePlayerMillisDefault));
    

    applyPlayerAgeBonus();
    

    applyFactionAgeBonus();
  }
  






  public void applyPlayerAgeBonus()
  {
    if (getremovePlayerMillisPlayerAgeToBonus.isEmpty()) { return;
    }
    
    Long firstPlayed = getMPlayer().getFirstPlayed();
    Long age = Long.valueOf(0L);
    if (firstPlayed != null)
    {
      age = Long.valueOf(System.currentTimeMillis() - firstPlayed.longValue());
    }
    

    long bonus = 0L;
    for (Map.Entry<Long, Long> entry : getremovePlayerMillisPlayerAgeToBonus.entrySet())
    {
      Long key = (Long)entry.getKey();
      if (key != null)
      {
        Long value = (Long)entry.getValue();
        if (value != null)
        {
          if (age.longValue() >= key.longValue())
          {
            bonus = value.longValue();
            break;
          }
        }
      }
    }
    setMillis(getMillis() + bonus);
    

    getCauseMillis().put("Player Age Bonus", Long.valueOf(bonus));
  }
  


  public void applyFactionAgeBonus()
  {
    if (getremovePlayerMillisFactionAgeToBonus.isEmpty()) { return;
    }
    
    Faction faction = getMPlayer().getFaction();
    long age = 0L;
    if (!faction.isNone())
    {
      age = System.currentTimeMillis() - faction.getCreatedAtMillis();
    }
    

    long bonus = 0L;
    for (Map.Entry<Long, Long> entry : getremovePlayerMillisFactionAgeToBonus.entrySet())
    {
      Long key = (Long)entry.getKey();
      if (key != null)
      {
        Long value = (Long)entry.getValue();
        if (value != null)
        {
          if (age >= key.longValue())
          {
            bonus = value.longValue();
            break;
          }
        }
      }
    }
    setMillis(getMillis() + bonus);
    

    getCauseMillis().put("Faction Age Bonus", Long.valueOf(bonus));
  }
}
