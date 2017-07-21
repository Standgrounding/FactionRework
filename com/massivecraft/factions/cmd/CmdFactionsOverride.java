package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.Txt;




public class CmdFactionsOverride
  extends FactionsCommand
{
  public CmdFactionsOverride()
  {
    addAliases(new String[] { "admin" });
    

    addParameter(TypeBooleanYes.get(), "on/off", "flip");
  }
  





  public void perform()
    throws MassiveException
  {
    boolean target = ((Boolean)readArg(Boolean.valueOf(!msender.isOverriding()))).booleanValue();
    

    msender.setOverriding(Boolean.valueOf(target));
    

    String desc = Txt.parse(msender.isOverriding() ? "<g>ENABLED" : "<b>DISABLED");
    
    String messageYou = Txt.parse("<i>%s %s <i>override mode.", new Object[] { msender.getDisplayName(msender), desc });
    String messageLog = Txt.parse("<i>%s %s <i>override mode.", new Object[] { msender.getDisplayName(IdUtil.getConsole()), desc });
    
    msender.message(messageYou);
    Factions.get().log(new Object[] { messageLog });
  }
}
