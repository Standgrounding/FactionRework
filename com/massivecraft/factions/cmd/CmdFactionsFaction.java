package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsFactionShowAsync;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.PriorityLines;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import java.util.Map;
import java.util.TreeSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;




public class CmdFactionsFaction
  extends FactionsCommand
{
  public CmdFactionsFaction()
  {
    addAliases(new String[] { "f", "show", "who" });
    

    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    final Faction faction = (Faction)readArg(msenderFaction);
    final CommandSender sender = this.sender;
    
    Bukkit.getScheduler().runTaskAsynchronously(Factions.get(), new Runnable()
    {

      public void run()
      {

        EventFactionsFactionShowAsync event = new EventFactionsFactionShowAsync(sender, faction);
        event.run();
        if (event.isCancelled()) { return;
        }
        
        MixinMessage.get().messageOne(sender, Txt.titleize("Faction " + faction.getName(msender)));
        

        TreeSet<PriorityLines> priorityLiness = new TreeSet(event.getIdPriorityLiness().values());
        for (PriorityLines priorityLines : priorityLiness)
        {
          MixinMessage.get().messageOne(sender, priorityLines.getLines());
        }
      }
    });
  }
}
