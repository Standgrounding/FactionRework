package com.massivecraft.factions.util;

import com.massivecraft.massivecore.collections.MassiveList;
import java.util.List;














public class AsciiCompass
{
  public AsciiCompass() {}
  
  public static List<String> getAsciiCompass(double degrees)
  {
    return getAsciiCompass(AsciiCompassDirection.getByDegrees(degrees));
  }
  

  private static List<String> getAsciiCompass(AsciiCompassDirection directionFacing)
  {
    List<String> ret = new MassiveList();
    

    ret.add(visualizeRow(directionFacing, new AsciiCompassDirection[] { AsciiCompassDirection.NW, AsciiCompassDirection.N, AsciiCompassDirection.NE }));
    ret.add(visualizeRow(directionFacing, new AsciiCompassDirection[] { AsciiCompassDirection.W, AsciiCompassDirection.NONE, AsciiCompassDirection.E }));
    ret.add(visualizeRow(directionFacing, new AsciiCompassDirection[] { AsciiCompassDirection.SW, AsciiCompassDirection.S, AsciiCompassDirection.SE }));
    

    return ret;
  }
  





  private static String visualizeRow(AsciiCompassDirection directionFacing, AsciiCompassDirection... cardinals)
  {
    if (cardinals == null) { throw new NullPointerException("cardinals");
    }
    
    StringBuilder ret = new StringBuilder(cardinals.length);
    

    for (AsciiCompassDirection asciiCardinal : cardinals)
    {
      ret.append(asciiCardinal.visualize(directionFacing));
    }
    

    return ret.toString();
  }
}
