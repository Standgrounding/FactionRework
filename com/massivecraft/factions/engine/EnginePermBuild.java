package com.massivecraft.factions.engine;

import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.integration.spigot.IntegrationSpigot;
import com.massivecraft.factions.util.EnumerationUtil;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnginePermBuild extends Engine
{
  public EnginePermBuild() {}
  
  private static EnginePermBuild i = new EnginePermBuild();
  public static EnginePermBuild get() { return i; }
  




  public static boolean canPlayerBuildAt(Object senderObject, PS ps, boolean verboose)
  {
    MPlayer mplayer = MPlayer.get(senderObject);
    if (mplayer == null) { return false;
    }
    String name = mplayer.getName();
    if (getplayersWhoBypassAllProtection.contains(name)) { return true;
    }
    if (mplayer.isOverriding()) { return true;
    }
    if ((!MPerm.getPermBuild().has(mplayer, ps, false)) && (MPerm.getPermPainbuild().has(mplayer, ps, false)))
    {
      if (verboose)
      {
        Faction hostFaction = BoardColl.get().getFactionAt(ps);
        mplayer.msg("<b>It is painful to build in the territory of %s<b>.", new Object[] { hostFaction.describeTo(mplayer) });
        Player player = mplayer.getPlayer();
        if (player != null)
        {
          player.damage(getactionDeniedPainAmount);
        }
      }
      return true;
    }
    
    return MPerm.getPermBuild().has(mplayer, ps, verboose);
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void blockBuild(BlockPlaceEvent event)
  {
    if (!event.canBuild()) { return;
    }
    boolean verboose = !isFake(event);
    
    if (canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getBlock()), verboose)) { return;
    }
    event.setBuild(false);
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(BlockBreakEvent event)
  {
    boolean verboose = !isFake(event);
    
    if (canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getBlock()), verboose)) { return;
    }
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(BlockDamageEvent event)
  {
    if (!event.getInstaBreak()) { return;
    }
    boolean verboose = !isFake(event);
    
    if (canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getBlock()), verboose)) { return;
    }
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(SignChangeEvent event)
  {
    boolean verboose = !isFake(event);
    
    if (canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getBlock()), verboose)) { return;
    }
    event.setCancelled(true);
  }
  

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(BlockPistonExtendEvent event)
  {
    if ((IntegrationSpigot.get().isIntegrationActive()) || (!gethandlePistonProtectionThroughDenyBuild)) { return;
    }
    Block block = event.getBlock();
    

    Block targetBlock = block.getRelative(event.getDirection(), event.getLength() + 1);
    

    Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf(block));
    Faction targetFaction = BoardColl.get().getFactionAt(PS.valueOf(targetBlock));
    

    if (targetFaction == pistonFaction) { return;
    }
    
    if (((targetBlock.isEmpty()) || (targetBlock.isLiquid())) && (!MPerm.getPermBuild().has(pistonFaction, targetFaction)))
    {
      event.setCancelled(true);
    }
  }
  








  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(BlockPistonRetractEvent event)
  {
    if ((IntegrationSpigot.get().isIntegrationActive()) || (!gethandlePistonProtectionThroughDenyBuild)) { return;
    }
    
    if (!event.isSticky()) { return;
    }
    Block retractBlock = event.getRetractLocation().getBlock();
    PS retractPs = PS.valueOf(retractBlock);
    

    if ((retractBlock.isEmpty()) || (retractBlock.isLiquid())) { return;
    }
    
    Faction pistonFaction = BoardColl.get().getFactionAt(PS.valueOf(event.getBlock()));
    Faction targetFaction = BoardColl.get().getFactionAt(retractPs);
    

    if (targetFaction == pistonFaction) { return;
    }
    if (MPerm.getPermBuild().has(pistonFaction, targetFaction)) { return;
    }
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(HangingPlaceEvent event)
  {
    boolean verboose = !isFake(event);
    
    if (canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getEntity().getLocation()), verboose)) { return;
    }
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockBuild(HangingBreakEvent event)
  {
    if (!(event instanceof HangingBreakByEntityEvent)) return;
    HangingBreakByEntityEvent entityEvent = (HangingBreakByEntityEvent)event;
    
    Entity breaker = entityEvent.getRemover();
    if (MUtil.isntPlayer(breaker)) { return;
    }
    boolean verboose = !isFake(event);
    
    if (!canPlayerBuildAt(breaker, PS.valueOf(event.getEntity().getLocation()), verboose))
    {
      event.setCancelled(true);
    }
  }
  



  @EventHandler(priority=EventPriority.NORMAL)
  public void blockBuild(PlayerInteractEvent event)
  {
    if (event.getAction() != Action.LEFT_CLICK_BLOCK) { return;
    }
    
    if (event.getClickedBlock() == null) { return;
    }
    Block potentialBlock = event.getClickedBlock().getRelative(BlockFace.UP, 1);
    

    if (potentialBlock == null) { return;
    }
    
    if (potentialBlock.getType() != Material.FIRE) { return;
    }
    
    if (canPlayerBuildAt(event.getPlayer(), PS.valueOf(potentialBlock), true)) { return;
    }
    
    event.setCancelled(true);
    

    event.getPlayer().sendBlockChange(potentialBlock.getLocation(), potentialBlock.getType(), potentialBlock.getState().getRawData());
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void blockLiquidFlow(BlockFromToEvent event)
  {
    if (!getprotectionLiquidFlowEnabled) { return;
    }
    
    Block fromBlock = event.getBlock();
    int fromChunkX = fromBlock.getX() >> 4;
    int fromChunkZ = fromBlock.getZ() >> 4;
    BlockFace blockFace = event.getFace();
    int toChunkX = fromBlock.getX() + blockFace.getModX() >> 4;
    int toChunkZ = fromBlock.getZ() + blockFace.getModZ() >> 4;
    

    if ((toChunkX == fromChunkX) && (toChunkZ == fromChunkZ)) { return;
    }
    Board board = (Board)BoardColl.get().getFixed(fromBlock.getWorld().getName().toLowerCase(), false);
    if (board == null) return;
    Map<PS, TerritoryAccess> map = board.getMapRaw();
    if (map.isEmpty()) { return;
    }
    PS fromPs = PS.valueOf(fromChunkX, fromChunkZ);
    PS toPs = PS.valueOf(toChunkX, toChunkZ);
    TerritoryAccess fromTerritoryAccess = (TerritoryAccess)map.get(fromPs);
    TerritoryAccess toTerritoryAccess = (TerritoryAccess)map.get(toPs);
    String fromFactionId = fromTerritoryAccess != null ? fromTerritoryAccess.getHostFactionId() : "none";
    String toFactionId = toTerritoryAccess != null ? toTerritoryAccess.getHostFactionId() : "none";
    

    if (toFactionId.equals(fromFactionId)) { return;
    }
    Faction fromFaction = (Faction)FactionColl.get().getFixed(fromFactionId);
    if (fromFaction == null) { fromFaction = FactionColl.get().getNone();
    }
    Faction toFaction = (Faction)FactionColl.get().getFixed(toFactionId);
    if (toFaction == null) { toFaction = FactionColl.get().getNone();
    }
    if (toFaction == fromFaction) { return;
    }
    
    if (MPerm.getPermBuild().has(fromFaction, toFaction)) { return;
    }
    
    event.setCancelled(true);
  }
  





  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerDamageEntity(EntityDamageByEntityEvent event)
  {
    Entity edamager = MUtil.getLiableDamager(event);
    if (MUtil.isntPlayer(edamager)) return;
    Player player = (Player)edamager;
    

    Entity edamagee = event.getEntity();
    if (edamagee == null) return;
    if (!EnumerationUtil.isEntityTypeEditOnDamage(edamagee.getType())) { return;
    }
    
    if (canPlayerBuildAt(player, PS.valueOf(edamagee.getLocation()), true)) { return;
    }
    
    event.setCancelled(true);
  }
  

  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    if ((event.getAction() != Action.RIGHT_CLICK_BLOCK) && (event.getAction() != Action.PHYSICAL)) { return;
    }
    Block block = event.getClickedBlock();
    Player player = event.getPlayer();
    
    if (block == null) { return;
    }
    if (!canPlayerUseBlock(player, block, true))
    {
      event.setCancelled(true);
      return;
    }
    
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) { return;
    }
    if (!playerCanUseItemHere(player, PS.valueOf(block), event.getMaterial(), true))
    {
      event.setCancelled(true);
      return;
    }
  }
  
  public static boolean playerCanUseItemHere(Player player, PS ps, Material material, boolean verboose)
  {
    if (MUtil.isntPlayer(player)) { return true;
    }
    if (!EnumerationUtil.isMaterialEditTool(material)) { return true;
    }
    String name = player.getName();
    if (getplayersWhoBypassAllProtection.contains(name)) { return true;
    }
    MPlayer mplayer = MPlayer.get(player);
    if (mplayer.isOverriding()) { return true;
    }
    return MPerm.getPermBuild().has(mplayer, ps, verboose);
  }
  
  public static boolean canPlayerUseBlock(Player player, Block block, boolean verboose)
  {
    if (MUtil.isntPlayer(player)) { return true;
    }
    String name = player.getName();
    if (getplayersWhoBypassAllProtection.contains(name)) { return true;
    }
    MPlayer me = MPlayer.get(player);
    if (me.isOverriding()) { return true;
    }
    PS ps = PS.valueOf(block);
    Material material = block.getType();
    
    if ((EnumerationUtil.isMaterialEditOnInteract(material)) && (!MPerm.getPermBuild().has(me, ps, verboose))) return false;
    if ((EnumerationUtil.isMaterialContainer(material)) && (!MPerm.getPermContainer().has(me, ps, verboose))) return false;
    if ((EnumerationUtil.isMaterialDoor(material)) && (!MPerm.getPermDoor().has(me, ps, verboose))) return false;
    if ((material == Material.STONE_BUTTON) && (!MPerm.getPermButton().has(me, ps, verboose))) return false;
    if ((material == Material.LEVER) && (!MPerm.getPermLever().has(me, ps, verboose))) return false;
    return true;
  }
  



  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
  {
    if (isOffHand(event)) { return;
    }
    
    Player player = event.getPlayer();
    Entity entity = event.getRightClicked();
    boolean verboose = true;
    

    if (canPlayerUseEntity(player, entity, true)) { return;
    }
    
    event.setCancelled(true);
  }
  

  public static boolean canPlayerUseEntity(Player player, Entity entity, boolean verboose)
  {
    if (MUtil.isntPlayer(player)) { return true;
    }
    
    if (entity == null) return true;
    EntityType type = entity.getType();
    PS ps = PS.valueOf(entity.getLocation());
    

    String name = player.getName();
    if (getplayersWhoBypassAllProtection.contains(name)) { return true;
    }
    
    MPlayer me = MPlayer.get(player);
    if (me.isOverriding()) { return true;
    }
    
    if ((EnumerationUtil.isEntityTypeContainer(type)) && (!MPerm.getPermContainer().has(me, ps, verboose))) { return false;
    }
    
    if ((EnumerationUtil.isEntityTypeEditOnInteract(type)) && (!MPerm.getPermBuild().has(me, ps, verboose))) { return false;
    }
    
    return true;
  }
  


  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
  {
    Block block = event.getBlockClicked();
    Player player = event.getPlayer();
    
    if (playerCanUseItemHere(player, PS.valueOf(block), event.getBucket(), true)) { return;
    }
    event.setCancelled(true);
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerBucketFill(PlayerBucketFillEvent event)
  {
    Block block = event.getBlockClicked();
    Player player = event.getPlayer();
    
    if (playerCanUseItemHere(player, PS.valueOf(block), event.getBucket(), true)) { return;
    }
    event.setCancelled(true);
  }
}
