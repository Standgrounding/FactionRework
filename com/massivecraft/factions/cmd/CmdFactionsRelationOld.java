package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.util.MUtil;









public class CmdFactionsRelationOld
  extends FactionsCommand
{
  public final String relName;
  
  public CmdFactionsRelationOld(String rel)
  {
    relName = rel.toLowerCase();
    setSetupEnabled(false);
    

    addAliases(new String[] { relName });
    

    addParameter(TypeFaction.get(), "faction", true);
    

    setVisibility(Visibility.INVISIBLE);
  }
  





  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg();
    

    getcmdFactionsRelation.cmdFactionsRelationSet.execute(sender, MUtil.list(new String[] {faction
      .getId(), relName }));
  }
}
