package com.massivecraft.factions.predicate;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.predicate.Predicate;

public class PredicateMPlayerRole
  implements Predicate<MPlayer>
{
  private final Rel role;
  
  public Rel getRole()
  {
    return role;
  }
  

  public static PredicateMPlayerRole get(Rel role)
  {
    return new PredicateMPlayerRole(role);
  }
  
  public PredicateMPlayerRole(Rel role) { this.role = role; }
  






  public boolean apply(MPlayer mplayer)
  {
    if (mplayer == null) return false;
    return mplayer.getRole() == role;
  }
}
