package com.massivecraft.factions.comparator;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.comparator.ComparatorAbstract;
import com.massivecraft.massivecore.comparator.ComparatorComparable;
import com.massivecraft.massivecore.util.IdUtil;
import java.lang.ref.WeakReference;
import java.util.List;
import org.bukkit.command.CommandSender;

public class ComparatorFactionList
  extends ComparatorAbstract<Faction>
{
  private final WeakReference<CommandSender> watcher;
  
  public CommandSender getWatcher()
  {
    return (CommandSender)watcher.get();
  }
  

  public static ComparatorFactionList get(Object watcherObject)
  {
    return new ComparatorFactionList(watcherObject);
  }
  
  public ComparatorFactionList(Object watcherObject) { watcher = new WeakReference(IdUtil.getSender(watcherObject)); }
  







  public int compareInner(Faction f1, Faction f2)
  {
    if ((f1.isNone()) && (f2.isNone())) return 0;
    if (f1.isNone()) return -1;
    if (f2.isNone()) { return 1;
    }
    
    int ret = f2.getMPlayersWhereOnlineTo(getWatcher()).size() - f1.getMPlayersWhereOnlineTo(getWatcher()).size();
    if (ret != 0) { return ret;
    }
    
    ret = f2.getMPlayers().size() - f1.getMPlayers().size();
    if (ret != 0) { return ret;
    }
    
    return ComparatorComparable.get().compare(f1.getId(), f2.getId());
  }
}
