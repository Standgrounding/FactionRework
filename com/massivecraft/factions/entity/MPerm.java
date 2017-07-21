package com.massivecraft.factions.entity;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.TerritoryAccess;
import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.factions.cmd.CmdFactionsOverride;
import com.massivecraft.factions.event.EventFactionsCreatePerms;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.Prioritized;
import com.massivecraft.massivecore.Registerable;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.predicate.PredicateIsRegistered;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;








public class MPerm
  extends Entity<MPerm>
  implements Prioritized, Registerable, Named
{
  public static final transient String ID_BUILD = "build";
  public static final transient String ID_PAINBUILD = "painbuild";
  public static final transient String ID_DOOR = "door";
  public static final transient String ID_BUTTON = "button";
  public static final transient String ID_LEVER = "lever";
  public static final transient String ID_CONTAINER = "container";
  public static final transient String ID_NAME = "name";
  public static final transient String ID_DESC = "desc";
  public static final transient String ID_MOTD = "motd";
  public static final transient String ID_INVITE = "invite";
  public static final transient String ID_KICK = "kick";
  public static final transient String ID_TITLE = "title";
  public static final transient String ID_HOME = "home";
  public static final transient String ID_SETHOME = "sethome";
  public static final transient String ID_DEPOSIT = "deposit";
  public static final transient String ID_WITHDRAW = "withdraw";
  public static final transient String ID_TERRITORY = "territory";
  public static final transient String ID_ACCESS = "access";
  public static final transient String ID_CLAIMNEAR = "claimnear";
  public static final transient String ID_REL = "rel";
  public static final transient String ID_DISBAND = "disband";
  public static final transient String ID_FLAGS = "flags";
  public static final transient String ID_PERMS = "perms";
  public static final transient String ID_STATUS = "status";
  public static final transient int PRIORITY_BUILD = 1000;
  public static final transient int PRIORITY_PAINBUILD = 2000;
  public static final transient int PRIORITY_DOOR = 3000;
  public static final transient int PRIORITY_BUTTON = 4000;
  public static final transient int PRIORITY_LEVER = 5000;
  public static final transient int PRIORITY_CONTAINER = 6000;
  public static final transient int PRIORITY_NAME = 7000;
  public static final transient int PRIORITY_DESC = 8000;
  public static final transient int PRIORITY_MOTD = 9000;
  public static final transient int PRIORITY_INVITE = 10000;
  public static final transient int PRIORITY_KICK = 11000;
  public static final transient int PRIORITY_TITLE = 12000;
  public static final transient int PRIORITY_HOME = 13000;
  public static final transient int PRIORITY_SETHOME = 14000;
  public static final transient int PRIORITY_DEPOSIT = 15000;
  public static final transient int PRIORITY_WITHDRAW = 16000;
  public static final transient int PRIORITY_TERRITORY = 17000;
  public static final transient int PRIORITY_ACCESS = 18000;
  public static final transient int PRIORITY_CLAIMNEAR = 19000;
  public static final transient int PRIORITY_REL = 20000;
  public static final transient int PRIORITY_DISBAND = 21000;
  public static final transient int PRIORITY_FLAGS = 22000;
  public static final transient int PRIORITY_PERMS = 23000;
  public static final transient int PRIORITY_STATUS = 24000;
  
  public static MPerm get(Object oid)
  {
    return (MPerm)MPermColl.get().get(oid);
  }
  
  public static List<MPerm> getAll()
  {
    return getAll(false);
  }
  
  public static List<MPerm> getAll(boolean isAsync)
  {
    setupStandardPerms();
    new EventFactionsCreatePerms().run();
    
    return MPermColl.get().getAll(PredicateIsRegistered.get(), ComparatorSmart.get());
  }
  
  public static void setupStandardPerms()
  {
    getPermBuild();
    getPermPainbuild();
    getPermDoor();
    getPermButton();
    getPermLever();
    getPermContainer();
    
    getPermName();
    getPermDesc();
    getPermMotd();
    getPermInvite();
    getPermKick();
    getPermTitle();
    getPermHome();
    getPermStatus();
    getPermSethome();
    getPermDeposit();
    getPermWithdraw();
    getPermTerritory();
    getPermAccess();
    getPermClaimnear();
    getPermRel();
    getPermDisband();
    getPermFlags();
    getPermPerms();
  }
  
  public static MPerm getPermBuild() { return getCreative(1000, "build", "build", "edit the terrain", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER }), true, true, true); }
  public static MPerm getPermPainbuild() { return getCreative(2000, "painbuild", "painbuild", "edit, take damage", new LinkedHashSet(), true, true, true); }
  public static MPerm getPermDoor() { return getCreative(3000, "door", "door", "use doors", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY }), true, true, true); }
  public static MPerm getPermButton() { return getCreative(4000, "button", "button", "use stone buttons", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY }), true, true, true); }
  public static MPerm getPermLever() { return getCreative(5000, "lever", "lever", "use levers", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY }), true, true, true); }
  public static MPerm getPermContainer() { return getCreative(6000, "container", "container", "use containers", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER }), true, true, true); }
  
  public static MPerm getPermName() { return getCreative(7000, "name", "name", "set name", MUtil.set(new Rel[] { Rel.LEADER }), false, true, true); }
  public static MPerm getPermDesc() { return getCreative(8000, "desc", "desc", "set description", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermMotd() { return getCreative(9000, "motd", "motd", "set motd", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermInvite() { return getCreative(10000, "invite", "invite", "invite players", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermStatus() { return getCreative(24000, "status", "status", "show status", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermKick() { return getCreative(11000, "kick", "kick", "kick members", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermTitle() { return getCreative(12000, "title", "title", "set titles", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermHome() { return getCreative(13000, "home", "home", "teleport home", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY }), false, true, true); }
  public static MPerm getPermSethome() { return getCreative(14000, "sethome", "sethome", "set the home", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermDeposit() { return getCreative(15000, "deposit", "deposit", "deposit money", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY, Rel.TRUCE, Rel.NEUTRAL, Rel.ENEMY }), false, false, false); }
  public static MPerm getPermWithdraw() { return getCreative(16000, "withdraw", "withdraw", "withdraw money", MUtil.set(new Rel[] { Rel.LEADER }), false, true, true); }
  public static MPerm getPermTerritory() { return getCreative(17000, "territory", "territory", "claim or unclaim", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermAccess() { return getCreative(18000, "access", "access", "grant territory", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermClaimnear() { return getCreative(19000, "claimnear", "claimnear", "claim nearby", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY }), false, false, false); }
  public static MPerm getPermRel() { return getCreative(20000, "rel", "rel", "change relations", MUtil.set(new Rel[] { Rel.LEADER, Rel.OFFICER }), false, true, true); }
  public static MPerm getPermDisband() { return getCreative(21000, "disband", "disband", "disband the faction", MUtil.set(new Rel[] { Rel.LEADER }), false, true, true); }
  public static MPerm getPermFlags() { return getCreative(22000, "flags", "flags", "manage flags", MUtil.set(new Rel[] { Rel.LEADER }), false, true, true); }
  public static MPerm getPermPerms() { return getCreative(23000, "perms", "perms", "manage permissions", MUtil.set(new Rel[] { Rel.LEADER }), false, true, true); }
  
  public static MPerm getCreative(int priority, String id, String name, String desc, Set<Rel> standard, boolean territory, boolean editable, boolean visible)
  {
    MPerm ret = (MPerm)MPermColl.get().get(id, false);
    if (ret != null)
    {
      ret.setRegistered(true);
      return ret;
    }
    
    ret = new MPerm(priority, name, desc, standard, territory, editable, visible);
    MPermColl.get().attach(ret, id);
    ret.setRegistered(true);
    ret.sync();
    
    return ret;
  }
  





  public MPerm load(MPerm that)
  {
    priority = priority;
    name = name;
    desc = desc;
    standard = standard;
    territory = territory;
    editable = editable;
    visible = visible;
    
    return this;
  }
  




  private transient boolean registered = false;
  public boolean isRegistered() { return registered; }
  public void setRegistered(boolean registered) { this.registered = registered; }
  








  private int priority = 0;
  public int getPriority() { return priority; }
  public MPerm setPriority(int priority) { this.priority = priority;changed();return this;
  }
  



  private String name = "defaultName";
  public String getName() { return name; }
  public MPerm setName(String name) { this.name = name;changed();return this;
  }
  




  private String desc = "defaultDesc";
  public String getDesc() { return desc; }
  public MPerm setDesc(String desc) { this.desc = desc;changed();return this;
  }
  


  private Set<Rel> standard = new LinkedHashSet();
  public Set<Rel> getStandard() { return standard; }
  public MPerm setStandard(Set<Rel> standard) { this.standard = standard;changed();return this;
  }
  


  private boolean territory = false;
  public boolean isTerritory() { return territory; }
  public MPerm setTerritory(boolean territory) { this.territory = territory;changed();return this;
  }
  



  private boolean editable = false;
  public boolean isEditable() { return editable; }
  public MPerm setEditable(boolean editable) { this.editable = editable;changed();return this;
  }
  







  private boolean visible = true;
  public boolean isVisible() { return visible; }
  public MPerm setVisible(boolean visible) { this.visible = visible;changed();return this;
  }
  



  public MPerm() {}
  



  public MPerm(int priority, String name, String desc, Set<Rel> standard, boolean territory, boolean editable, boolean visible)
  {
    this.priority = priority;
    this.name = name;
    this.desc = desc;
    this.standard = standard;
    this.territory = territory;
    this.editable = editable;
    this.visible = visible;
  }
  





  public String createDeniedMessage(MPlayer mplayer, Faction hostFaction)
  {
    if (mplayer == null) throw new NullPointerException("mplayer");
    if (hostFaction == null) { throw new NullPointerException("hostFaction");
    }
    String ret = Txt.parse("%s<b> does not allow you to %s<b>.", new Object[] { hostFaction.describeTo(mplayer, true), getDesc() });
    
    Player player = mplayer.getPlayer();
    if ((player != null) && (Perm.OVERRIDE.has(player)))
    {
      ret = ret + Txt.parse(new StringBuilder().append("\n<i>You can bypass by using ").append(getcmdFactionsOverride.getTemplate(false).toPlain(true)).toString());
    }
    
    return ret;
  }
  
  public String getDesc(boolean withName, boolean withDesc)
  {
    List<String> parts = new ArrayList();
    
    if (withName) {
      String nameFormat;
      String nameFormat;
      if (!isVisible())
      {
        nameFormat = "<silver>%s";
      } else { String nameFormat;
        if (isEditable())
        {
          nameFormat = "<pink>%s";
        }
        else
        {
          nameFormat = "<aqua>%s"; }
      }
      String name = getName();
      String nameDesc = Txt.parse(nameFormat, new Object[] { name });
      parts.add(nameDesc);
    }
    
    if (withDesc)
    {
      String desc = getDesc();
      
      String descDesc = Txt.parse("<i>%s", new Object[] { desc });
      parts.add(descDesc);
    }
    
    return Txt.implode(parts, " ");
  }
  

  public boolean has(Faction faction, Faction hostFaction)
  {
    if (faction == null) throw new NullPointerException("faction");
    if (hostFaction == null) { throw new NullPointerException("hostFaction");
    }
    Rel rel = faction.getRelationTo(hostFaction);
    return hostFaction.isPermitted(this, rel);
  }
  

  public boolean has(MPlayer mplayer, Faction hostFaction, boolean verboose)
  {
    if (mplayer == null) throw new NullPointerException("mplayer");
    if (hostFaction == null) { throw new NullPointerException("hostFaction");
    }
    if (mplayer.isOverriding()) { return true;
    }
    Rel rel = mplayer.getRelationTo(hostFaction);
    if (hostFaction.isPermitted(this, rel)) { return true;
    }
    if (verboose) { mplayer.message(createDeniedMessage(mplayer, hostFaction));
    }
    return false;
  }
  

  public boolean has(MPlayer mplayer, PS ps, boolean verboose)
  {
    if (mplayer == null) throw new NullPointerException("mplayer");
    if (ps == null) { throw new NullPointerException("ps");
    }
    if (mplayer.isOverriding()) { return true;
    }
    TerritoryAccess ta = BoardColl.get().getTerritoryAccessAt(ps);
    Faction hostFaction = ta.getHostFaction();
    
    if (isTerritory())
    {
      Boolean hasTerritoryAccess = ta.hasTerritoryAccess(mplayer);
      if (hasTerritoryAccess != null)
      {
        if ((verboose) && (!hasTerritoryAccess.booleanValue()))
        {
          mplayer.message(createDeniedMessage(mplayer, hostFaction));
        }
        return hasTerritoryAccess.booleanValue();
      }
    }
    
    return has(mplayer, hostFaction, verboose);
  }
  




  public static String getStateHeaders()
  {
    String ret = "";
    for (Rel rel : Rel.values())
    {
      ret = ret + rel.getColor().toString();
      ret = ret + rel.toString().substring(0, 3);
      ret = ret + " ";
    }
    
    return ret;
  }
  
  public String getStateInfo(Set<Rel> value, boolean withDesc)
  {
    String ret = "";
    
    for (Rel rel : Rel.values())
    {
      if (value.contains(rel))
      {
        ret = ret + "<g>YES";
      }
      else
      {
        ret = ret + "<b>NOO";
      }
      ret = ret + " ";
    }
    
    String color = "<aqua>";
    if (!isVisible())
    {
      color = "<silver>";
    }
    else if (isEditable())
    {
      color = "<pink>";
    }
    
    ret = ret + color;
    ret = ret + getName();
    
    ret = Txt.parse(ret);
    
    if (withDesc) { ret = ret + " <i>" + getDesc();
    }
    return ret;
  }
}
