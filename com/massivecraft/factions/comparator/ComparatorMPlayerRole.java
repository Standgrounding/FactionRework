package com.massivecraft.factions.comparator;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.comparator.ComparatorAbstract;

public class ComparatorMPlayerRole
  extends ComparatorAbstract<MPlayer>
  implements Named
{
  public ComparatorMPlayerRole() {}
  
  private static ComparatorMPlayerRole i = new ComparatorMPlayerRole();
  public static ComparatorMPlayerRole get() { return i; }
  





  public String getName()
  {
    return "Rank";
  }
  


  public int compareInner(MPlayer m1, MPlayer m2)
  {
    Rel r1 = m1.getRole();
    Rel r2 = m2.getRole();
    return r2.getValue() - r1.getValue();
  }
}
