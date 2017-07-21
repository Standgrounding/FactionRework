package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;





public class CmdFactionsClean
  extends FactionsCommand
{
  public CmdFactionsClean() {}
  
  public void perform()
    throws MassiveException
  {
    Object message = Txt.titleize("Factions Cleaner Results");
    message(message);
    

    cleanMessage(cleanPlayer(), "player");
    cleanMessage(cleanFactionInvites(), "faction invites");
    cleanMessage(cleanFactionRelationWhishes(), "faction relation whishes");
    cleanMessage(cleanBoardHost(), "chunk whole");
    cleanMessage(cleanBoardGrant(), "chunk access");
  }
  




  private void cleanMessage(int count, String name)
  {
    msg("<v>%d<k> %s", new Object[] { Integer.valueOf(count), name });
  }
  
  private int cleanPlayer()
  {
    int ret = 0;
    
    for (MPlayer mplayer : MPlayerColl.get().getAll())
    {
      if (mplayer.isFactionOrphan())
      {
        mplayer.resetFactionData();
        ret++;
      }
    }
    return ret;
  }
  
  private int cleanFactionInvites()
  {
    int ret = 0;
    
    for (Faction faction : FactionColl.get().getAll())
    {
      EntityInternalMap<Invitation> invitations = faction.getInvitations();
      if (!invitations.isEmpty())
      {
        ret += invitations.size();
        Set<String> invitationIds = new MassiveSet(invitations.keySet());
        for (String inviteId : invitationIds)
        {
          invitations.detachIdFixed(inviteId);
        }
        
        faction.changed();
      }
    }
    return ret;
  }
  
  private int cleanFactionRelationWhishes()
  {
    int ret = 0;
    
    for (Iterator localIterator = FactionColl.get().getAll().iterator(); localIterator.hasNext();) { faction = (Faction)localIterator.next();
      
      for (iterator = faction.getRelationWishes().entrySet().iterator(); iterator.hasNext();)
      {
        Map.Entry<String, Rel> entry = (Map.Entry)iterator.next();
        String factionId = (String)entry.getKey();
        if (!FactionColl.get().containsId(factionId))
        {
          iterator.remove();
          ret++;
          faction.changed();
        } } }
    Faction faction;
    Iterator<Map.Entry<String, Rel>> iterator;
    return ret;
  }
  
  private int cleanBoardHost()
  {
    int ret = 0;
    
    for (Iterator localIterator1 = BoardColl.get().getAll().iterator(); localIterator1.hasNext();) { board = (Board)localIterator1.next();
      
      for (Map.Entry<PS, TerritoryAccess> entry : board.getMap().entrySet())
      {
        PS ps = (PS)entry.getKey();
        TerritoryAccess territoryAccess = (TerritoryAccess)entry.getValue();
        String factionId = territoryAccess.getHostFactionId();
        
        if (!FactionColl.get().containsId(factionId))
        {
          board.removeAt(ps);
          ret++;
        }
      } }
    Board board;
    return ret;
  }
  
  private int cleanBoardGrant()
  {
    int ret = 0;
    
    for (Iterator localIterator1 = BoardColl.get().getAll().iterator(); localIterator1.hasNext();) { board = (Board)localIterator1.next();
      
      for (Map.Entry<PS, TerritoryAccess> entry : board.getMap().entrySet())
      {
        PS ps = (PS)entry.getKey();
        TerritoryAccess territoryAccess = (TerritoryAccess)entry.getValue();
        boolean changed = false;
        
        for (String factionId : territoryAccess.getFactionIds())
        {
          if (!FactionColl.get().containsId(factionId))
          {
            territoryAccess = territoryAccess.withFactionId(factionId, false);
            ret++;
            changed = true;
          }
        }
        if (changed)
        {
          board.setTerritoryAccessAt(ps, territoryAccess);
        }
      }
    }
    Board board;
    return ret;
  }
}
