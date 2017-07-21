package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsInvitedChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collection;
import org.bukkit.ChatColor;





public class CmdFactionsInviteAdd
  extends FactionsCommand
{
  public CmdFactionsInviteAdd()
  {
    addParameter(TypeSet.get(TypeMPlayer.get()), "players", true);
  }
  





  public void perform()
    throws MassiveException
  {
    Collection<MPlayer> mplayers = (Collection)readArg();
    
    String senderId = IdUtil.getId(sender);
    long creationMillis = System.currentTimeMillis();
    

    if (!MPerm.getPermInvite().has(msender, msenderFaction, true)) { return;
    }
    for (MPlayer mplayer : mplayers)
    {

      if (mplayer.getFaction() == msenderFaction)
      {
        msg("%s<i> is already a member of %s<i>.", new Object[] { mplayer.getName(), msenderFaction.getName(msender) });

      }
      else
      {
        boolean isInvited = msenderFaction.isInvited(mplayer);
        
        if (!isInvited)
        {

          EventFactionsInvitedChange event = new EventFactionsInvitedChange(sender, mplayer, msenderFaction, isInvited);
          event.run();
          if (!event.isCancelled()) {
            isInvited = event.isNewInvited();
            

            mplayer.msg("%s<i> invited you to %s<i>.", new Object[] { msender.describeTo(mplayer, true), msenderFaction.describeTo(mplayer) });
            msenderFaction.msg("%s<i> invited %s<i> to your faction.", new Object[] { msender.describeTo(msenderFaction, true), mplayer.describeTo(msenderFaction) });
            

            Invitation invitation = new Invitation(senderId, Long.valueOf(creationMillis));
            msenderFaction.invite(mplayer.getId(), invitation);
            msenderFaction.changed();
          }
        }
        else
        {
          String command = getcmdFactionsInvite.cmdFactionsInviteRemove.getCommandLine(new String[] { mplayer.getName() });
          String tooltip = Txt.parse("<i>Click to <c>%s<i>.", new Object[] { command });
          
          Mson remove = Mson.mson(new Object[] {
            mson(new Object[] { "You might want to remove him. " }).color(ChatColor.YELLOW), 
            mson(new Object[] { "Click to " + command }).color(ChatColor.RED).tooltip(tooltip).suggest(command) });
          


          msg("%s <i>is already invited to %s<i>.", new Object[] { mplayer.getName(), msenderFaction.getName(msender) });
          message(remove);
        }
      }
    }
  }
}
