package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPerm;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;






public class CmdFactionsPermShow
  extends FactionsCommand
{
  public CmdFactionsPermShow()
  {
    addParameter(TypeFaction.get(), "faction", "you");
    addParameter(TypeSet.get(TypeMPerm.get()), "perms", "all", true);
  }
  





  public void perform()
    throws MassiveException
  {
    Faction faction = (Faction)readArg(msenderFaction);
    Collection<MPerm> mperms = (Collection)readArg(MPerm.getAll());
    

    List<Object> messages = new ArrayList();
    
    messages.add(Txt.titleize("Perm for " + faction.describeTo(msender, true)));
    messages.add(MPerm.getStateHeaders());
    for (MPerm mperm : mperms)
    {
      messages.add(Txt.parse(mperm.getStateInfo(faction.getPermitted(mperm), true)));
    }
    

    message(messages);
  }
}
