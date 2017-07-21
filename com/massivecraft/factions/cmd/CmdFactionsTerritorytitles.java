package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementTitlesAvailable;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.util.Txt;



public class CmdFactionsTerritorytitles
  extends FactionsCommand
{
  public CmdFactionsTerritorytitles()
  {
    addAliases(new String[] { "tt" });
    

    addParameter(TypeBooleanOn.get(), "on|off", "toggle");
    

    addRequirements(new Requirement[] { RequirementTitlesAvailable.get() });
  }
  






  public Visibility getVisibility()
  {
    if (!MixinTitle.get().isAvailable()) return Visibility.INVISIBLE;
    return super.getVisibility();
  }
  

  public void perform()
    throws MassiveException
  {
    boolean before = msender.isTerritoryInfoTitles();
    boolean after = ((Boolean)readArg(Boolean.valueOf(!before))).booleanValue();
    String desc = Txt.parse(after ? "<g>ON" : "<b>OFF");
    

    if (after == before)
    {
      msg("<i>Territory titles is already %s<i>.", new Object[] { desc });
      return;
    }
    

    msender.setTerritoryInfoTitles(Boolean.valueOf(after));
    

    msg("<i>Territory titles is now %s<i>.", new Object[] { desc });
  }
}
