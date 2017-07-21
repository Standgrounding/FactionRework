package com.massivecraft.factions.engine;

import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;


public class EngineMoveChunk
  extends Engine
{
  public EngineMoveChunk() {}
  
  private static EngineMoveChunk i = new EngineMoveChunk();
  public static EngineMoveChunk get() { return i; }
  





  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void moveChunkDetect(PlayerMoveEvent event)
  {
    if (MUtil.isSameChunk(event)) return;
    Player player = event.getPlayer();
    if (MUtil.isntPlayer(player)) { return;
    }
    
    MPlayer mplayer = MPlayer.get(player);
    
    PS chunkFrom = PS.valueOf(event.getFrom()).getChunk(true);
    PS chunkTo = PS.valueOf(event.getTo()).getChunk(true);
    
    Faction factionFrom = BoardColl.get().getFactionAt(chunkFrom);
    Faction factionTo = BoardColl.get().getFactionAt(chunkTo);
    

    moveChunkTerritoryInfo(mplayer, player, chunkFrom, chunkTo, factionFrom, factionTo);
    moveChunkAutoClaim(mplayer, chunkTo);
  }
  





  public void moveChunkTerritoryInfo(MPlayer mplayer, Player player, PS chunkFrom, PS chunkTo, Faction factionFrom, Faction factionTo)
  {
    if (mplayer.isMapAutoUpdating())
    {
      List<Object> message = BoardColl.get().getMap(mplayer, chunkTo, player.getLocation().getYaw(), 48, 8);
      mplayer.message(message);
    }
    else if (factionFrom != factionTo)
    {
      if (mplayer.isTerritoryInfoTitles())
      {
        String maintitle = parseTerritoryInfo(getterritoryInfoTitlesMain, mplayer, factionTo);
        String subtitle = parseTerritoryInfo(getterritoryInfoTitlesSub, mplayer, factionTo);
        MixinTitle.get().sendTitleMessage(player, getterritoryInfoTitlesTicksIn, getterritoryInfoTitlesTicksStay, getterritoryInfoTitleTicksOut, maintitle, subtitle);
      }
      else
      {
        String message = parseTerritoryInfo(getterritoryInfoChat, mplayer, factionTo);
        MixinMessage.get().messageOne(player, message);
      }
    }
    

    TerritoryAccess accessFrom = BoardColl.get().getTerritoryAccessAt(chunkFrom);
    Boolean hasTerritoryAccessFrom = accessFrom.hasTerritoryAccess(mplayer);
    
    TerritoryAccess accessTo = BoardColl.get().getTerritoryAccessAt(chunkTo);
    Boolean hasTerritoryAccessTo = accessTo.hasTerritoryAccess(mplayer);
    
    if (!MUtil.equals(hasTerritoryAccessFrom, hasTerritoryAccessTo))
    {
      if (hasTerritoryAccessTo == null)
      {
        mplayer.msg("<i>You have standard access to this area.");
      }
      else if (hasTerritoryAccessTo.booleanValue())
      {
        mplayer.msg("<g>You have elevated access to this area.");
      }
      else
      {
        mplayer.msg("<b>You have decreased access to this area.");
      }
    }
  }
  
  public String parseTerritoryInfo(String string, MPlayer mplayer, Faction faction)
  {
    if (string == null) throw new NullPointerException("string");
    if (faction == null) { throw new NullPointerException("faction");
    }
    string = Txt.parse(string);
    
    string = string.replace("{name}", faction.getName());
    string = string.replace("{relcolor}", faction.getColorTo(mplayer).toString());
    string = string.replace("{desc}", faction.getDescriptionDesc());
    
    return string;
  }
  





  public void moveChunkAutoClaim(MPlayer mplayer, PS chunkTo)
  {
    Faction autoClaimFaction = mplayer.getAutoClaimFaction();
    if (autoClaimFaction == null) { return;
    }
    
    mplayer.tryClaim(autoClaimFaction, Collections.singletonList(chunkTo));
  }
}
