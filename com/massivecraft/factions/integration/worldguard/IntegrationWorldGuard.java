package com.massivecraft.factions.integration.worldguard;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.Integration;




public class IntegrationWorldGuard
  extends Integration
{
  private static IntegrationWorldGuard i = new IntegrationWorldGuard();
  public static IntegrationWorldGuard get() { return i; }
  
  private IntegrationWorldGuard() {
    setPluginName("WorldGuard");
  }
  





  public Engine getEngine()
  {
    return EngineWorldGuard.get();
  }
}
