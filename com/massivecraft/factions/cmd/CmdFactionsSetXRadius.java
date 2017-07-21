package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;





public abstract class CmdFactionsSetXRadius
  extends CmdFactionsSetX
{
  public CmdFactionsSetXRadius(boolean claim)
  {
    super(claim);
    

    addParameter(Integer.valueOf(1), TypeInteger.get(), "radius");
    if (claim)
    {
      addParameter(TypeFaction.get(), "faction", "you");
      setFactionArgIndex(1);
    }
  }
  



  public Integer getRadius()
    throws MassiveException
  {
    int radius = ((Integer)readArgAt(0)).intValue();
    

    if (radius < 1)
    {
      throw new MassiveException().setMsg("<b>If you specify a radius, it must be at least 1.");
    }
    

    if ((radius > getsetRadiusMax) && (!msender.isOverriding()))
    {
      throw new MassiveException().setMsg("<b>The maximum radius allowed is <h>%s<b>.", new Object[] { Integer.valueOf(getsetRadiusMax) });
    }
    
    return Integer.valueOf(radius);
  }
  
  public Integer getRadiusZero() throws MassiveException
  {
    Integer ret = getRadius();
    return Integer.valueOf(ret.intValue() - 1);
  }
}
