package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;




public class CmdFactionsRelationWishes
  extends FactionsCommand
{
  public CmdFactionsRelationWishes()
  {
    addParameter(Parameter.getPage());
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    final Faction faction = (Faction)readArg(msenderFaction);
    

    final Pager<Map.Entry<Faction, Rel>> pager = new Pager(this, "", Integer.valueOf(page), new Stringifier()
    {

      public String toString(Map.Entry<Faction, Rel> item, int index)
      {
        Rel rel = (Rel)item.getValue();
        Faction fac = (Faction)item.getKey();
        return rel.getColor().toString() + rel.getName() + CmdFactionsRelationList.SEPERATOR + fac.describeTo(faction, true);
      }
      
    });
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {
        Map<Faction, Rel> realWishes = new MassiveMap();
        
        for (Map.Entry<String, Rel> entry : faction.getRelationWishes().entrySet())
        {
          Rel rel = (Rel)entry.getValue();
          Faction fac = (Faction)FactionColl.get().getFixed((String)entry.getKey());
          if ((fac != null) && 
          

            (!fac.getRelationTo(faction).isAtLeast(rel))) {
            realWishes.put(fac, rel);
          }
        }
        
        pager.setTitle(Txt.parse("<white>%s's <green>Relation wishes <a>(%d)", new Object[] { faction.getName(), Integer.valueOf(realWishes.size()) }));
        

        pager.setItems(MUtil.entriesSortedByValues(realWishes));
        

        pager.message();
      }
    });
  }
}
