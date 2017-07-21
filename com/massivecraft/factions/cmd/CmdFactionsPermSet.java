package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPerm;
import com.massivecraft.factions.cmd.type.TypeRel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPermChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;





public class CmdFactionsPermSet
  extends FactionsCommand
{
  public CmdFactionsPermSet()
  {
    addParameter(TypeMPerm.get(), "perm");
    addParameter(TypeRel.get(), "relation");
    addParameter(TypeBooleanYes.get(), "yes/no");
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    MPerm perm = (MPerm)readArg();
    Rel rel = (Rel)readArg();
    Boolean value = (Boolean)readArg();
    Faction faction = (Faction)readArg(msenderFaction);
    

    if (!MPerm.getPermPerms().has(msender, faction, true)) { return;
    }
    
    if ((!msender.isOverriding()) && (!perm.isEditable()))
    {
      msg("<b>The perm <h>%s <b>is not editable.", new Object[] { perm.getName() });
      return;
    }
    

    EventFactionsPermChange event = new EventFactionsPermChange(sender, faction, perm, rel, value.booleanValue());
    event.run();
    if (event.isCancelled()) return;
    value = Boolean.valueOf(event.getNewValue());
    

    if (faction.getPermitted(perm).contains(rel) == value.booleanValue())
    {
      msg("%s <i>already has %s <i>set to %s <i>for %s<i>.", new Object[] { faction.describeTo(msender), perm.getDesc(true, false), Txt.parse(value.booleanValue() ? "<g>YES" : "<b>NOO"), rel.getColor() + rel.getDescPlayerMany() });
      return;
    }
    

    faction.setRelationPermitted(perm, rel, value.booleanValue());
    

    if ((perm == MPerm.getPermPerms()) && (MPerm.getPermPerms().getStandard().contains(Rel.LEADER)))
    {
      faction.setRelationPermitted(MPerm.getPermPerms(), Rel.LEADER, true);
    }
    

    List<Object> messages = new ArrayList();
    

    messages.add(Txt.titleize("Perm for " + faction.describeTo(msender, true)));
    messages.add(MPerm.getStateHeaders());
    messages.add(Txt.parse(perm.getStateInfo(faction.getPermitted(perm), true)));
    message(messages);
    

    List<MPlayer> recipients = faction.getMPlayers();
    recipients.remove(msender);
    
    for (MPlayer recipient : recipients)
    {
      recipient.msg("<h>%s <i>set a perm for <h>%s<i>.", new Object[] { msender.describeTo(recipient, true), faction.describeTo(recipient, true) });
      recipient.message(messages);
    }
  }
}
