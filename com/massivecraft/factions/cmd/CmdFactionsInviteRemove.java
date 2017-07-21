package com.massivecraft.factions.cmd;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsInvitedChange;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;

public class CmdFactionsInviteRemove
  extends FactionsCommand
{
  public CmdFactionsInviteRemove()
  {
    addParameter(TypeSet.get(TypeMPlayer.get()), "players/all", true);
  }
  




  public void perform()
    throws MassiveException
  {
    Set<MPlayer> mplayers = new HashSet();
    boolean all = false;
    

    if ("all".equalsIgnoreCase(argAt(0)))
    {
      ids = msenderFaction.getInvitations().keySet();
      
      if ((ids == null) || (ids.isEmpty()))
      {
        throw new MassiveException().addMsg("<b>No one is invited to your faction.");
      }
      all = true;
      
      for (String id : ids)
      {
        mplayers.add(MPlayer.get(id));
      }
    }
    else
    {
      mplayers = (Set)readArgAt(0);
    }
    

    if (!MPerm.getPermInvite().has(msender, msenderFaction, true)) { return;
    }
    for (Set<String> ids = mplayers.iterator(); ids.hasNext();) { mplayer = (MPlayer)ids.next();
      

      if (((MPlayer)mplayer).getFaction() == msenderFaction)
      {

        String command = getcmdFactionsKick.getCommandLine(new String[] { ((MPlayer)mplayer).getName() });
        String tooltip = Txt.parse("Click to <c>%s<i>.", new Object[] { command });
        
        Mson kick = Mson.mson(new Object[] {
          mson(new Object[] { "You might want to kick him. " }).color(ChatColor.YELLOW), 
          mson(new Object[] {ChatColor.RED.toString() + tooltip }).tooltip(ChatColor.YELLOW.toString() + tooltip).suggest(command) });
        


        msg("%s<i> is already a member of %s<i>.", new Object[] { ((MPlayer)mplayer).getName(), msenderFaction.getName(msender) });
        message(kick);

      }
      else
      {
        boolean isInvited = msenderFaction.isInvited((MPlayer)mplayer);
        
        if (isInvited)
        {

          EventFactionsInvitedChange event = new EventFactionsInvitedChange(sender, (MPlayer)mplayer, msenderFaction, isInvited);
          event.run();
          if (!event.isCancelled()) {
            isInvited = event.isNewInvited();
            

            ((MPlayer)mplayer).msg("%s<i> revoked your invitation to <h>%s<i>.", new Object[] { msender.describeTo((RelationParticipator)mplayer, true), msenderFaction.describeTo((RelationParticipator)mplayer) });
            

            if (!all)
            {
              msenderFaction.msg("%s<i> revoked %s's<i> invitation.", new Object[] { msender.describeTo(msenderFaction), ((MPlayer)mplayer).describeTo(msenderFaction) });
            }
            

            msenderFaction.uninvite((MPlayer)mplayer);
            

            if (!all) msenderFaction.changed();
          }
        }
        else
        {
          String command = getcmdFactionsInvite.cmdFactionsInviteAdd.getCommandLine(new String[] { ((MPlayer)mplayer).getName() });
          String tooltip = Txt.parse("Click to <c>%s<i>.", new Object[] { command });
          
          Mson invite = Mson.mson(new Object[] {
            mson(new Object[] { "You might want to invite him. " }).color(ChatColor.YELLOW), 
            mson(new Object[] {ChatColor.GREEN.toString() + tooltip }).tooltip(ChatColor.YELLOW.toString() + tooltip).suggest(command) });
          


          msg("%s <i>is not invited to %s<i>.", new Object[] { ((MPlayer)mplayer).describeTo(msender, true), msenderFaction.describeTo((RelationParticipator)mplayer) });
          message(invite);
        }
      }
    }
    Object mplayer;
    if (all)
    {
      List<String> names = new ArrayList();
      for (mplayer = mplayers.iterator(); ((Iterator)mplayer).hasNext();) { MPlayer mplayer = (MPlayer)((Iterator)mplayer).next();
        
        names.add(mplayer.describeTo(msender, true));
      }
      
      Mson factionsRevokeAll = mson(new Object[] {
        Mson.parse("%s<i> revoked ", new Object[] {msender.describeTo(msenderFaction) }), 
        Mson.parse("<i>all <h>%s <i>pending invitations", new Object[] {Integer.valueOf(mplayers.size()) }).tooltip(names), 
        mson(new Object[] { " from your faction." }).color(ChatColor.YELLOW) });
      

      msenderFaction.sendMessage(factionsRevokeAll);
      msenderFaction.changed();
    }
  }
}
