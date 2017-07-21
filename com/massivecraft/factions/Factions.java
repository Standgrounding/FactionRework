package com.massivecraft.factions;

import com.massivecraft.factions.adapter.BoardAdapter;
import com.massivecraft.factions.adapter.BoardMapAdapter;
import com.massivecraft.factions.adapter.RelAdapter;
import com.massivecraft.factions.adapter.TerritoryAccessAdapter;
import com.massivecraft.factions.chat.ChatActive;
import com.massivecraft.factions.cmd.type.TypeFactionChunkChangeType;
import com.massivecraft.factions.cmd.type.TypeRel;
import com.massivecraft.factions.engine.EngineEcon;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConfColl;
import com.massivecraft.factions.entity.MFlagColl;
import com.massivecraft.factions.entity.MPermColl;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.mixin.PowerMixin;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.type.RegistryType;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.xlib.gson.GsonBuilder;
import java.util.List;
import org.bukkit.ChatColor;






public class Factions
  extends MassivePlugin
{
  public static final String FACTION_MONEY_ACCOUNT_ID_PREFIX = "faction-";
  public static final String ID_NONE = "none";
  public static final String ID_SAFEZONE = "safezone";
  public static final String ID_WARZONE = "warzone";
  public static final String NAME_NONE_DEFAULT = ChatColor.DARK_GREEN.toString() + "Wilderness";
  

  public static final String NAME_SAFEZONE_DEFAULT = "SafeZone";
  

  public static final String NAME_WARZONE_DEFAULT = "WarZone";
  private static Factions i;
  
  public static Factions get() { return i; }
  public Factions() { i = this; }
  




  @Deprecated
  public PowerMixin getPowerMixin() { return PowerMixin.get(); } @Deprecated
  public void setPowerMixin(PowerMixin powerMixin) { PowerMixin.get().setInstance(powerMixin); }
  






  public void onEnableInner()
  {
    RegistryType.register(Rel.class, TypeRel.get());
    RegistryType.register(EventFactionsChunkChangeType.class, TypeFactionChunkChangeType.get());
    


    MUtil.registerExtractor(String.class, "accountId", ExtractorFactionAccountId.get());
    

    activateAuto();
    activate(new Object[] { getClassesActive("chat", ChatActive.class, new Predicate[0]) });
  }
  










  public List<Class<?>> getClassesActiveColls()
  {
    return new MassiveList(new Class[] { MConfColl.class, MFlagColl.class, MPermColl.class, FactionColl.class, MPlayerColl.class, BoardColl.class });
  }
  








  public List<Class<?>> getClassesActiveEngines()
  {
    List<Class<?>> ret = super.getClassesActiveEngines();
    
    ret.remove(EngineEcon.class);
    ret.add(EngineEcon.class);
    
    return ret;
  }
  

  public GsonBuilder getGsonBuilder()
  {
    return 
    


      super.getGsonBuilder().registerTypeAdapter(TerritoryAccess.class, TerritoryAccessAdapter.get()).registerTypeAdapter(Board.class, BoardAdapter.get()).registerTypeAdapter(Board.MAP_TYPE, BoardMapAdapter.get()).registerTypeAdapter(Rel.class, RelAdapter.get());
  }
}
