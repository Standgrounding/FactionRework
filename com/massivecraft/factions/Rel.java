package com.massivecraft.factions;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.Colorized;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.collections.MassiveSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.ChatColor;




public enum Rel
  implements Colorized, Named
{
  ENEMY("an enemy", "enemies", "an enemy faction", "enemy factions", new String[] { "Enemy" }), 
  



  NEUTRAL("someone neutral to you", "those neutral to you", "a neutral faction", "neutral factions", new String[] { "Neutral" }), 
  



  TRUCE("someone in truce with you", "those in truce with you", "a faction in truce", "factions in truce", new String[] { "Truce" }), 
  



  ALLY("an ally", "allies", "an allied faction", "allied factions", new String[] { "Ally" }), 
  



  RECRUIT("a recruit in your faction", "recruits in your faction", "", "", new String[] { "Recruit" }), 
  



  MEMBER("a member in your faction", "members in your faction", "your faction", "your factions", new String[] { "Member" }), 
  



  OFFICER("an officer in your faction", "officers in your faction", "", "", new String[] { "Officer", "Moderator" }), 
  



  LEADER("your faction leader", "your faction leader", "", "", new String[] { "Leader", "Admin", "Owner" });
  

  private final String descPlayerOne;
  
  private final String descPlayerMany;
  private final String descFactionOne;
  private final String descFactionMany;
  private final Set<String> names;
  
  public int getValue()
  {
    return ordinal();
  }
  
  public String getDescPlayerOne() { return descPlayerOne; }
  
  public String getDescPlayerMany() {
    return descPlayerMany;
  }
  
  public String getDescFactionOne() { return descFactionOne; }
  
  public String getDescFactionMany() {
    return descFactionMany;
  }
  
  public Set<String> getNames() { return names; }
  public String getName() { return (String)getNames().iterator().next(); }
  




  private Rel(String descPlayerOne, String descPlayerMany, String descFactionOne, String descFactionMany, String... names)
  {
    this.descPlayerOne = descPlayerOne;
    this.descPlayerMany = descPlayerMany;
    this.descFactionOne = descFactionOne;
    this.descFactionMany = descFactionMany;
    this.names = Collections.unmodifiableSet(new MassiveSet(names));
  }
  





  public ChatColor getColor()
  {
    return getcolorMember;
  }
  




  public boolean isAtLeast(Rel rel)
  {
    return getValue() >= rel.getValue();
  }
  
  public boolean isAtMost(Rel rel)
  {
    return getValue() <= rel.getValue();
  }
  
  public boolean isLessThan(Rel rel)
  {
    return getValue() < rel.getValue();
  }
  
  public boolean isMoreThan(Rel rel)
  {
    return getValue() > rel.getValue();
  }
  
  public boolean isRank()
  {
    return isAtLeast(RECRUIT);
  }
  

  public boolean isFriend()
  {
    return isAtLeast(TRUCE);
  }
  
  public String getPrefix()
  {
    return "";
  }
}
