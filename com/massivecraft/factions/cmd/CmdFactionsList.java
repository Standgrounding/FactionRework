package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.comparator.ComparatorFactionList;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;





public class CmdFactionsList
  extends FactionsCommand
{
  public CmdFactionsList()
  {
    addParameter(Parameter.getPage());
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    final CommandSender sender = this.sender;
    final MPlayer msender = this.msender;
    




    final Pager<Faction> pager = new Pager(this, "Faction List", Integer.valueOf(page), new Stringifier()
    {
      public String toString(Faction faction, int index)
      {
        if (faction.isNone())
        {
          return Txt.parse("<i>Factionless<i> %d online", new Object[] { Integer.valueOf(FactionColl.get().getNone().getMPlayersWhereOnlineTo(sender).size()) });
        }
        

        return Txt.parse("%s<i> %d/%d online, %d/%d/%d", new Object[] {faction
          .getName(msender), 
          Integer.valueOf(faction.getMPlayersWhereOnlineTo(sender).size()), 
          Integer.valueOf(faction.getMPlayers().size()), 
          Integer.valueOf(faction.getLandCount()), 
          Integer.valueOf(faction.getPowerRounded()), 
          Integer.valueOf(faction.getPowerMaxRounded()) });

      }
      

    });
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {

        List<Faction> factions = FactionColl.get().getAll(ComparatorFactionList.get(sender));
        pager.setItems(factions);
        

        pager.message();
      }
    });
  }
}
