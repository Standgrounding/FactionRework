package com.massivecraft.factions.entity;

import com.massivecraft.massivecore.store.Coll;
import java.util.ArrayList;
import java.util.List;





public class MPermColl
  extends Coll<MPerm>
{
  private static MPermColl i = new MPermColl();
  public static MPermColl get() { return i; }
  
  private MPermColl() {
    setLowercasing(true);
  }
  





  public void onTick()
  {
    super.onTick();
  }
  





  public void setActive(boolean active)
  {
    super.setActive(active);
    if (!active) return;
    MPerm.setupStandardPerms();
  }
  





  public List<MPerm> getAll(boolean registered)
  {
    List<MPerm> ret = new ArrayList();
    

    for (MPerm mperm : getAll())
    {
      if (mperm.isRegistered() == registered) {
        ret.add(mperm);
      }
    }
    
    return ret;
  }
}
