package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.TypeAbstract;
import com.massivecraft.massivecore.comparator.ComparatorCaseInsensitive;
import com.massivecraft.massivecore.util.IdUtil;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;





public class TypeFaction
  extends TypeAbstract<Faction>
{
  private static TypeFaction i = new TypeFaction();
  public static TypeFaction get() { return i; }
  public TypeFaction() { super(Faction.class); }
  





  public String getVisualInner(Faction value, CommandSender sender)
  {
    return value.describeTo(MPlayer.get(sender));
  }
  

  public String getNameInner(Faction value)
  {
    return ChatColor.stripColor(value.getName());
  }
  



  public Faction read(String str, CommandSender sender)
    throws MassiveException
  {
    if (MassiveCore.NOTHING_REMOVE.contains(str))
    {
      return FactionColl.get().getNone();
    }
    

    if (FactionColl.get().containsId(str))
    {
      Faction ret = (Faction)FactionColl.get().get(str);
      if (ret != null) { return ret;
      }
    }
    
    Faction ret = FactionColl.get().getByName(str);
    if (ret != null) { return ret;
    }
    
    String id = IdUtil.getId(str);
    MPlayer mplayer = (MPlayer)MPlayerColl.get().get(id, false);
    if (mplayer != null)
    {
      return mplayer.getFaction();
    }
    
    throw new MassiveException().addMsg("<b>No faction or player matching \"<p>%s<b>\".", new Object[] { str });
  }
  


  public Collection<String> getTabList(CommandSender sender, String arg)
  {
    Set<String> ret = new TreeSet(ComparatorCaseInsensitive.get());
    

    for (Faction faction : FactionColl.get().getAll())
    {
      ret.add(ChatColor.stripColor(faction.getName()));
    }
    

    return ret;
  }
}
