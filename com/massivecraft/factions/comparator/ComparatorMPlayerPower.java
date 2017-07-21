package com.massivecraft.factions.comparator;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.comparator.ComparatorAbstract;

public class ComparatorMPlayerPower
  extends ComparatorAbstract<MPlayer>
  implements Named
{
  public ComparatorMPlayerPower() {}
  
  private static ComparatorMPlayerPower i = new ComparatorMPlayerPower();
  public static ComparatorMPlayerPower get() { return i; }
  





  public String getName()
  {
    return "Power";
  }
  


  public int compareInner(MPlayer m1, MPlayer m2)
  {
    int ret = m1.getPowerRounded() - m2.getPowerRounded();
    if (ret != 0) { return ret;
    }
    
    return m1.getPowerMaxRounded() - m2.getPowerMaxRounded();
  }
}
