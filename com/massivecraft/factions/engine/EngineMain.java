package com.massivecraft.factions.engine;

import com.massivecraft.massivecore.ps.PS;

public class EngineMain
{
  public EngineMain() {}
  
  /**
   * @deprecated
   */
  public static boolean canPlayerBuildAt(Object senderObject, PS ps, boolean verboose) {
    return EnginePermBuild.canPlayerBuildAt(senderObject, ps, verboose);
  }
}
