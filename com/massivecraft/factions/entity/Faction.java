package com.massivecraft.factions.entity;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.FactionsIndex;
import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.predicate.PredicateCommandSenderFaction;
import com.massivecraft.factions.predicate.PredicateMPlayerRole;
import com.massivecraft.factions.util.MiscUtil;
import com.massivecraft.factions.util.RelationUtil;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveMapDef;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.predicate.PredicateAnd;
import com.massivecraft.massivecore.predicate.PredicateVisibleTo;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.store.SenderColl;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class Faction
  extends Entity<Faction>
  implements FactionsParticipator
{
  public static final transient String NODESCRIPTION = Txt.parse("<em><silver>no description set");
  public static final transient String NOMOTD = Txt.parse("<em><silver>no message of the day set");
  

  public Faction() {}
  

  public static Faction get(Object oid)
  {
    return (Faction)FactionColl.get().get(oid);
  }
  





  public Faction load(Faction that)
  {
    setName(name);
    setDescription(description);
    setMotd(motd);
    setCreatedAtMillis(createdAtMillis);
    setHome(home);
    setPowerBoost(powerBoost);
    invitations.load(invitations);
    setRelationWishes(relationWishes);
    setFlagIds(flags);
    setPermIds(perms);
    
    return this;
  }
  

  public void preDetach(String id)
  {
    if (!isLive()) { return;
    }
    

    if (Money.exists(this))
    {

      Money.set(this, null, 0.0D);
    }
  }
  




  public int version = 1;
  









  private String name = null;
  



  private String description = null;
  



  private String motd = null;
  


  private long createdAtMillis = System.currentTimeMillis();
  



  private PS home = null;
  



  private Double powerBoost = null;
  









  private EntityInternalMap<Invitation> invitations = new EntityInternalMap(this, Invitation.class);
  


  private MassiveMapDef<String, Rel> relationWishes = new MassiveMapDef();
  


  private MassiveMapDef<String, Boolean> flags = new MassiveMapDef();
  


  private MassiveMapDef<String, Set<Rel>> perms = new MassiveMapDef();
  






  public boolean isNone()
  {
    return getId().equals("none");
  }
  
  public boolean isNormal()
  {
    return !isNone();
  }
  







  public String getName()
  {
    String ret = name;
    
    if (getfactionNameForceUpperCase)
    {
      ret = ret.toUpperCase();
    }
    
    return ret;
  }
  

  public void setName(String name)
  {
    String target = name;
    

    if (MUtil.equals(this.name, target)) { return;
    }
    
    this.name = target;
    

    changed();
  }
  


  public String getComparisonName()
  {
    return MiscUtil.getComparisonString(getName());
  }
  
  public String getName(String prefix)
  {
    return prefix + getName();
  }
  
  public String getName(RelationParticipator observer)
  {
    if (observer == null) return getName();
    return getName(getColorTo(observer).toString());
  }
  






  public boolean hasDescription()
  {
    return description != null;
  }
  
  public String getDescription()
  {
    return description;
  }
  

  public void setDescription(String description)
  {
    String target = clean(description);
    

    if (MUtil.equals(this.description, target)) { return;
    }
    
    this.description = target;
    

    changed();
  }
  


  public String getDescriptionDesc()
  {
    String motd = getDescription();
    if (motd == null) motd = NODESCRIPTION;
    return motd;
  }
  






  public boolean hasMotd()
  {
    return motd != null;
  }
  
  public String getMotd()
  {
    return motd;
  }
  

  public void setMotd(String motd)
  {
    String target = clean(motd);
    

    if (MUtil.equals(this.motd, target)) { return;
    }
    
    this.motd = target;
    

    changed();
  }
  


  public String getMotdDesc()
  {
    return getMotdDesc(getMotd());
  }
  
  private static String getMotdDesc(String motd)
  {
    if (motd == null) motd = NOMOTD;
    return motd;
  }
  

  public List<Object> getMotdMessages()
  {
    List<Object> ret = new MassiveList();
    

    Object title = getName() + " - Message of the Day";
    title = Txt.titleize(title);
    ret.add(title);
    
    String motd = Txt.parse("<i>") + getMotdDesc();
    ret.add(motd);
    
    ret.add("");
    

    return ret;
  }
  




  public long getCreatedAtMillis()
  {
    return createdAtMillis;
  }
  

  public void setCreatedAtMillis(long createdAtMillis)
  {
    long target = createdAtMillis;
    

    if (MUtil.equals(Long.valueOf(this.createdAtMillis), Long.valueOf(createdAtMillis))) { return;
    }
    
    this.createdAtMillis = target;
    

    changed();
  }
  




  public PS getHome()
  {
    verifyHomeIsValid();
    return home;
  }
  
  public void verifyHomeIsValid()
  {
    if (isValidHome(home)) return;
    home = null;
    changed();
    msg("<b>Your faction home has been un-set since it is no longer in your territory.");
  }
  
  public boolean isValidHome(PS ps)
  {
    if (ps == null) return true;
    if (!gethomesMustBeInClaimedTerritory) return true;
    if (BoardColl.get().getFactionAt(ps) == this) return true;
    return false;
  }
  
  public boolean hasHome()
  {
    return getHome() != null;
  }
  

  public void setHome(PS home)
  {
    PS target = home;
    

    if (MUtil.equals(this.home, target)) { return;
    }
    
    this.home = target;
    

    changed();
  }
  






  public double getPowerBoost()
  {
    Double ret = powerBoost;
    if (ret == null) ret = Double.valueOf(0.0D);
    return ret.doubleValue();
  }
  


  public void setPowerBoost(Double powerBoost)
  {
    Double target = powerBoost;
    
    if ((target == null) || (target.doubleValue() == 0.0D)) { target = null;
    }
    
    if (MUtil.equals(this.powerBoost, target)) { return;
    }
    
    this.powerBoost = target;
    

    changed();
  }
  






  @Deprecated
  public boolean isDefaultOpen()
  {
    return MFlag.getFlagOpen().isStandard();
  }
  
  @Deprecated
  public boolean isOpen()
  {
    return getFlag(MFlag.getFlagOpen());
  }
  
  @Deprecated
  public void setOpen(Boolean open)
  {
    MFlag flag = MFlag.getFlagOpen();
    if (open == null) open = Boolean.valueOf(flag.isStandard());
    setFlag(flag, open.booleanValue());
  }
  





  public EntityInternalMap<Invitation> getInvitations()
  {
    return invitations;
  }
  

  public boolean isInvited(String playerId)
  {
    return getInvitations().containsKey(playerId);
  }
  
  public boolean isInvited(MPlayer mplayer)
  {
    return isInvited(mplayer.getId());
  }
  
  public boolean uninvite(String playerId)
  {
    System.out.println(playerId);
    return getInvitations().detachId(playerId) != null;
  }
  
  public boolean uninvite(MPlayer mplayer)
  {
    return uninvite(mplayer.getId());
  }
  
  public void invite(String playerId, Invitation invitation)
  {
    uninvite(playerId);
    invitations.attach(invitation, playerId);
  }
  






  public Map<String, Rel> getRelationWishes()
  {
    return relationWishes;
  }
  

  public void setRelationWishes(Map<String, Rel> relationWishes)
  {
    MassiveMapDef<String, Rel> target = new MassiveMapDef(relationWishes);
    

    if (MUtil.equals(this.relationWishes, target)) { return;
    }
    
    this.relationWishes = target;
    

    changed();
  }
  


  public Rel getRelationWish(String factionId)
  {
    Rel ret = (Rel)getRelationWishes().get(factionId);
    if (ret == null) ret = Rel.NEUTRAL;
    return ret;
  }
  
  public Rel getRelationWish(Faction faction)
  {
    return getRelationWish(faction.getId());
  }
  
  public void setRelationWish(String factionId, Rel rel)
  {
    Map<String, Rel> relationWishes = getRelationWishes();
    if ((rel == null) || (rel == Rel.NEUTRAL))
    {
      relationWishes.remove(factionId);
    }
    else
    {
      relationWishes.put(factionId, rel);
    }
    setRelationWishes(relationWishes);
  }
  
  public void setRelationWish(Faction faction, Rel rel)
  {
    setRelationWish(faction.getId(), rel);
  }
  







  public Map<MFlag, Boolean> getFlags()
  {
    Map<MFlag, Boolean> ret = new MassiveMap();
    for (MFlag mflag : MFlag.getAll())
    {
      ret.put(mflag, Boolean.valueOf(mflag.isStandard()));
    }
    

    Object iter = flags.entrySet().iterator();
    while (((Iterator)iter).hasNext())
    {

      Map.Entry<String, Boolean> entry = (Map.Entry)((Iterator)iter).next();
      

      String id = (String)entry.getKey();
      if (id == null)
      {
        ((Iterator)iter).remove();
        changed();

      }
      else
      {
        MFlag mflag = MFlag.get(id);
        if (mflag != null)
        {
          ret.put(mflag, entry.getValue()); }
      }
    }
    return ret;
  }
  
  public void setFlags(Map<MFlag, Boolean> flags)
  {
    Map<String, Boolean> flagIds = new MassiveMap();
    for (Map.Entry<MFlag, Boolean> entry : flags.entrySet())
    {
      flagIds.put(((MFlag)entry.getKey()).getId(), entry.getValue());
    }
    setFlagIds(flagIds);
  }
  

  public void setFlagIds(Map<String, Boolean> flagIds)
  {
    MassiveMapDef<String, Boolean> target = new MassiveMapDef();
    for (Map.Entry<String, Boolean> entry : flagIds.entrySet())
    {
      String key = (String)entry.getKey();
      if (key != null) {
        key = key.toLowerCase();
        
        Boolean value = (Boolean)entry.getValue();
        if (value != null)
        {
          target.put(key, value);
        }
      }
    }
    if (MUtil.equals(flags, target)) { return;
    }
    
    flags = new MassiveMapDef(target);
    

    changed();
  }
  


  public boolean getFlag(String flagId)
  {
    if (flagId == null) { throw new NullPointerException("flagId");
    }
    Boolean ret = (Boolean)flags.get(flagId);
    if (ret != null) { return ret.booleanValue();
    }
    MFlag flag = MFlag.get(flagId);
    if (flag == null) { throw new NullPointerException("flag");
    }
    return flag.isStandard();
  }
  
  public boolean getFlag(MFlag flag)
  {
    if (flag == null) { throw new NullPointerException("flag");
    }
    String flagId = flag.getId();
    if (flagId == null) { throw new NullPointerException("flagId");
    }
    Boolean ret = (Boolean)flags.get(flagId);
    if (ret != null) { return ret.booleanValue();
    }
    return flag.isStandard();
  }
  
  public Boolean setFlag(String flagId, boolean value)
  {
    if (flagId == null) { throw new NullPointerException("flagId");
    }
    Boolean ret = (Boolean)flags.put(flagId, Boolean.valueOf(value));
    if ((ret == null) || (ret.booleanValue() != value)) changed();
    return ret;
  }
  
  public Boolean setFlag(MFlag flag, boolean value)
  {
    if (flag == null) { throw new NullPointerException("flag");
    }
    String flagId = flag.getId();
    if (flagId == null) { throw new NullPointerException("flagId");
    }
    Boolean ret = (Boolean)flags.put(flagId, Boolean.valueOf(value));
    if ((ret == null) || (ret.booleanValue() != value)) changed();
    return ret;
  }
  







  public Map<MPerm, Set<Rel>> getPerms()
  {
    Map<MPerm, Set<Rel>> ret = new MassiveMap();
    for (MPerm mperm : MPerm.getAll())
    {
      ret.put(mperm, new MassiveSet(mperm.getStandard()));
    }
    

    Object iter = perms.entrySet().iterator();
    while (((Iterator)iter).hasNext())
    {

      Map.Entry<String, Set<Rel>> entry = (Map.Entry)((Iterator)iter).next();
      

      String id = (String)entry.getKey();
      if (id == null)
      {
        ((Iterator)iter).remove();

      }
      else
      {
        MPerm mperm = MPerm.get(id);
        if (mperm != null)
        {
          ret.put(mperm, new MassiveSet((Collection)entry.getValue())); }
      }
    }
    return ret;
  }
  
  public void setPerms(Map<MPerm, Set<Rel>> perms)
  {
    Map<String, Set<Rel>> permIds = new MassiveMap();
    for (Map.Entry<MPerm, Set<Rel>> entry : perms.entrySet())
    {
      permIds.put(((MPerm)entry.getKey()).getId(), entry.getValue());
    }
    setPermIds(permIds);
  }
  

  public void setPermIds(Map<String, Set<Rel>> perms)
  {
    MassiveMapDef<String, Set<Rel>> target = new MassiveMapDef();
    for (Map.Entry<String, Set<Rel>> entry : perms.entrySet())
    {
      String key = (String)entry.getKey();
      if (key != null) {
        key = key.toLowerCase();
        
        Set<Rel> value = (Set)entry.getValue();
        if (value != null)
        {
          target.put(key, value);
        }
      }
    }
    if (MUtil.equals(this.perms, target)) { return;
    }
    
    this.perms = target;
    

    changed();
  }
  


  public boolean isPermitted(String permId, Rel rel)
  {
    if (permId == null) { throw new NullPointerException("permId");
    }
    Set<Rel> rels = (Set)perms.get(permId);
    if (rels != null) { return rels.contains(rel);
    }
    MPerm perm = MPerm.get(permId);
    if (perm == null) { throw new NullPointerException("perm");
    }
    return perm.getStandard().contains(rel);
  }
  
  public boolean isPermitted(MPerm perm, Rel rel)
  {
    if (perm == null) { throw new NullPointerException("perm");
    }
    String permId = perm.getId();
    if (permId == null) { throw new NullPointerException("permId");
    }
    Set<Rel> rels = (Set)perms.get(permId);
    if (rels != null) { return rels.contains(rel);
    }
    return perm.getStandard().contains(rel);
  }
  


  public Set<Rel> getPermitted(MPerm perm)
  {
    if (perm == null) { throw new NullPointerException("perm");
    }
    String permId = perm.getId();
    if (permId == null) { throw new NullPointerException("permId");
    }
    Set<Rel> rels = (Set)perms.get(permId);
    if (rels != null) { return rels;
    }
    return perm.getStandard();
  }
  
  public Set<Rel> getPermitted(String permId)
  {
    if (permId == null) { throw new NullPointerException("permId");
    }
    Set<Rel> rels = (Set)perms.get(permId);
    if (rels != null) { return rels;
    }
    MPerm perm = MPerm.get(permId);
    if (perm == null) { throw new NullPointerException("perm");
    }
    return perm.getStandard();
  }
  

  @Deprecated
  public Set<Rel> getPermittedRelations(MPerm perm)
  {
    return (Set)getPerms().get(perm);
  }
  



  public void setPermittedRelations(MPerm perm, Set<Rel> rels)
  {
    Map<MPerm, Set<Rel>> perms = getPerms();
    perms.put(perm, rels);
    setPerms(perms);
  }
  
  public void setPermittedRelations(MPerm perm, Rel... rels)
  {
    Set<Rel> temp = new HashSet();
    temp.addAll(Arrays.asList(rels));
    setPermittedRelations(perm, temp);
  }
  
  public void setRelationPermitted(MPerm perm, Rel rel, boolean permitted)
  {
    Map<MPerm, Set<Rel>> perms = getPerms();
    
    Set<Rel> rels = (Set)perms.get(perm);
    boolean changed;
    boolean changed;
    if (permitted)
    {
      changed = rels.add(rel);
    }
    else
    {
      changed = rels.remove(rel);
    }
    
    setPerms(perms);
    
    if (changed) { changed();
    }
  }
  




  public String describeTo(RelationParticipator observer, boolean ucfirst)
  {
    return RelationUtil.describeThatToMe(this, observer, ucfirst);
  }
  

  public String describeTo(RelationParticipator observer)
  {
    return RelationUtil.describeThatToMe(this, observer);
  }
  

  public Rel getRelationTo(RelationParticipator observer)
  {
    return RelationUtil.getRelationOfThatToMe(this, observer);
  }
  

  public Rel getRelationTo(RelationParticipator observer, boolean ignorePeaceful)
  {
    return RelationUtil.getRelationOfThatToMe(this, observer, ignorePeaceful);
  }
  

  public ChatColor getColorTo(RelationParticipator observer)
  {
    return RelationUtil.getColorOfThatToMe(this, observer);
  }
  





  public double getPower()
  {
    if (getFlag(MFlag.getFlagInfpower())) { return 999999.0D;
    }
    double ret = 0.0D;
    for (MPlayer mplayer : getMPlayers())
    {
      ret += mplayer.getPower();
    }
    
    ret = limitWithPowerMax(ret);
    ret += getPowerBoost();
    
    return ret;
  }
  
  public double getPowerMax()
  {
    if (getFlag(MFlag.getFlagInfpower())) { return 999999.0D;
    }
    double ret = 0.0D;
    for (MPlayer mplayer : getMPlayers())
    {
      ret += mplayer.getPowerMax();
    }
    
    ret = limitWithPowerMax(ret);
    ret += getPowerBoost();
    
    return ret;
  }
  

  private double limitWithPowerMax(double power)
  {
    double powerMax = getfactionPowerMax;
    
    return (powerMax <= 0.0D) || (power < powerMax) ? power : powerMax;
  }
  
  public int getPowerRounded()
  {
    return (int)Math.round(getPower());
  }
  
  public int getPowerMaxRounded()
  {
    return (int)Math.round(getPowerMax());
  }
  
  public int getLandCount()
  {
    return BoardColl.get().getCount(this);
  }
  
  public int getLandCountInWorld(String worldName) {
    return Board.get(worldName).getCount(this);
  }
  
  public boolean hasLandInflation()
  {
    return getLandCount() > getPowerRounded();
  }
  




  public Set<String> getClaimedWorlds()
  {
    return BoardColl.get().getClaimedWorlds(this);
  }
  




  public List<MPlayer> getMPlayers()
  {
    return new MassiveList(FactionsIndex.get().getMPlayers(this));
  }
  
  public List<MPlayer> getMPlayers(Predicate<? super MPlayer> where, Comparator<? super MPlayer> orderby, Integer limit, Integer offset)
  {
    return MUtil.transform(getMPlayers(), where, orderby, limit, offset);
  }
  
  public List<MPlayer> getMPlayersWhere(Predicate<? super MPlayer> predicate)
  {
    return getMPlayers(predicate, null, null, null);
  }
  
  public List<MPlayer> getMPlayersWhereOnline(boolean online)
  {
    return getMPlayersWhere(online ? SenderColl.PREDICATE_ONLINE : SenderColl.PREDICATE_OFFLINE);
  }
  
  public List<MPlayer> getMPlayersWhereOnlineTo(Object senderObject)
  {
    return getMPlayersWhere(PredicateAnd.get(new Predicate[] { SenderColl.PREDICATE_ONLINE, PredicateVisibleTo.get(senderObject) }));
  }
  
  public List<MPlayer> getMPlayersWhereRole(Rel role)
  {
    return getMPlayersWhere(PredicateMPlayerRole.get(role));
  }
  
  public MPlayer getLeader()
  {
    List<MPlayer> ret = getMPlayersWhereRole(Rel.LEADER);
    if (ret.size() == 0) return null;
    return (MPlayer)ret.get(0);
  }
  

  public List<CommandSender> getOnlineCommandSenders()
  {
    List<CommandSender> ret = new MassiveList();
    

    for (CommandSender sender : IdUtil.getLocalSenders())
    {
      if (!MUtil.isntSender(sender))
      {
        MPlayer mplayer = MPlayer.get(sender);
        if (mplayer.getFaction() == this)
        {
          ret.add(sender);
        }
      }
    }
    return ret;
  }
  

  public List<Player> getOnlinePlayers()
  {
    List<Player> ret = new MassiveList();
    

    for (Player player : MUtil.getOnlinePlayers())
    {
      if (!MUtil.isntPlayer(player))
      {
        MPlayer mplayer = MPlayer.get(player);
        if (mplayer.getFaction() == this)
        {
          ret.add(player);
        }
      }
    }
    return ret;
  }
  

  public void promoteNewLeader()
  {
    if (!isNormal()) return;
    if ((getFlag(MFlag.getFlagPermanent())) && (getpermanentFactionsDisableLeaderPromotion)) { return;
    }
    MPlayer oldLeader = getLeader();
    

    List<MPlayer> replacements = getMPlayersWhereRole(Rel.OFFICER);
    if ((replacements == null) || (replacements.isEmpty()))
    {
      replacements = getMPlayersWhereRole(Rel.MEMBER);
    }
    
    if ((replacements == null) || (replacements.isEmpty()))
    {

      if (getFlag(MFlag.getFlagPermanent()))
      {
        if (oldLeader != null)
        {

          oldLeader.setRole(Rel.MEMBER);
        }
        return;
      }
      

      if (getlogFactionDisband)
      {
        Factions.get().log(new Object[] { "The faction " + getName() + " (" + getId() + ") has been disbanded since it has no members left." });
      }
      
      for (MPlayer mplayer : MPlayerColl.get().getAllOnline())
      {
        mplayer.msg("<i>The faction %s<i> was disbanded.", new Object[] { getName(mplayer) });
      }
      
      detach();

    }
    else
    {
      if (oldLeader != null)
      {
        oldLeader.setRole(Rel.MEMBER);
      }
      
      ((MPlayer)replacements.get(0)).setRole(Rel.LEADER);
      msg("<i>Faction leader <h>%s<i> has been removed. %s<i> has been promoted as the new faction leader.", new Object[] { oldLeader == null ? "" : oldLeader.getName(), ((MPlayer)replacements.get(0)).getName() });
      Factions.get().log(new Object[] { "Faction " + getName() + " (" + getId() + ") leader was removed. Replacement leader: " + ((MPlayer)replacements.get(0)).getName() });
    }
  }
  




  public boolean isAllMPlayersOffline()
  {
    return getMPlayersWhereOnline(true).size() == 0;
  }
  
  public boolean isAnyMPlayersOnline()
  {
    return !isAllMPlayersOffline();
  }
  
  public boolean isFactionConsideredOffline()
  {
    return isAllMPlayersOffline();
  }
  
  public boolean isFactionConsideredOnline()
  {
    return !isFactionConsideredOffline();
  }
  
  public boolean isExplosionsAllowed()
  {
    boolean explosions = getFlag(MFlag.getFlagExplosions());
    boolean offlineexplosions = getFlag(MFlag.getFlagOfflineexplosions());
    
    if ((explosions) && (offlineexplosions)) return true;
    if ((!explosions) && (!offlineexplosions)) { return false;
    }
    boolean online = isFactionConsideredOnline();
    
    return ((online) && (explosions)) || ((!online) && (offlineexplosions));
  }
  







  public boolean sendMessage(Object message)
  {
    return MixinMessage.get().messagePredicate(new PredicateCommandSenderFaction(this), message);
  }
  
  public boolean sendMessage(Object... messages)
  {
    return MixinMessage.get().messagePredicate(new PredicateCommandSenderFaction(this), messages);
  }
  
  public boolean sendMessage(Collection<Object> messages)
  {
    return MixinMessage.get().messagePredicate(new PredicateCommandSenderFaction(this), messages);
  }
  


  public boolean msg(String msg)
  {
    return MixinMessage.get().msgPredicate(new PredicateCommandSenderFaction(this), msg);
  }
  
  public boolean msg(String msg, Object... args)
  {
    return MixinMessage.get().msgPredicate(new PredicateCommandSenderFaction(this), msg, args);
  }
  
  public boolean msg(Collection<String> msgs)
  {
    return MixinMessage.get().msgPredicate(new PredicateCommandSenderFaction(this), msgs);
  }
  





  public static String clean(String message)
  {
    String target = message;
    if (target == null) { return null;
    }
    target = target.trim();
    if (target.isEmpty()) { target = null;
    }
    return target;
  }
}
