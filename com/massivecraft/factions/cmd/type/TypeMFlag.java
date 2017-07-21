package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MFlagColl;
import com.massivecraft.massivecore.command.type.store.TypeEntity;
import java.util.Collection;





public class TypeMFlag
  extends TypeEntity<MFlag>
{
  private static TypeMFlag i = new TypeMFlag();
  public static TypeMFlag get() { return i; }
  
  public TypeMFlag() {
    super(MFlagColl.get());
  }
  





  public String getName()
  {
    return "faction flag";
  }
  

  public Collection<MFlag> getAll()
  {
    return MFlag.getAll();
  }
}
