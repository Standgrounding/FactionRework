package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPermColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.predicate.Predicate;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;


public class CmdFactionsPermList
  extends FactionsCommand
{
  private static final Predicate<MPerm> PREDICATE_MPERM_VISIBLE = new Predicate()
  {

    public boolean apply(MPerm mperm)
    {
      return mperm.isVisible();
    }
  };
  





  public CmdFactionsPermList()
  {
    addParameter(Parameter.getPage());
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    

    String title = String.format("Perms for %s", new Object[] { msenderFaction.describeTo(msender) });
    final Pager<MPerm> pager = new Pager(this, title, Integer.valueOf(page), new Stringifier()
    {

      public String toString(MPerm mperm, int index)
      {
        return mperm.getDesc(true, true);
      }
      
    });
    final Predicate<MPerm> predicate = msender.isOverriding() ? null : PREDICATE_MPERM_VISIBLE;
    
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {

        List<MPerm> items = MPermColl.get().getAll(predicate);
        

        pager.setItems(items);
        

        pager.message();
      }
    });
  }
}
