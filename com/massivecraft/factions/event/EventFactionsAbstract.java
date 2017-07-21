package com.massivecraft.factions.event;

import com.massivecraft.massivecore.event.EventMassiveCore;







public abstract class EventFactionsAbstract
  extends EventMassiveCore
{
  public EventFactionsAbstract() {}
  
  public EventFactionsAbstract(boolean isAsync)
  {
    super(isAsync);
  }
}
