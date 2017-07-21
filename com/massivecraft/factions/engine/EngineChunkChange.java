package com.massivecraft.factions.engine;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.mixin.MixinWorld;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineChunkChange
  extends Engine
{
  public EngineChunkChange() {}
  
  private static EngineChunkChange i = new EngineChunkChange();
  public static EngineChunkChange get() { return i; }
  





  @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
  public void onChunksChange(EventFactionsChunksChange event)
  {
    try
    {
      onChunksChangeInner(event);
    }
    catch (Throwable throwable)
    {
      event.setCancelled(true);
      throwable.printStackTrace();
    }
  }
  

  public void onChunksChangeInner(EventFactionsChunksChange event)
  {
    MPlayer mplayer = event.getMPlayer();
    Faction newFaction = event.getNewFaction();
    Map<Faction, Set<PS>> currentFactionChunks = event.getOldFactionChunks();
    Set<Faction> currentFactions = currentFactionChunks.keySet();
    Set<PS> chunks = event.getChunks();
    

    if (mplayer.isOverriding()) { return;
    }
    
    boolean currentFactionsContainsAtLeastOneNormal = false;
    for (Faction currentFaction : currentFactions)
    {
      if (currentFaction.isNormal())
      {
        currentFactionsContainsAtLeastOneNormal = true;
        break;
      }
    }
    
    int claimedLandCount;
    if (newFaction.isNormal())
    {

      for (PS chunk : chunks)
      {
        String worldId = chunk.getWorld();
        if (!getworldsClaimingEnabled.contains(worldId))
        {
          String worldName = MixinWorld.get().getWorldDisplayName(worldId);
          mplayer.msg("<b>Land claiming is disabled in <h>%s<b>.", new Object[] { worldName });
          event.setCancelled(true);
          return;
        }
      }
      

      if (!MPerm.getPermTerritory().has(mplayer, newFaction, true))
      {

        event.setCancelled(true);
        return;
      }
      

      if (newFaction.getMPlayers().size() < getclaimsRequireMinFactionMembers)
      {
        mplayer.msg("<b>Factions must have at least <h>%s<b> members to claim land.", new Object[] { Integer.valueOf(getclaimsRequireMinFactionMembers) });
        event.setCancelled(true);
        return;
      }
      
      claimedLandCount = newFaction.getLandCount();
      List<String> worldNames; if (!newFaction.getFlag(MFlag.getFlagInfpower()))
      {

        if ((getclaimedLandsMax != 0) && (claimedLandCount + chunks.size() > getclaimedLandsMax))
        {
          mplayer.msg("<b>Limit reached. You can't claim more land.");
          event.setCancelled(true);
          return;
        }
        

        if (getclaimedWorldsMax >= 0)
        {
          Set<String> oldWorlds = newFaction.getClaimedWorlds();
          Set<String> newWorlds = PS.getDistinctWorlds(chunks);
          
          Set<String> worlds = new MassiveSet();
          worlds.addAll(oldWorlds);
          worlds.addAll(newWorlds);
          
          if ((!oldWorlds.containsAll(newWorlds)) && (worlds.size() > getclaimedWorldsMax))
          {
            worldNames = new MassiveList();
            for (String world : oldWorlds)
            {
              worldNames.add(MixinWorld.get().getWorldDisplayName(world));
            }
            
            String worldsMax = getclaimedWorldsMax == 1 ? "world" : "worlds";
            String worldsAlready = oldWorlds.size() == 1 ? "world" : "worlds";
            mplayer.msg("<b>A faction may only be present on <h>%d<b> different %s.", new Object[] { Integer.valueOf(getclaimedWorldsMax), worldsMax });
            mplayer.msg("%s<i> is already present on <h>%d<i> %s:", new Object[] { newFaction.describeTo(mplayer), Integer.valueOf(oldWorlds.size()), worldsAlready });
            mplayer.message(Txt.implodeCommaAndDot(worldNames, ChatColor.YELLOW.toString()));
            mplayer.msg("<i>Please unclaim bases on other worlds to claim here.");
            
            event.setCancelled(true);
            return;
          }
        }
      }
      


      if (claimedLandCount + chunks.size() > newFaction.getPowerRounded())
      {
        mplayer.msg("<b>You don't have enough power to claim that land.");
        event.setCancelled(true);
        return;
      }
      



      Set<PS> nearbyChunks = BoardColl.getNearbyChunks(chunks, getclaimMinimumChunksDistanceToOthers);
      nearbyChunks.removeAll(chunks);
      Set<Faction> nearbyFactions = BoardColl.getDistinctFactions(nearbyChunks);
      nearbyFactions.remove(FactionColl.get().getNone());
      nearbyFactions.remove(newFaction);
      
      MPerm claimnear = MPerm.getPermClaimnear();
      for (Faction nearbyFaction : nearbyFactions)
      {
        if (!claimnear.has(newFaction, nearbyFaction)) {
          mplayer.message(claimnear.createDeniedMessage(mplayer, nearbyFaction));
          event.setCancelled(true);
          return;
        }
      }
      



      if (getclaimsMustBeConnected)
      {

        if (newFaction.getLandCountInWorld(((PS)chunks.iterator().next()).getWorld()) > 0)
        {

          if (!BoardColl.get().isAnyConnectedPs(chunks, newFaction))
          {

            if ((!getclaimsCanBeUnconnectedIfOwnedByOtherFaction) || (currentFactionsContainsAtLeastOneNormal))
            {

              if (getclaimsCanBeUnconnectedIfOwnedByOtherFaction)
              {
                mplayer.msg("<b>You can only claim additional land which is connected to your first claim or controlled by another faction!");
              }
              else
              {
                mplayer.msg("<b>You can only claim additional land which is connected to your first claim!");
              }
              event.setCancelled(true);
              return;
            } }
        }
      }
    }
    for (Map.Entry<Faction, Set<PS>> entry : currentFactionChunks.entrySet())
    {
      Faction oldFaction = (Faction)entry.getKey();
      Set<PS> oldChunks = (Set)entry.getValue();
      

      if ((!oldFaction.isNone()) && 
      

        (!MPerm.getPermTerritory().has(mplayer, oldFaction, false)))
      {



        if (!getclaimingFromOthersAllowed)
        {
          mplayer.msg("<b>You may not claim land from others.");
          event.setCancelled(true);
          return;
        }
        

        if (oldFaction.getRelationTo(newFaction).isAtLeast(Rel.TRUCE))
        {
          mplayer.msg("<b>You can't claim this land due to your relation with the current owner.");
          event.setCancelled(true);
          return;
        }
        

        if (oldFaction.getPowerRounded() > oldFaction.getLandCount() - oldChunks.size())
        {
          mplayer.msg("%s<i> owns this land and is strong enough to keep it.", new Object[] { oldFaction.getName(mplayer) });
          event.setCancelled(true);
          return;
        }
        

        if (!BoardColl.get().isAnyBorderPs(chunks))
        {
          mplayer.msg("<b>You must start claiming land at the border of the territory.");
          event.setCancelled(true);
          return;
        }
      }
    }
  }
}
