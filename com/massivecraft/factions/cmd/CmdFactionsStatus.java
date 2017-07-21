package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeSortMPlayer;
import com.massivecraft.factions.comparator.ComparatorMPlayerInactivity;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.ChatColor;





public class CmdFactionsStatus
  extends FactionsCommand
{
  public CmdFactionsStatus()
  {
    addParameter(Parameter.getPage());
    addParameter(TypeFaction.get(), "faction", "you");
    addParameter(TypeSortMPlayer.get(), "sort", "time");
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    Faction faction = (Faction)readArg(msenderFaction);
    Comparator<MPlayer> sortedBy = (Comparator)readArg(ComparatorMPlayerInactivity.get());
    

    if (!MPerm.getPermStatus().has(msender, faction, true)) { return;
    }
    
    List<MPlayer> mplayers = faction.getMPlayers();
    Collections.sort(mplayers, sortedBy);
    

    String title = Txt.parse("<i>Status of %s<i>.", new Object[] { faction.describeTo(msender, true) });
    Pager<MPlayer> pager = new Pager(this, title, Integer.valueOf(page), mplayers, new Stringifier()
    {

      public String toString(MPlayer mplayer, int index)
      {

        String displayName = mplayer.getNameAndSomething(msender.getColorTo(mplayer).toString(), "");
        int length = 15 - displayName.length();
        length = length <= 0 ? 1 : length;
        String whiteSpace = Txt.repeat(" ", length);
        

        double currentPower = mplayer.getPower();
        double maxPower = mplayer.getPowerMax();
        
        double percent = currentPower / maxPower;
        String color;
        String color; if (percent > 0.75D)
        {
          color = "<green>";
        } else { String color;
          if (percent > 0.5D)
          {
            color = "<yellow>";
          } else { String color;
            if (percent > 0.25D)
            {
              color = "<rose>";
            }
            else
            {
              color = "<red>"; }
          }
        }
        String power = Txt.parse("<art>Power: %s%.0f<gray>/<green>%.0f", new Object[] { Txt.parse(color), Double.valueOf(currentPower), Double.valueOf(maxPower) });
        

        long lastActiveMillis = mplayer.getLastActivityMillis() - System.currentTimeMillis();
        LinkedHashMap<TimeUnit, Long> activeTimes = TimeDiffUtil.limit(TimeDiffUtil.unitcounts(lastActiveMillis, TimeUnit.getAllButMillis()), 3);
        String lastActive = mplayer.isOnline(msender) ? Txt.parse("<lime>Online right now.") : Txt.parse("<i>Last active: " + TimeDiffUtil.formatedMinimal(activeTimes, "<i>"));
        
        return Txt.parse("%s%s %s %s", new Object[] { displayName, whiteSpace, power, lastActive });

      }
      

    });
    pager.message();
  }
}
