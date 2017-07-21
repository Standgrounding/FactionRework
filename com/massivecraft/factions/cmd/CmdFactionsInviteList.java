package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.mixin.MixinDisplayName;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;





public class CmdFactionsInviteList
  extends FactionsCommand
{
  public CmdFactionsInviteList()
  {
    addParameter(Parameter.getPage());
    addParameter(TypeFaction.get(), "faction", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    int page = ((Integer)readArg()).intValue();
    
    Faction faction = (Faction)readArg(msenderFaction);
    
    if ((faction != msenderFaction) && (!Perm.INVITE_LIST_OTHER.has(sender, true))) { return;
    }
    
    if (!MPerm.getPermInvite().has(msender, msenderFaction, true)) { return;
    }
    
    List<Map.Entry<String, Invitation>> invitations = new MassiveList(faction.getInvitations().entrySet());
    
    Collections.sort(invitations, new Comparator()
    {

      public int compare(Map.Entry<String, Invitation> i1, Map.Entry<String, Invitation> i2)
      {
        return ComparatorSmart.get().compare(((Invitation)i2.getValue()).getCreationMillis(), ((Invitation)i1.getValue()).getCreationMillis());
      }
      
    });
    final long now = System.currentTimeMillis();
    
    Pager<Map.Entry<String, Invitation>> pager = new Pager(this, "Invited Players List", Integer.valueOf(page), invitations, new Stringifier()
    {
      public String toString(Map.Entry<String, Invitation> entry, int index)
      {
        String inviteeId = (String)entry.getKey();
        String inviterId = ((Invitation)entry.getValue()).getInviterId();
        
        String inviteeDisplayName = MixinDisplayName.get().getDisplayName(inviteeId, sender);
        String inviterDisplayName = inviterId != null ? MixinDisplayName.get().getDisplayName(inviterId, sender) : Txt.parse("<silver>unknown");
        
        String ageDesc = "";
        if (((Invitation)entry.getValue()).getCreationMillis() != null)
        {
          long millis = now - ((Invitation)entry.getValue()).getCreationMillis().longValue();
          LinkedHashMap<TimeUnit, Long> ageUnitcounts = TimeDiffUtil.limit(TimeDiffUtil.unitcounts(millis, TimeUnit.getAllButMillis()), 2);
          ageDesc = TimeDiffUtil.formatedMinimal(ageUnitcounts, "<i>");
          ageDesc = " " + ageDesc + Txt.parse(" ago");
        }
        
        return Txt.parse("%s<i> was invited by %s<reset>%s<i>.", new Object[] { inviteeDisplayName, inviterDisplayName, ageDesc });
      }
      

    });
    pager.message();
  }
}
