package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MFlagColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.predicate.Predicate;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;





public class CmdFactionsFlagList
  extends FactionsCommand
{
  public CmdFactionsFlagList()
  {
    addParameter(Parameter.getPage());
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    final MPlayer mplayer = msender;
    

    String title = "Flag List for " + msenderFaction.describeTo(mplayer);
    final Pager<MFlag> pager = new Pager(this, title, Integer.valueOf(page), new Stringifier()
    {

      public String toString(MFlag mflag, int index)
      {
        return mflag.getStateDesc(false, false, true, true, true, false);
      }
      
    });
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {

        List<MFlag> items = MFlagColl.get().getAll(mplayer.isOverriding() ? null : new Predicate()
        {

          public boolean apply(MFlag mflag)
          {
            return mflag.isVisible();
          }
          

        });
        pager.setItems(items);
        

        pager.message();
      }
    });
  }
}
