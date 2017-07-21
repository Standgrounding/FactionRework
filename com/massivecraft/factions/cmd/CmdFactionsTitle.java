package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsTitleChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;



public class CmdFactionsTitle
  extends FactionsCommand
{
  public CmdFactionsTitle()
  {
    addParameter(TypeMPlayer.get(), "player");
    addParameter(TypeString.get(), "title", "", true);
  }
  





  public void perform()
    throws MassiveException
  {
    MPlayer you = (MPlayer)readArg();
    String newTitle = (String)readArg("");
    
    newTitle = Txt.parse(newTitle);
    if (!Perm.TITLE_COLOR.has(sender, false))
    {
      newTitle = ChatColor.stripColor(newTitle);
    }
    

    if (!MPerm.getPermTitle().has(msender, you.getFaction(), true)) { return;
    }
    
    if ((!msender.isOverriding()) && (you.getRole().isMoreThan(msender.getRole())))
    {
      msg("<b>You can not edit titles for higher ranks.");
      return;
    }
    

    EventFactionsTitleChange event = new EventFactionsTitleChange(sender, you, newTitle);
    event.run();
    if (event.isCancelled()) return;
    newTitle = event.getNewTitle();
    

    you.setTitle(newTitle);
    

    msenderFaction.msg("%s<i> changed a title: %s", new Object[] { msender.describeTo(msenderFaction, true), you.describeTo(msenderFaction, true) });
  }
}
