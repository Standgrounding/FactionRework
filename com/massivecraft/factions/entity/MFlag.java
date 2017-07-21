package com.massivecraft.factions.entity;

import com.massivecraft.factions.event.EventFactionsCreateFlags;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.Prioritized;
import com.massivecraft.massivecore.Registerable;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.predicate.PredicateIsRegistered;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.Txt;
import java.util.List;
import org.bukkit.ChatColor;









public class MFlag
  extends Entity<MFlag>
  implements Prioritized, Registerable, Named
{
  public static final transient String ID_OPEN = "open";
  public static final transient String ID_MONSTERS = "monsters";
  public static final transient String ID_ANIMALS = "animals";
  public static final transient String ID_POWERLOSS = "powerloss";
  public static final transient String ID_POWERGAIN = "powergain";
  public static final transient String ID_PVP = "pvp";
  public static final transient String ID_FRIENDLYFIRE = "friendlyfire";
  public static final transient String ID_EXPLOSIONS = "explosions";
  public static final transient String ID_OFFLINEEXPLOSIONS = "offlineexplosions";
  public static final transient String ID_FIRESPREAD = "firespread";
  public static final transient String ID_ENDERGRIEF = "endergrief";
  public static final transient String ID_ZOMBIEGRIEF = "zombiegrief";
  public static final transient String ID_PERMANENT = "permanent";
  public static final transient String ID_PEACEFUL = "peaceful";
  public static final transient String ID_INFPOWER = "infpower";
  public static final transient int PRIORITY_OPEN = 1000;
  public static final transient int PRIORITY_MONSTERS = 2000;
  public static final transient int PRIORITY_ANIMALS = 3000;
  public static final transient int PRIORITY_POWERLOSS = 4000;
  public static final transient int PRIORITY_POWERGAIN = 5000;
  public static final transient int PRIORITY_PVP = 6000;
  public static final transient int PRIORITY_FRIENDLYFIRE = 7000;
  public static final transient int PRIORITY_EXPLOSIONS = 8000;
  public static final transient int PRIORITY_OFFLINEEXPLOSIONS = 9000;
  public static final transient int PRIORITY_FIRESPREAD = 10000;
  public static final transient int PRIORITY_ENDERGRIEF = 11000;
  public static final transient int PRIORITY_ZOMBIEGRIEF = 12000;
  public static final transient int PRIORITY_PERMANENT = 13000;
  public static final transient int PRIORITY_PEACEFUL = 14000;
  public static final transient int PRIORITY_INFPOWER = 15000;
  
  public static MFlag get(Object oid)
  {
    return (MFlag)MFlagColl.get().get(oid);
  }
  
  public static List<MFlag> getAll()
  {
    return getAll(false);
  }
  
  public static List<MFlag> getAll(boolean isAsync)
  {
    setupStandardFlags();
    new EventFactionsCreateFlags(isAsync).run();
    return MFlagColl.get().getAll(PredicateIsRegistered.get(), ComparatorSmart.get());
  }
  
  public static void setupStandardFlags()
  {
    getFlagOpen();
    getFlagMonsters();
    getFlagAnimals();
    getFlagPowerloss();
    getFlagPowergain();
    getFlagPvp();
    getFlagFriendlyire();
    getFlagExplosions();
    getFlagOfflineexplosions();
    getFlagFirespread();
    getFlagEndergrief();
    getFlagZombiegrief();
    getFlagPermanent();
    getFlagPeaceful();
    getFlagInfpower();
  }
  
  public static MFlag getFlagOpen() { return getCreative(1000, "open", "open", "Can the faction be joined without an invite?", "Anyone can join. No invite required.", "An invite is required to join.", false, true, true); }
  public static MFlag getFlagMonsters() { return getCreative(2000, "monsters", "monsters", "Can monsters spawn in this territory?", "Monsters can spawn in this territory.", "Monsters can NOT spawn in this territory.", false, true, true); }
  public static MFlag getFlagAnimals() { return getCreative(3000, "animals", "animals", "Can animals spawn in this territory?", "Animals can spawn in this territory.", "Animals can NOT spawn in this territory.", true, true, true); }
  public static MFlag getFlagPowerloss() { return getCreative(4000, "powerloss", "powerloss", "Is power lost on death in this territory?", "Power is lost on death in this territory.", "Power is NOT lost on death in this territory.", true, false, true); }
  public static MFlag getFlagPowergain() { return getCreative(5000, "powergain", "powergain", "Can power be gained in this territory?", "Power can be gained in this territory.", "Power is NOT gained in this territory.", true, false, true); }
  public static MFlag getFlagPvp() { return getCreative(6000, "pvp", "pvp", "Can you PVP in territory?", "You can PVP in this territory.", "You can NOT PVP in this territory.", true, false, true); }
  public static MFlag getFlagFriendlyire() { return getCreative(7000, "friendlyfire", "friendlyfire", "Can friends hurt eachother in this territory?", "Friendly fire is on here.", "Friendly fire is off here.", false, false, true); }
  public static MFlag getFlagExplosions() { return getCreative(8000, "explosions", "explosions", "Can explosions occur in this territory?", "Explosions can occur in this territory.", "Explosions can NOT occur in this territory.", true, false, true); }
  public static MFlag getFlagOfflineexplosions() { return getCreative(9000, "offlineexplosions", "offlineexplosions", "Can explosions occur if faction is offline?", "Explosions if faction is offline.", "No explosions if faction is offline.", false, false, true); }
  public static MFlag getFlagFirespread() { return getCreative(10000, "firespread", "firespread", "Can fire spread in territory?", "Fire can spread in this territory.", "Fire can NOT spread in this territory.", true, false, true); }
  public static MFlag getFlagEndergrief() { return getCreative(11000, "endergrief", "endergrief", "Can endermen grief in this territory?", "Endermen can grief in this territory.", "Endermen can NOT grief in this territory.", false, false, true); }
  public static MFlag getFlagZombiegrief() { return getCreative(12000, "zombiegrief", "zombiegrief", "Can zombies break doors in this territory?", "Zombies can break doors in this territory.", "Zombies can NOT break doors in this territory.", false, false, true); }
  public static MFlag getFlagPermanent() { return getCreative(13000, "permanent", "permanent", "Is the faction immune to deletion?", "The faction can NOT be deleted.", "The faction can be deleted.", false, false, true); }
  public static MFlag getFlagPeaceful() { return getCreative(14000, "peaceful", "peaceful", "Is the faction in truce with everyone?", "The faction is in truce with everyone.", "The faction relations work as usual.", false, false, true); }
  public static MFlag getFlagInfpower() { return getCreative(15000, "infpower", "infpower", "Does the faction have infinite power?", "The faction has infinite power.", "The faction power works as usual.", false, false, true); }
  
  public static MFlag getCreative(int priority, String id, String name, String desc, String descYes, String descNo, boolean standard, boolean editable, boolean visible)
  {
    MFlag ret = (MFlag)MFlagColl.get().get(id, false);
    if (ret != null)
    {
      ret.setRegistered(true);
      return ret;
    }
    
    ret = new MFlag(priority, name, desc, descYes, descNo, standard, editable, visible);
    MFlagColl.get().attach(ret, id);
    ret.setRegistered(true);
    ret.sync();
    
    return ret;
  }
  





  public MFlag load(MFlag that)
  {
    priority = priority;
    name = name;
    desc = desc;
    descYes = descYes;
    descNo = descNo;
    standard = standard;
    editable = editable;
    visible = visible;
    
    return this;
  }
  




  private transient boolean registered = false;
  public boolean isRegistered() { return registered; }
  public void setRegistered(boolean registered) { this.registered = registered; }
  








  private int priority = 0;
  public int getPriority() { return priority; }
  public MFlag setPriority(int priority) { this.priority = priority;changed();return this;
  }
  



  private String name = "defaultName";
  public String getName() { return name; }
  public MFlag setName(String name) { this.name = name;changed();return this;
  }
  

  private String desc = "defaultDesc";
  public String getDesc() { return desc; }
  public MFlag setDesc(String desc) { this.desc = desc;changed();return this;
  }
  

  private String descYes = "defaultDescYes";
  public String getDescYes() { return descYes; }
  public MFlag setDescYes(String descYes) { this.descYes = descYes;changed();return this;
  }
  

  private String descNo = "defaultDescNo";
  public String getDescNo() { return descNo; }
  public MFlag setDescNo(String descNo) { this.descNo = descNo;changed();return this;
  }
  


  private boolean standard = true;
  public boolean isStandard() { return standard; }
  public MFlag setStandard(boolean standard) { this.standard = standard;changed();return this;
  }
  



  private boolean editable = false;
  public boolean isEditable() { return editable; }
  public MFlag setEditable(boolean editable) { this.editable = editable;changed();return this;
  }
  







  private boolean visible = true;
  public boolean isVisible() { return visible; }
  public MFlag setVisible(boolean visible) { this.visible = visible;changed();return this;
  }
  



  public MFlag() {}
  



  public MFlag(int priority, String name, String desc, String descYes, String descNo, boolean standard, boolean editable, boolean visible)
  {
    this.priority = priority;
    this.name = name;
    this.desc = desc;
    this.descYes = descYes;
    this.descNo = descNo;
    this.standard = standard;
    this.editable = editable;
    this.visible = visible;
  }
  




  public boolean isInteresting(boolean value)
  {
    if (!isVisible()) return false;
    if (isEditable()) return true;
    return isStandard() != value;
  }
  

  public String getStateDesc(boolean value, boolean withValue, boolean monospaceValue, boolean withName, boolean withDesc, boolean specificDesc)
  {
    List<String> ret = new MassiveList();
    

    if (withValue) ret.add(getStateValue(value, monospaceValue));
    if (withName) ret.add(getStateName());
    if (withDesc) { ret.add(getStateDescription(value, specificDesc));
    }
    
    return Txt.implode(ret, " ");
  }
  
  private static String getStateValue(boolean value, boolean monoSpace)
  {
    String yes = "<g>YES";
    String no = monoSpace ? "<b>NOO" : "<b>NO";
    
    return Txt.parse(value ? yes : no);
  }
  
  private String getStateName()
  {
    return getStateColor().toString() + getName();
  }
  

  private ChatColor getStateColor()
  {
    if (!isVisible()) return ChatColor.GRAY;
    if (isEditable()) { return ChatColor.LIGHT_PURPLE;
    }
    
    return ChatColor.AQUA;
  }
  

  private String getStateDescription(boolean value, boolean specific)
  {
    String desc = getDesc();
    

    if (specific) { desc = value ? getDescYes() : getDescNo();
    }
    
    return Txt.parse("<i>%s", new Object[] { desc });
  }
}
