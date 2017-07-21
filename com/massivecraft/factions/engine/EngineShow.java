package com.massivecraft.factions.engine;

import com.massivecraft.factions.comparator.ComparatorMPlayerRole;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsFactionShowAsync;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.PriorityLines;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;




public class EngineShow
  extends Engine
{
  public EngineShow() {}
  
  private static EngineShow i = new EngineShow();
  public static EngineShow get() { return i; }
  




  @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
  public void onFactionShow(EventFactionsFactionShowAsync event)
  {
    int tableCols = 4;
    CommandSender sender = event.getSender();
    MPlayer mplayer = event.getMPlayer();
    Faction faction = event.getFaction();
    boolean normal = faction.isNormal();
    Map<String, PriorityLines> idPriorityLiness = event.getIdPriorityLiness();
    String none = Txt.parse("<silver><italic>none");
    

    if (mplayer.isOverriding())
    {
      show(idPriorityLiness, "factions_id", 1000, "ID", faction.getId());
    }
    

    show(idPriorityLiness, "factions_description", 2000, "Description", faction.getDescriptionDesc());
    
    List<String> flagDescs;
    if (normal)
    {

      long ageMillis = faction.getCreatedAtMillis() - System.currentTimeMillis();
      LinkedHashMap<TimeUnit, Long> ageUnitcounts = TimeDiffUtil.limit(TimeDiffUtil.unitcounts(ageMillis, TimeUnit.getAllButMillis()), 3);
      String ageDesc = TimeDiffUtil.formatedVerboose(ageUnitcounts, "<i>");
      show(idPriorityLiness, "factions_age", 3000, "Age", ageDesc);
      


      flagDescs = new LinkedList();
      for (Map.Entry<MFlag, Boolean> entry : faction.getFlags().entrySet())
      {
        MFlag mflag = (MFlag)entry.getKey();
        if (mflag != null)
        {
          Boolean value = (Boolean)entry.getValue();
          if ((value != null) && 
          
            (mflag.isInteresting(value.booleanValue())))
          {
            String flagDesc = Txt.parse(value.booleanValue() ? "<g>" : "<b>") + mflag.getName();
            flagDescs.add(flagDesc);
          } } }
      String flagsDesc = Txt.parse("<silver><italic>default");
      if (!flagDescs.isEmpty())
      {
        flagsDesc = Txt.implode(flagDescs, Txt.parse(" <i>| "));
      }
      show(idPriorityLiness, "factions_flags", 4000, "Flags", flagsDesc);
      

      double powerBoost = faction.getPowerBoost();
      String boost = (powerBoost > 0.0D ? " (bonus: " : " (penalty: ") + powerBoost + ")";
      String powerDesc = Txt.parse("%d/%d/%d%s", new Object[] { Integer.valueOf(faction.getLandCount()), Integer.valueOf(faction.getPowerRounded()), Integer.valueOf(faction.getPowerMaxRounded()), boost });
      show(idPriorityLiness, "factions_power", 5000, "Land / Power / Maxpower", powerDesc);
      

      if (Econ.isEnabled())
      {

        List<String> landvalueLines = new LinkedList();
        long landCount = faction.getLandCount();
        for (EventFactionsChunkChangeType type : EventFactionsChunkChangeType.values())
        {
          Double money = (Double)geteconChunkCost.get(type);
          if ((money != null) && 
            (money.doubleValue() != 0.0D)) {
            money = Double.valueOf(money.doubleValue() * landCount);
            
            String word = "Cost";
            if (money.doubleValue() <= 0.0D)
            {
              word = "Reward";
              money = Double.valueOf(money.doubleValue() * -1.0D);
            }
            
            String key = Txt.parse("Total Land %s %s", new Object[] { type.toString().toLowerCase(), word });
            String value = Txt.parse("<h>%s", new Object[] { Money.format(money.doubleValue()) });
            String line = show(key, value);
            landvalueLines.add(line);
          } }
        idPriorityLiness.put("factions_landvalue", new PriorityLines(6000, new Object[] { landvalueLines }));
        

        if (getbankEnabled)
        {
          double bank = Money.get(faction);
          String bankDesc = Txt.parse("<h>%s", new Object[] { Money.format(bank, true) });
          show(idPriorityLiness, "factions_bank", 7000, "Bank", bankDesc);
        }
      }
    }
    

    List<String> followerLines = new ArrayList();
    
    List<String> followerNamesOnline = new ArrayList();
    List<String> followerNamesOffline = new ArrayList();
    
    List<MPlayer> followers = faction.getMPlayers();
    Collections.sort(followers, ComparatorMPlayerRole.get());
    for (MPlayer follower : followers)
    {
      if (follower.isOnline(sender))
      {
        followerNamesOnline.add(follower.getNameAndTitle(mplayer));
      }
      else if (normal)
      {

        followerNamesOffline.add(follower.getNameAndTitle(mplayer));
      }
    }
    
    String headerOnline = Txt.parse("<a>Followers Online (%s):", new Object[] { Integer.valueOf(followerNamesOnline.size()) });
    followerLines.add(headerOnline);
    if (followerNamesOnline.isEmpty())
    {
      followerLines.add(none);
    }
    else
    {
      followerLines.addAll(table(followerNamesOnline, 4));
    }
    
    if (normal)
    {
      String headerOffline = Txt.parse("<a>Followers Offline (%s):", new Object[] { Integer.valueOf(followerNamesOffline.size()) });
      followerLines.add(headerOffline);
      if (followerNamesOffline.isEmpty())
      {
        followerLines.add(none);
      }
      else
      {
        followerLines.addAll(table(followerNamesOffline, 4));
      }
    }
    idPriorityLiness.put("factions_followers", new PriorityLines(9000, new Object[] { followerLines }));
  }
  
  public static String show(String key, String value)
  {
    return Txt.parse("<a>%s: <i>%s", new Object[] { key, value });
  }
  
  public static PriorityLines show(int priority, String key, String value)
  {
    return new PriorityLines(priority, new Object[] { show(key, value) });
  }
  
  public static void show(Map<String, PriorityLines> idPriorityLiness, String id, int priority, String key, String value)
  {
    idPriorityLiness.put(id, show(priority, key, value));
  }
  
  public static List<String> table(List<String> strings, int cols)
  {
    List<String> ret = new ArrayList();
    
    StringBuilder row = new StringBuilder();
    int count = 0;
    
    Iterator<String> iter = strings.iterator();
    while (iter.hasNext())
    {
      String string = (String)iter.next();
      row.append(string);
      count++;
      
      if ((iter.hasNext()) && (count != cols))
      {
        row.append(Txt.parse(" <i>| "));
      }
      else
      {
        ret.add(row.toString());
        row = new StringBuilder();
        count = 0;
      }
    }
    
    return ret;
  }
}
