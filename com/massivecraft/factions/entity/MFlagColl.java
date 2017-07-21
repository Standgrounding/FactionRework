package com.massivecraft.factions.entity;

import com.massivecraft.massivecore.store.Coll;
import java.util.ArrayList;
import java.util.List;





public class MFlagColl
  extends Coll<MFlag>
{
  private static MFlagColl i = new MFlagColl();
  public static MFlagColl get() { return i; }
  
  private MFlagColl() {
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
    MFlag.setupStandardFlags();
  }
  





  public List<MFlag> getAll(boolean registered)
  {
    List<MFlag> ret = new ArrayList();
    

    for (MFlag mflag : getAll())
    {
      if (mflag.isRegistered() == registered) {
        ret.add(mflag);
      }
    }
    
    return ret;
  }
}
