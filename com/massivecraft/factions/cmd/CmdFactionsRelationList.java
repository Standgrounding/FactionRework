package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeRelation;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;


public class CmdFactionsRelationList
  extends FactionsCommand
{
  public static final Set<Rel> RELEVANT_RELATIONS = new MassiveSet(new Rel[] { Rel.ENEMY, Rel.TRUCE, Rel.ALLY });
  public static final String SEPERATOR = Txt.parse("<silver>: ");
  





  public CmdFactionsRelationList()
  {
    addParameter(Parameter.getPage());
    addParameter(TypeFaction.get(), "faction", "you");
    addParameter(TypeSet.get(TypeRelation.get()), "relations", "all");
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    final Faction faction = (Faction)readArg(msenderFaction);
    final Set<Rel> relations = (Set)readArg(RELEVANT_RELATIONS);
    

    final Pager<String> pager = new Pager(this, "", Integer.valueOf(page), new Stringifier()
    {

      public String toString(String item, int index)
      {
        return item;
      }
      
    });
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {

        List<String> relNames = new MassiveList();
        for (Map.Entry<Rel, List<String>> entry : FactionColl.get().getRelationNames(faction, relations).entrySet())
        {
          Rel relation = (Rel)entry.getKey();
          coloredName = relation.getColor().toString() + relation.getName();
          
          for (String name : (List)entry.getValue())
          {
            relNames.add(coloredName + CmdFactionsRelationList.SEPERATOR + name);
          }
        }
        
        String coloredName;
        pager.setTitle(Txt.parse("<white>%s's <green>Relations <a>(%d)", new Object[] { faction.getName(), Integer.valueOf(relNames.size()) }));
        

        pager.setItems(relNames);
        

        pager.message();
      }
    });
  }
}
