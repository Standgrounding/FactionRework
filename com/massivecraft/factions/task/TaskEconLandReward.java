package com.massivecraft.factions.task;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.ModuloRepeatTask;



public class TaskEconLandReward
  extends ModuloRepeatTask
{
  public TaskEconLandReward() {}
  
  private static TaskEconLandReward i = new TaskEconLandReward();
  public static TaskEconLandReward get() { return i; }
  






  public long getDelayMillis()
  {
    return (gettaskEconLandRewardMinutes * 60000.0D);
  }
  


  public void invoke(long now)
  {
    if (!MassiveCore.isTaskServer()) { return;
    }
    
    FactionColl.get().econLandRewardRoutine();
  }
}
