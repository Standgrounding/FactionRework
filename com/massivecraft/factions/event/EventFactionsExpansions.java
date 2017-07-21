package com.massivecraft.factions.event;

import com.massivecraft.massivecore.collections.MassiveTreeMap;
import com.massivecraft.massivecore.comparator.ComparatorCaseInsensitive;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;





public class EventFactionsExpansions
  extends EventFactionsAbstractSender
{
  private static final HandlerList handlers = new HandlerList();
  public HandlerList getHandlers() { return handlers; }
  public static HandlerList getHandlerList() { return handlers; }
  




  private final MassiveTreeMap<String, Boolean, ComparatorCaseInsensitive> expansions = new MassiveTreeMap(ComparatorCaseInsensitive.get());
  public Map<String, Boolean> getExpansions() { return expansions; }
  




  public EventFactionsExpansions(CommandSender sender)
  {
    super(sender);
    getExpansions().put("FactionsTax", Boolean.valueOf(false));
    getExpansions().put("FactionsDynmap", Boolean.valueOf(false));
  }
}
