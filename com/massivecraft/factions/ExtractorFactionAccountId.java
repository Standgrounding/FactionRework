package com.massivecraft.factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.util.extractor.Extractor;


public class ExtractorFactionAccountId
  implements Extractor
{
  public ExtractorFactionAccountId() {}
  
  private static ExtractorFactionAccountId i = new ExtractorFactionAccountId();
  public static ExtractorFactionAccountId get() { return i; }
  





  public Object extract(Object o)
  {
    if ((o instanceof Faction))
    {
      String factionId = ((Faction)o).getId();
      if (factionId == null) return null;
      return "faction-" + factionId;
    }
    
    return null;
  }
}
