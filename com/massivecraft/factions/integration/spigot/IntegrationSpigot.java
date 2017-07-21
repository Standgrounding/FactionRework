package com.massivecraft.factions.integration.spigot;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.Integration;




public class IntegrationSpigot
  extends Integration
{
  private static IntegrationSpigot i = new IntegrationSpigot();
  public static IntegrationSpigot get() { return i; }
  
  private IntegrationSpigot() {
    setPredicate(PredicateSpigot.get());
  }
  





  public Engine getEngine()
  {
    return EngineSpigot.get();
  }
}
