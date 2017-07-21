package com.massivecraft.factions.util;

import org.bukkit.ChatColor;





public enum AsciiCompassDirection
{
  N('N'), 
  NE('/'), 
  E('E'), 
  SE('\\'), 
  S('S'), 
  SW('/'), 
  W('W'), 
  NW('\\'), 
  NONE('+');
  


  private final char asciiChar;
  


  public char getAsciiChar()
  {
    return asciiChar;
  }
  



  public static final ChatColor ACTIVE = ChatColor.RED;
  public static final ChatColor INACTIVE = ChatColor.YELLOW;
  




  private AsciiCompassDirection(char asciiChar)
  {
    this.asciiChar = asciiChar;
  }
  




  public String visualize(AsciiCompassDirection directionFacing)
  {
    boolean isFacing = isFacing(directionFacing);
    ChatColor color = getColor(isFacing);
    
    return color.toString() + getAsciiChar();
  }
  
  private boolean isFacing(AsciiCompassDirection directionFacing)
  {
    return this == directionFacing;
  }
  
  private ChatColor getColor(boolean active)
  {
    return active ? ACTIVE : INACTIVE;
  }
  







  public static AsciiCompassDirection getByDegrees(double degrees)
  {
    degrees = (degrees - 157.0D) % 360.0D;
    if (degrees < 0.0D) { degrees += 360.0D;
    }
    
    int ordinal = (int)Math.floor(degrees / 45.0D);
    

    return values()[ordinal];
  }
}
