package com.massivecraft.factions.integration.lwc;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.Integration;




public class IntegrationLwc
  extends Integration
{
  private static IntegrationLwc i = new IntegrationLwc();
  public static IntegrationLwc get() { return i; }
  
  private IntegrationLwc() {
    setPluginName("LWC");
  }
  





  public Engine getEngine()
  {
    return EngineLwc.get();
  }
}
