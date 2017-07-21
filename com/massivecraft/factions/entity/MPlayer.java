package com.massivecraft.factions.entity;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.FactionsIndex;
import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.factions.event.EventFactionsRemovePlayerMillis;
import com.massivecraft.factions.mixin.PowerMixin;
import com.massivecraft.factions.util.RelationUtil;
import com.massivecraft.massivecore.mixin.MixinSenderPs;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSFormatHumanSpace;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivecore.xlib.gson.annotations.SerializedName;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MPlayer
  extends SenderEntity<MPlayer>
  implements FactionsParticipator
{
  public static final transient String NOTITLE = Txt.parse("<em><silver>no title set");
  

  public MPlayer() {}
  

  public static MPlayer get(Object oid)
  {
    return (MPlayer)MPlayerColl.get().get(oid);
  }
  





  public MPlayer load(MPlayer that)
  {
    setLastActivityMillis(lastActivityMillis);
    setFactionId(factionId);
    setRole(role);
    setTitle(title);
    setPowerBoost(powerBoost);
    setPower(power);
    setMapAutoUpdating(mapAutoUpdating);
    setOverriding(overriding);
    setTerritoryInfoTitles(territoryInfoTitles);
    
    return this;
  }
  






  public boolean isDefault()
  {
    if (hasFaction()) { return false;
    }
    
    if (hasPowerBoost()) return false;
    if (getPowerRounded() != (int)Math.round(getdefaultPlayerPower)) { return false;
    }
    if (isOverriding()) return false;
    if (isTerritoryInfoTitles() != getterritoryInfoTitlesDefault) { return false;
    }
    return true;
  }
  





  public void postAttach(String id)
  {
    FactionsIndex.get().update(this);
  }
  

  public void preDetach(String id)
  {
    FactionsIndex.get().update(this);
  }
  














  private long lastActivityMillis = System.currentTimeMillis();
  



  private String factionId = null;
  


  private Rel role = null;
  









  private String title = null;
  



  private Double powerBoost = null;
  




  private Double power = null;
  


  private Boolean mapAutoUpdating = null;
  

  @SerializedName("usingAdminMode")
  private Boolean overriding = null;
  



  private Boolean territoryInfoTitles = null;
  



  private transient WeakReference<Faction> autoClaimFaction = new WeakReference(null);
  
  public Faction getAutoClaimFaction()
  {
    if (isFactionOrphan()) return null;
    Faction ret = (Faction)autoClaimFaction.get();
    if (ret == null) return null;
    if (ret.detached()) return null;
    return ret; }
  
  public void setAutoClaimFaction(Faction autoClaimFaction) { this.autoClaimFaction = new WeakReference(autoClaimFaction); }
  


  private transient boolean seeingChunk = false;
  public boolean isSeeingChunk() { return seeingChunk; }
  public void setSeeingChunk(boolean seeingChunk) { this.seeingChunk = seeingChunk; }
  





  public void resetFactionData()
  {
    setFactionId(null);
    setRole(null);
    setTitle(null);
    setAutoClaimFaction(null);
  }
  




  public long getLastActivityMillis()
  {
    return lastActivityMillis;
  }
  

  public void setLastActivityMillis(long lastActivityMillis)
  {
    long target = lastActivityMillis;
    

    if (MUtil.equals(Long.valueOf(this.lastActivityMillis), Long.valueOf(target))) { return;
    }
    
    this.lastActivityMillis = target;
    

    changed();
  }
  
  public void setLastActivityMillis()
  {
    setLastActivityMillis(System.currentTimeMillis());
  }
  




  private Faction getFactionInternal()
  {
    String effectiveFactionId = (String)convertGet(factionId, getdefaultPlayerFactionId);
    return Faction.get(effectiveFactionId);
  }
  
  public boolean isFactionOrphan()
  {
    return getFactionInternal() == null;
  }
  
  @Deprecated
  public String getFactionId()
  {
    return getFaction().getId();
  }
  



  public Faction getFaction()
  {
    Faction ret = getFactionInternal();
    

    if (ret == null)
    {
      ret = FactionColl.get().getNone();
    }
    
    return ret;
  }
  
  public boolean hasFaction()
  {
    return !getFaction().isNone();
  }
  



  public void setFactionId(String factionId)
  {
    String beforeId = this.factionId;
    

    String afterId = factionId;
    

    if (MUtil.equals(beforeId, afterId)) { return;
    }
    
    this.factionId = afterId;
    

    FactionsIndex.get().update(this);
    

    changed();
  }
  
  public void setFaction(Faction faction)
  {
    setFactionId(faction.getId());
  }
  




  public Rel getRole()
  {
    if (isFactionOrphan()) { return Rel.RECRUIT;
    }
    if (role == null) return getdefaultPlayerRole;
    return role;
  }
  

  public void setRole(Rel role)
  {
    Rel target = role;
    

    if (MUtil.equals(this.role, target)) { return;
    }
    
    this.role = target;
    

    changed();
  }
  






  public boolean hasTitle()
  {
    return (!isFactionOrphan()) && (title != null);
  }
  
  public String getTitle()
  {
    if (isFactionOrphan()) { return NOTITLE;
    }
    if (hasTitle()) { return title;
    }
    return NOTITLE;
  }
  

  public void setTitle(String title)
  {
    String target = Faction.clean(title);
    

    if (MUtil.equals(this.title, target)) { return;
    }
    
    this.title = target;
    

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
  
  public boolean hasPowerBoost()
  {
    return getPowerBoost() != 0.0D;
  }
  






  public double getPowerMaxUniversal()
  {
    return PowerMixin.get().getMaxUniversal(this);
  }
  
  public double getPowerMax()
  {
    return PowerMixin.get().getMax(this);
  }
  
  public double getPowerMin()
  {
    return PowerMixin.get().getMin(this);
  }
  
  public double getPowerPerHour()
  {
    return PowerMixin.get().getPerHour(this);
  }
  
  public double getPowerPerDeath()
  {
    return PowerMixin.get().getPerDeath(this);
  }
  


  public double getLimitedPower(double power)
  {
    power = Math.max(power, getPowerMin());
    power = Math.min(power, getPowerMax());
    
    return power;
  }
  
  public int getPowerMaxRounded()
  {
    return (int)Math.round(getPowerMax());
  }
  
  public int getPowerMinRounded()
  {
    return (int)Math.round(getPowerMin());
  }
  
  public int getPowerMaxUniversalRounded()
  {
    return (int)Math.round(getPowerMaxUniversal());
  }
  


  @Deprecated
  public double getDefaultPower()
  {
    return getdefaultPlayerPower;
  }
  
  public double getPower()
  {
    Double ret = power;
    if (ret == null) ret = Double.valueOf(getdefaultPlayerPower);
    ret = Double.valueOf(getLimitedPower(ret.doubleValue()));
    return ret.doubleValue();
  }
  

  public void setPower(Double power)
  {
    Double target = power;
    

    if (MUtil.equals(this.power, target)) { return;
    }
    
    this.power = target;
    

    changed();
  }
  


  public int getPowerRounded()
  {
    return (int)Math.round(getPower());
  }
  




  public boolean isMapAutoUpdating()
  {
    if (mapAutoUpdating == null) return false;
    if (!mapAutoUpdating.booleanValue()) return false;
    return true;
  }
  

  public void setMapAutoUpdating(Boolean mapAutoUpdating)
  {
    Boolean target = mapAutoUpdating;
    if (MUtil.equals(target, Boolean.valueOf(false))) { target = null;
    }
    
    if (MUtil.equals(this.mapAutoUpdating, target)) { return;
    }
    
    this.mapAutoUpdating = target;
    

    changed();
  }
  




  public boolean isOverriding()
  {
    if (overriding == null) return false;
    if (!overriding.booleanValue()) { return false;
    }
    if (!hasPermission(Perm.OVERRIDE, Boolean.valueOf(true)).booleanValue())
    {
      setOverriding(Boolean.valueOf(false));
      return false;
    }
    
    return true;
  }
  

  public void setOverriding(Boolean overriding)
  {
    Boolean target = overriding;
    if (MUtil.equals(target, Boolean.valueOf(false))) { target = null;
    }
    
    if (MUtil.equals(this.overriding, target)) { return;
    }
    
    this.overriding = target;
    

    changed();
  }
  




  public boolean isTerritoryInfoTitles()
  {
    if (!MixinTitle.get().isAvailable()) return false;
    if (territoryInfoTitles == null) return getterritoryInfoTitlesDefault;
    return territoryInfoTitles.booleanValue();
  }
  

  public void setTerritoryInfoTitles(Boolean territoryInfoTitles)
  {
    Boolean target = territoryInfoTitles;
    if (MUtil.equals(target, Boolean.valueOf(getterritoryInfoTitlesDefault))) { target = null;
    }
    
    if (MUtil.equals(this.territoryInfoTitles, target)) { return;
    }
    
    this.territoryInfoTitles = target;
    

    changed();
  }
  




  public String getFactionName()
  {
    Faction faction = getFaction();
    if (faction.isNone()) return "";
    return faction.getName();
  }
  


  public String getNameAndSomething(String color, String something)
  {
    String ret = "";
    ret = ret + color;
    ret = ret + getRole().getPrefix();
    if ((something != null) && (something.length() > 0))
    {
      ret = ret + something;
      ret = ret + " ";
      ret = ret + color;
    }
    ret = ret + getName();
    return ret;
  }
  
  public String getNameAndFactionName()
  {
    return getNameAndSomething("", getFactionName());
  }
  
  public String getNameAndTitle(String color)
  {
    if (hasTitle())
    {
      return getNameAndSomething(color, getTitle());
    }
    

    return getNameAndSomething(color, null);
  }
  




  public String getNameAndTitle(Faction faction)
  {
    return getNameAndTitle(getColorTo(faction).toString());
  }
  
  public String getNameAndTitle(MPlayer mplayer)
  {
    return getNameAndTitle(getColorTo(mplayer).toString());
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
  




  public void heal(int amnt)
  {
    Player player = getPlayer();
    if (player == null)
    {
      return;
    }
    player.setHealth(player.getHealth() + amnt);
  }
  




  public boolean isInOwnTerritory()
  {
    PS ps = MixinSenderPs.get().getSenderPs(getId());
    if (ps == null) return false;
    return BoardColl.get().getFactionAt(ps) == getFaction();
  }
  
  public boolean isInEnemyTerritory()
  {
    PS ps = MixinSenderPs.get().getSenderPs(getId());
    if (ps == null) return false;
    return BoardColl.get().getFactionAt(ps).getRelationTo(this) == Rel.ENEMY;
  }
  




  public long getRemovePlayerMillis(boolean async)
  {
    EventFactionsRemovePlayerMillis event = new EventFactionsRemovePlayerMillis(async, this);
    event.run();
    return event.getMillis();
  }
  




  public boolean considerRemovePlayerMillis(boolean async)
  {
    if (detached()) { return false;
    }
    
    long lastActivityMillis = getLastActivityMillis();
    

    long toleranceMillis = getRemovePlayerMillis(async);
    if (System.currentTimeMillis() - lastActivityMillis <= toleranceMillis) { return false;
    }
    
    if ((getlogFactionLeave) || (getlogFactionKick))
    {
      Factions.get().log(new Object[] { "Player " + getName() + " was auto-removed due to inactivity." });
    }
    



    if (getRole() == Rel.LEADER)
    {
      Faction faction = getFaction();
      if (faction != null)
      {
        getFaction().promoteNewLeader();
      }
    }
    
    leave();
    detach();
    
    return true;
  }
  




  public void leave()
  {
    Faction myFaction = getFaction();
    
    boolean permanent = myFaction.getFlag(MFlag.getFlagPermanent());
    
    if (myFaction.getMPlayers().size() > 1)
    {
      if ((!permanent) && (getRole() == Rel.LEADER))
      {
        msg("<b>You must give the leader role to someone else first.");
        return;
      }
      
      if ((!getcanLeaveWithNegativePower) && (getPower() < 0.0D))
      {
        msg("<b>You cannot leave until your power is positive.");
        return;
      }
    }
    

    EventFactionsMembershipChange membershipChangeEvent = new EventFactionsMembershipChange(getSender(), this, myFaction, EventFactionsMembershipChange.MembershipChangeReason.LEAVE);
    membershipChangeEvent.run();
    if (membershipChangeEvent.isCancelled()) { return;
    }
    if (myFaction.isNormal())
    {
      for (MPlayer mplayer : myFaction.getMPlayersWhereOnline(true))
      {
        mplayer.msg("%s<i> left %s<i>.", new Object[] { describeTo(mplayer, true), myFaction.describeTo(mplayer) });
      }
      
      if (getlogFactionLeave)
      {
        Factions.get().log(new Object[] { getName() + " left the faction: " + myFaction.getName() });
      }
    }
    
    resetFactionData();
    
    if ((myFaction.isNormal()) && (!permanent) && (myFaction.getMPlayers().isEmpty()))
    {
      EventFactionsDisband eventFactionsDisband = new EventFactionsDisband(getSender(), myFaction);
      eventFactionsDisband.run();
      if (!eventFactionsDisband.isCancelled())
      {

        msg("%s <i>was disbanded since you were the last player.", new Object[] { myFaction.describeTo(this, true) });
        if (getlogFactionDisband)
        {
          Factions.get().log(new Object[] { "The faction " + myFaction.getName() + " (" + myFaction.getId() + ") was disbanded due to the last player (" + getName() + ") leaving." });
        }
        myFaction.detach();
      }
    }
  }
  

  public boolean tryClaim(Faction newFaction, Collection<PS> pss)
  {
    return tryClaim(newFaction, pss, null, null);
  }
  

  public boolean tryClaim(Faction newFaction, Collection<PS> pss, String formatOne, String formatMany)
  {
    if (formatOne == null) formatOne = "<h>%s<i> %s <h>%d <i>chunk %s<i>.";
    if (formatMany == null) { formatMany = "<h>%s<i> %s <h>%d <i>chunks near %s<i>.";
    }
    if (newFaction == null) { throw new NullPointerException("newFaction");
    }
    if (pss == null) throw new NullPointerException("pss");
    Set<PS> chunks = PS.getDistinctChunks(pss);
    



    Iterator<PS> iter = chunks.iterator();
    while (iter.hasNext())
    {
      PS chunk = (PS)iter.next();
      Faction oldFaction = BoardColl.get().getFactionAt(chunk);
      if (newFaction == oldFaction) iter.remove();
    }
    if (chunks.isEmpty())
    {
      msg("%s<i> already owns this land.", new Object[] { newFaction.describeTo(this, true) });
      return true;
    }
    



    CommandSender sender = getSender();
    if (sender == null)
    {
      msg("<b>ERROR: Your \"CommandSender Link\" has been severed.");
      msg("<b>It's likely that you are using Cauldron.");
      msg("<b>We do currently not support Cauldron.");
      msg("<b>We would love to but lack time to develop support ourselves.");
      msg("<g>Do you know how to code? Please send us a pull request <3, sorry.");
      return false;
    }
    EventFactionsChunksChange event = new EventFactionsChunksChange(sender, chunks, newFaction);
    event.run();
    if (event.isCancelled()) { return false;
    }
    
    for (PS chunk : chunks)
    {
      BoardColl.get().setFactionAt(chunk, newFaction);
    }
    

    for (Map.Entry<Faction, Set<PS>> entry : event.getOldFactionChunks().entrySet())
    {
      oldFaction = (Faction)entry.getKey();
      oldChunks = (Set)entry.getValue();
      PS oldChunk = (PS)oldChunks.iterator().next();
      Set<MPlayer> informees = getClaimInformees(this, new Faction[] { oldFaction, newFaction });
      EventFactionsChunkChangeType type = EventFactionsChunkChangeType.get(oldFaction, newFaction, getFaction());
      
      chunkString = oldChunk.toString(PSFormatHumanSpace.get());
      typeString = past;
      
      for (MPlayer informee : informees)
      {
        informee.msg(oldChunks.size() == 1 ? formatOne : formatMany, new Object[] { describeTo(informee, true), typeString, Integer.valueOf(oldChunks.size()), chunkString });
        informee.msg("  <h>%s<i> --> <h>%s", new Object[] { oldFaction.describeTo(informee, true), newFaction.describeTo(informee, true) }); } }
    Faction oldFaction;
    Set<PS> oldChunks;
    String chunkString;
    String typeString;
    return true;
  }
  




  public static Set<MPlayer> getClaimInformees(MPlayer msender, Faction... factions)
  {
    Set<MPlayer> ret = new HashSet();
    
    if (msender != null) { ret.add(msender);
    }
    for (Faction faction : factions)
    {
      if ((faction != null) && 
        (!faction.isNone())) {
        ret.addAll(faction.getMPlayers());
      }
    }
    if (getlogLandClaims)
    {
      ret.add(get(IdUtil.getConsole()));
    }
    
    return ret;
  }
}
