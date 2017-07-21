package com.massivecraft.factions.entity;

import com.massivecraft.massivecore.store.Coll;



public class MConfColl
  extends Coll<MConf>
{
  public MConfColl() {}
  
  private static MConfColl i = new MConfColl();
  public static MConfColl get() { return i; }
  





  public void onTick()
  {
    super.onTick();
  }
  





  public void setActive(boolean active)
  {
    super.setActive(active);
    if (!active) return;
    MConf.i = (MConf)get("instance", true);
  }
}
