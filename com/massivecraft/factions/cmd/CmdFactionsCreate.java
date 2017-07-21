package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.req.ReqHasntFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.store.MStore;
import java.util.ArrayList;
import org.bukkit.ChatColor;




public class CmdFactionsCreate
  extends FactionsCommand
{
  public CmdFactionsCreate()
  {
    addAliases(new String[] { "new" });
    

    addParameter(TypeString.get(), "name");
    

    addRequirements(new Requirement[] { ReqHasntFaction.get() });
  }
  





  public void perform()
    throws MassiveException
  {
    String newName = (String)readArg();
    

    if (FactionColl.get().isNameTaken(newName))
    {
      msg("<b>That name is already in use.");
      return;
    }
    
    ArrayList<String> nameValidationErrors = FactionColl.get().validateName(newName);
    if (nameValidationErrors.size() > 0)
    {
      message(nameValidationErrors);
      return;
    }
    

    String factionId = MStore.createId();
    

    EventFactionsCreate createEvent = new EventFactionsCreate(sender, factionId, newName);
    createEvent.run();
    if (createEvent.isCancelled()) { return;
    }
    
    Faction faction = (Faction)FactionColl.get().create(factionId);
    faction.setName(newName);
    
    msender.setRole(Rel.LEADER);
    msender.setFaction(faction);
    
    EventFactionsMembershipChange joinEvent = new EventFactionsMembershipChange(sender, msender, faction, EventFactionsMembershipChange.MembershipChangeReason.CREATE);
    joinEvent.run();
    


    msg("<i>You created the faction %s", new Object[] { faction.getName(msender) });
    message(Mson.mson(new Object[] { mson(new Object[] { "You should now: " }).color(ChatColor.YELLOW), getcmdFactionsDescription.getTemplate() }));
    

    if (getlogFactionCreate)
    {
      Factions.get().log(new Object[] { msender.getName() + " created a new faction: " + newName });
    }
  }
}
