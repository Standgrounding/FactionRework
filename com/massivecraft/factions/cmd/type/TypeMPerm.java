package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPermColl;
import com.massivecraft.massivecore.command.type.store.TypeEntity;
import java.util.Collection;





public class TypeMPerm
  extends TypeEntity<MPerm>
{
  private static TypeMPerm i = new TypeMPerm();
  public static TypeMPerm get() { return i; }
  
  public TypeMPerm() {
    super(MPermColl.get());
  }
  





  public String getName()
  {
    return "faction permission";
  }
  

  public Collection<MPerm> getAll()
  {
    return MPerm.getAll();
  }
}
