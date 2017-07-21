package com.massivecraft.factions.event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.PriorityLines;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;





public class EventFactionsFactionShowAsync
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  

  private final Faction faction;
  
  public Faction getFaction()
  {
    return faction;
  }
  
  public Map<String, PriorityLines> getIdPriorityLiness() { return idPriorityLiness; }
  


  private final Map<String, PriorityLines> idPriorityLiness;
  
  public EventFactionsFactionShowAsync(CommandSender sender, Faction faction)
  {
    super(true, sender);
    this.faction = faction;
    idPriorityLiness = new HashMap();
  }
}
