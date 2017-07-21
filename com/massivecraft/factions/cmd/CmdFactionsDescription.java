package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsDescriptionChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.mixin.MixinDisplayName;



public class CmdFactionsDescription
  extends FactionsCommand
{
  public CmdFactionsDescription()
  {
    addParameter(TypeString.get(), "desc", true);
    

    addRequirements(new Requirement[] { ReqHasFaction.get() });
  }
  





  public void perform()
    throws MassiveException
  {
    String newDescription = (String)readArg();
    

    if (!MPerm.getPermDesc().has(msender, msenderFaction, true)) { return;
    }
    
    EventFactionsDescriptionChange event = new EventFactionsDescriptionChange(sender, msenderFaction, newDescription);
    event.run();
    if (event.isCancelled()) return;
    newDescription = event.getNewDescription();
    

    msenderFaction.setDescription(newDescription);
    

    for (MPlayer follower : msenderFaction.getMPlayers())
    {
      follower.msg("<i>%s <i>set your faction description to:\n%s", new Object[] { MixinDisplayName.get().getDisplayName(sender, follower), msenderFaction.getDescriptionDesc() });
    }
  }
}
