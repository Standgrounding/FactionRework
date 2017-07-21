package com.massivecraft.factions.mixin;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.mixin.Mixin;


public class PowerMixin
  extends Mixin
{
  public PowerMixin() {}
  
  private static PowerMixin d = new PowerMixin();
  private static PowerMixin i = d;
  public static PowerMixin get() { return i; }
  




  public double getMaxUniversal(MPlayer mplayer)
  {
    return getMax(mplayer);
  }
  
  public double getMax(MPlayer mplayer)
  {
    return getpowerMax + mplayer.getPowerBoost();
  }
  
  public double getMin(MPlayer mplayer)
  {
    return getpowerMin;
  }
  
  public double getPerHour(MPlayer mplayer)
  {
    return getpowerPerHour;
  }
  
  public double getPerDeath(MPlayer mplayer)
  {
    return getpowerPerDeath;
  }
}
