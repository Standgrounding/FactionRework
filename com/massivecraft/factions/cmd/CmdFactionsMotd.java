package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsMotdChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.TypeNullable;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.mixin.MixinDisplayName;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;




public class CmdFactionsMotd
  extends FactionsCommand
{
  public CmdFactionsMotd()
  {
    addParameter(TypeNullable.get(TypeString.get()), "new", "read", true);
  }
  





  public void perform()
    throws MassiveException
  {
    if (!argIsSet(0))
    {
      message(msenderFaction.getMotdMessages());
      return;
    }
    

    if (!MPerm.getPermMotd().has(msender, msenderFaction, true)) { return;
    }
    
    String target = (String)readArg();
    
    target = target.trim();
    target = Txt.parse(target);
    

    String old = msenderFaction.getMotd();
    

    if (MUtil.equals(old, target))
    {
      msg("<i>The motd for %s <i>is already: <h>%s", new Object[] { msenderFaction.describeTo(msender, true), msenderFaction.getMotdDesc() });
      return;
    }
    

    EventFactionsMotdChange event = new EventFactionsMotdChange(sender, msenderFaction, target);
    event.run();
    if (event.isCancelled()) return;
    target = event.getNewMotd();
    

    msenderFaction.setMotd(target);
    

    for (MPlayer follower : msenderFaction.getMPlayers())
    {
      follower.msg("<i>%s <i>set your faction motd to:\n%s", new Object[] { MixinDisplayName.get().getDisplayName(sender, follower), msenderFaction.getMotdDesc() });
    }
  }
}
