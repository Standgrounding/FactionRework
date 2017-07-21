package com.massivecraft.factions.cmd;

import com.massivecraft.factions.event.EventFactionsExpansions;
import com.massivecraft.massivecore.util.Txt;
import java.util.Map;
import java.util.Map.Entry;




public class CmdFactionsExpansions
  extends FactionsCommand
{
  public CmdFactionsExpansions() {}
  
  public void perform()
  {
    EventFactionsExpansions event = new EventFactionsExpansions(sender);
    event.run();
    

    Object title = "Factions Expansions";
    title = Txt.titleize(title);
    message(title);
    

    for (Map.Entry<String, Boolean> entry : event.getExpansions().entrySet())
    {
      String name = (String)entry.getKey();
      Boolean installed = (Boolean)entry.getValue();
      String format = installed.booleanValue() ? "<g>[X] <h>%s" : "<b>[ ] <h>%s";
      msg(format, new Object[] { name });
    }
    

    msg("<i>Learn all about expansions in the online documentation:");
    msg("<aqua>https://www.massivecraft.com/factions");
  }
}
