package com.massivecraft.factions.entity;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.engine.EngineChat;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.massivecore.collections.BackstringSet;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.command.editor.annotation.EditorType;
import com.massivecraft.massivecore.command.editor.annotation.EditorTypeInner;
import com.massivecraft.massivecore.command.type.TypeMillisDiff;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventPriority;


@EditorName("config")
public class MConf
  extends Entity<MConf>
{
  protected static transient MConf i;
  
  public MConf() {}
  
  public static MConf get()
  {
    return i;
  }
  




  public MConf load(MConf that)
  {
    super.load(that);
    


    EngineChat engine = EngineChat.get();
    if (engine.isActive())
    {
      engine.setActive(false);
      engine.setActive(true);
    }
    
    return this;
  }
  




  public int version = 1;
  





  public List<String> aliasesF = MUtil.list(new String[] { "f" });
  







  public WorldExceptionSet worldsClaimingEnabled = new WorldExceptionSet();
  public WorldExceptionSet worldsPowerLossEnabled = new WorldExceptionSet();
  public WorldExceptionSet worldsPowerGainEnabled = new WorldExceptionSet();
  
  public WorldExceptionSet worldsPvpRulesEnabled = new WorldExceptionSet();
  







  public Set<String> playersWhoBypassAllProtection = new LinkedHashSet();
  





  public double taskPlayerPowerUpdateMinutes = 1.0D;
  public double taskPlayerDataRemoveMinutes = 5.0D;
  public double taskEconLandRewardMinutes = 20.0D;
  





  public boolean removePlayerWhenBanned = true;
  


  @EditorType(TypeMillisDiff.class)
  public long removePlayerMillisDefault = 864000000L;
  


  @EditorTypeInner({TypeMillisDiff.class, TypeMillisDiff.class})
  public Map<Long, Long> removePlayerMillisPlayerAgeToBonus = MUtil.map(
    Long.valueOf(1209600000L), Long.valueOf(864000000L), new Object[0]);
  


  @EditorTypeInner({TypeMillisDiff.class, TypeMillisDiff.class})
  public Map<Long, Long> removePlayerMillisFactionAgeToBonus = MUtil.map(
    Long.valueOf(2419200000L), Long.valueOf(864000000L), new Object[] {
    Long.valueOf(1209600000L), Long.valueOf(432000000L) });
  







  public String defaultPlayerFactionId = "none";
  


  public Rel defaultPlayerRole = Rel.RECRUIT;
  

  public double defaultPlayerPower = 0.0D;
  







  public EventPriority motdPriority = EventPriority.NORMAL;
  




  public int motdDelayTicks = -1;
  





  public double powerMax = 10.0D;
  


  public double powerMin = 0.0D;
  

  public double powerPerHour = 2.0D;
  

  public double powerPerDeath = -2.0D;
  


  public boolean canLeaveWithNegativePower = true;
  






  public int factionMemberLimit = 0;
  


  public double factionPowerMax = 0.0D;
  

  public int factionNameLengthMin = 3;
  public int factionNameLengthMax = 16;
  



  public boolean factionNameForceUpperCase = false;
  





  public int setRadiusMax = 30;
  

  public int setFillMax = 1000;
  







  public boolean claimsMustBeConnected = true;
  




  public boolean claimsCanBeUnconnectedIfOwnedByOtherFaction = false;
  


  public boolean claimingFromOthersAllowed = true;
  




  public int claimMinimumChunksDistanceToOthers = 0;
  


  public int claimsRequireMinFactionMembers = 1;
  


  public int claimedLandsMax = 0;
  

  public int claimedWorldsMax = -1;
  




  public boolean protectionLiquidFlowEnabled = true;
  






  public boolean homesEnabled = true;
  



  public boolean homesMustBeInClaimedTerritory = true;
  


  public boolean homesTeleportCommandEnabled = true;
  

  public boolean homesTeleportAllowedFromEnemyTerritory = true;
  public boolean homesTeleportAllowedFromDifferentWorld = true;
  public double homesTeleportAllowedEnemyDistance = 32.0D;
  public boolean homesTeleportIgnoreEnemiesIfInOwnTerritory = true;
  


  public boolean homesTeleportToOnDeathActive = false;
  


  public EventPriority homesTeleportToOnDeathPriority = EventPriority.NORMAL;
  




  public boolean territoryInfoTitlesDefault = true;
  
  public String territoryInfoTitlesMain = "{relcolor}{name}";
  public String territoryInfoTitlesSub = "<i>{desc}";
  public int territoryInfoTitlesTicksIn = 5;
  public int territoryInfoTitlesTicksStay = 60;
  public int territoryInfoTitleTicksOut = 5;
  
  public String territoryInfoChat = "<i> ~ {relcolor}{name} <i>{desc}";
  






  public boolean permanentFactionsDisableLeaderPromotion = false;
  


  public double actionDeniedPainAmount = 2.0D;
  


  public boolean disablePVPForFactionlessPlayers = false;
  


  public boolean enablePVPBetweenFactionlessPlayers = true;
  



  public boolean enablePVPAgainstFactionlessInAttackersLand = false;
  


  public double territoryShieldFactor = 0.1D;
  


  public boolean handlePistonProtectionThroughDenyBuild = true;
  






  public List<String> denyCommandsPermanentFactionMember = new ArrayList();
  


  public Map<Rel, List<String>> denyCommandsTerritoryRelation = MUtil.map(Rel.ENEMY, 
    MUtil.list(new String[] { "home", "homes", "sethome", "createhome", "tpahere", "tpaccept", "tpyes", "tpa", "call", "tpask", "warp", "warps", "spawn", "ehome", "ehomes", "esethome", "ecreatehome", "etpahere", "etpaccept", "etpyes", "etpa", "ecall", "etpask", "ewarp", "ewarps", "espawn", "essentials:home", "essentials:homes", "essentials:sethome", "essentials:createhome", "essentials:tpahere", "essentials:tpaccept", "essentials:tpyes", "essentials:tpa", "essentials:call", "essentials:tpask", "essentials:warp", "essentials:warps", "essentials:spawn", "wtp", "uspawn", "utp", "mspawn", "mtp", "fspawn", "ftp", "jspawn", "jtp" }), new Object[] { Rel.NEUTRAL, new ArrayList(), Rel.TRUCE, new ArrayList(), Rel.ALLY, new ArrayList(), Rel.MEMBER, new ArrayList() });
  



























































  public double denyCommandsDistance = -1.0D;
  

  public Map<Rel, List<String>> denyCommandsDistanceRelation = MUtil.map(Rel.ENEMY, 
    MUtil.list(new String[] { "home" }), new Object[] { Rel.NEUTRAL, new ArrayList(), Rel.TRUCE, new ArrayList(), Rel.ALLY, new ArrayList(), Rel.MEMBER, new ArrayList() });
  








  public List<Rel> denyCommandsDistanceBypassIn = MUtil.list(new Rel[] { Rel.MEMBER, Rel.ALLY });
  










  public boolean chatSetFormat = true;
  


  public EventPriority chatSetFormatAt = EventPriority.LOWEST;
  

  public String chatSetFormatTo = "<{factions_relcolor}§l{factions_roleprefix}§r{factions_relcolor}{factions_name|rp}§f%1$s> %2$s";
  



  public boolean chatParseTags = true;
  


  public EventPriority chatParseTagsAt = EventPriority.LOW;
  







  public ChatColor colorMember = ChatColor.GREEN;
  public ChatColor colorAlly = ChatColor.DARK_PURPLE;
  public ChatColor colorTruce = ChatColor.LIGHT_PURPLE;
  public ChatColor colorNeutral = ChatColor.WHITE;
  public ChatColor colorEnemy = ChatColor.RED;
  

  public ChatColor colorNoPVP = ChatColor.GOLD;
  

  public ChatColor colorFriendlyFire = ChatColor.DARK_RED;
  





  public String prefixLeader = "**";
  public String prefixOfficer = "*";
  public String prefixMember = "+";
  public String prefixRecruit = "-";
  




  public boolean handleExploitObsidianGenerators = true;
  public boolean handleExploitEnderPearlClipping = true;
  public boolean handleExploitTNTWaterlog = false;
  public boolean handleNetherPortalTrap = true;
  








  public int seeChunkSteps = 1;
  

  public int seeChunkKeepEvery = 5;
  public int seeChunkSkipEvery = 0;
  @EditorType(TypeMillisDiff.class)
  public long seeChunkPeriodMillis = 500L;
  
  public int seeChunkParticleAmount = 30;
  public float seeChunkParticleOffsetY = 2.0F;
  public float seeChunkParticleDeltaY = 2.0F;
  




  public int unstuckSeconds = 30;
  public int unstuckChunkRadius = 10;
  






  public boolean logFactionCreate = true;
  public boolean logFactionDisband = true;
  public boolean logFactionJoin = true;
  public boolean logFactionKick = true;
  public boolean logFactionLeave = true;
  public boolean logLandClaims = true;
  public boolean logMoneyTransactions = true;
  










  public BackstringSet<Material> materialsEditOnInteract = new BackstringSet(Material.class);
  


  public BackstringSet<Material> materialsEditTools = new BackstringSet(Material.class);
  

  public BackstringSet<Material> materialsDoor = new BackstringSet(Material.class);
  

  public BackstringSet<Material> materialsContainer = new BackstringSet(Material.class);
  

  public BackstringSet<EntityType> entityTypesEditOnInteract = new BackstringSet(EntityType.class);
  

  public BackstringSet<EntityType> entityTypesEditOnDamage = new BackstringSet(EntityType.class);
  

  public BackstringSet<EntityType> entityTypesContainer = new BackstringSet(EntityType.class);
  

  public BackstringSet<EntityType> entityTypesMonsters = new BackstringSet(EntityType.class);
  

  public BackstringSet<EntityType> entityTypesAnimals = new BackstringSet(EntityType.class);
  












  public String herochatFactionName = "Faction";
  public String herochatFactionNick = "F";
  public String herochatFactionFormat = "{color}[&l{nick}&r{color} &l{factions_roleprefix}&r{color}{factions_title|rp}{sender}{color}] &f{msg}";
  public ChatColor herochatFactionColor = ChatColor.GREEN;
  public int herochatFactionDistance = 0;
  public boolean herochatFactionIsShortcutAllowed = false;
  public boolean herochatFactionCrossWorld = true;
  public boolean herochatFactionMuted = false;
  public Set<String> herochatFactionWorlds = new HashSet();
  

  public String herochatAlliesName = "Allies";
  public String herochatAlliesNick = "A";
  public String herochatAlliesFormat = "{color}[&l{nick}&r&f {factions_relcolor}&l{factions_roleprefix}&r{factions_relcolor}{factions_name|rp}{sender}{color}] &f{msg}";
  public ChatColor herochatAlliesColor = ChatColor.DARK_PURPLE;
  public int herochatAlliesDistance = 0;
  public boolean herochatAlliesIsShortcutAllowed = false;
  public boolean herochatAlliesCrossWorld = true;
  public boolean herochatAlliesMuted = false;
  public Set<String> herochatAlliesWorlds = new HashSet();
  





  public boolean lwcMustHaveBuildRightsToCreate = true;
  




  public boolean lwcRemoveIfNoBuildRights = false;
  


  public Map<EventFactionsChunkChangeType, Boolean> lwcRemoveOnChange = MUtil.map(EventFactionsChunkChangeType.BUY, 
    Boolean.valueOf(false), new Object[] { EventFactionsChunkChangeType.SELL, 
    Boolean.valueOf(false), EventFactionsChunkChangeType.CONQUER, 
    Boolean.valueOf(false), EventFactionsChunkChangeType.PILLAGE, 
    Boolean.valueOf(false) });
  






  public boolean worldguardCheckEnabled = false;
  


  public WorldExceptionSet worldguardCheckWorldsEnabled = new WorldExceptionSet();
  






  public boolean econEnabled = true;
  


  public double econLandReward = 0.0D;
  


  public String econUniverseAccount = "";
  

  public Map<EventFactionsChunkChangeType, Double> econChunkCost = MUtil.map(EventFactionsChunkChangeType.BUY, 
    Double.valueOf(1.0D), new Object[] { EventFactionsChunkChangeType.SELL, 
    Double.valueOf(0.0D), EventFactionsChunkChangeType.CONQUER, 
    Double.valueOf(0.0D), EventFactionsChunkChangeType.PILLAGE, 
    Double.valueOf(0.0D) });
  


  public double econCostCreate = 100.0D;
  

  public double econCostSethome = 0.0D;
  public double econCostJoin = 0.0D;
  public double econCostLeave = 0.0D;
  public double econCostKick = 0.0D;
  public double econCostInvite = 0.0D;
  public double econCostDeinvite = 0.0D;
  public double econCostHome = 0.0D;
  public double econCostName = 0.0D;
  public double econCostDescription = 0.0D;
  public double econCostTitle = 0.0D;
  public double econCostFlag = 0.0D;
  
  public Map<Rel, Double> econRelCost = MUtil.map(Rel.ENEMY, 
    Double.valueOf(0.0D), new Object[] { Rel.ALLY, 
    Double.valueOf(0.0D), Rel.TRUCE, 
    Double.valueOf(0.0D), Rel.NEUTRAL, 
    Double.valueOf(0.0D) });
  



  public boolean bankEnabled = true;
  


  public boolean bankFactionPaysCosts = true;
}
