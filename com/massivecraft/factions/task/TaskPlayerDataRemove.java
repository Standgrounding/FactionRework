package com.massivecraft.factions.task;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.ModuloRepeatTask;



public class TaskPlayerDataRemove
  extends ModuloRepeatTask
{
  public TaskPlayerDataRemove() {}
  
  private static TaskPlayerDataRemove i = new TaskPlayerDataRemove();
  public static TaskPlayerDataRemove get() { return i; }
  






  public long getDelayMillis()
  {
    return (gettaskPlayerDataRemoveMinutes * 60000.0D);
  }
  


  public void invoke(long now)
  {
    if (!MassiveCore.isTaskServer()) { return;
    }
    
    MPlayerColl.get().considerRemovePlayerMillis();
  }
}
