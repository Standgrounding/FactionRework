package com.massivecraft.factions.integration.spigot;

import com.massivecraft.massivecore.Integration;
import com.massivecraft.massivecore.predicate.Predicate;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;


public class PredicateSpigot
  implements Predicate<Integration>
{
  public PredicateSpigot() {}
  
  private static PredicateSpigot i = new PredicateSpigot();
  public static PredicateSpigot get() { return i; }
  






  public boolean apply(Integration integration)
  {
    try
    {
      PlayerInteractAtEntityEvent.class.getName();
      
      return true;
    }
    catch (Throwable t) {}
    
    return false;
  }
}
