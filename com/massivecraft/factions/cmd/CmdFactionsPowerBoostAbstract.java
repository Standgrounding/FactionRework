package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.FactionsParticipator;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNullable;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.util.Txt;




public abstract class CmdFactionsPowerBoostAbstract
  extends FactionsCommand
{
  protected CmdFactionsPowerBoostAbstract(Type<? extends FactionsParticipator> parameterType, String parameterName)
  {
    addParameter(parameterType, parameterName);
    addParameter(TypeNullable.get(TypeDouble.get()), "amount", "show");
  }
  





  public void perform()
    throws MassiveException
  {
    FactionsParticipator factionsParticipator = (FactionsParticipator)readArg();
    Double powerBoost = (Double)readArg(Double.valueOf(factionsParticipator.getPowerBoost()));
    

    boolean updated = trySet(factionsParticipator, powerBoost);
    

    informPowerBoost(factionsParticipator, powerBoost, updated);
  }
  
  private boolean trySet(FactionsParticipator factionsParticipator, Double powerBoost)
    throws MassiveException
  {
    if (!argIsSet(1)) { return false;
    }
    
    if (!Perm.POWERBOOST_SET.has(sender, true)) { throw new MassiveException();
    }
    
    factionsParticipator.setPowerBoost(powerBoost);
    

    return true;
  }
  

  private void informPowerBoost(FactionsParticipator factionsParticipator, Double powerBoost, boolean updated)
  {
    String participatorDescribe = factionsParticipator.describeTo(msender, true);
    powerBoost = Double.valueOf(powerBoost == null ? factionsParticipator.getPowerBoost() : powerBoost.doubleValue());
    String powerDescription = Txt.parse(Double.compare(powerBoost.doubleValue(), 0.0D) >= 0 ? "<g>bonus" : "<b>penalty");
    String when = updated ? "now " : "";
    String verb = factionsParticipator.equals(msender) ? "have" : "has";
    

    String messagePlayer = Txt.parse("<i>%s<i> %s%s a power %s<i> of <h>%.2f<i> to min and max power levels.", new Object[] { participatorDescribe, when, verb, powerDescription, powerBoost });
    String messageLog = Txt.parse("%s %s set the power %s<i> for %s<i> to <h>%.2f<i>.", new Object[] { msender.getName(), verb, powerDescription, factionsParticipator.getName(), powerBoost });
    

    msender.message(messagePlayer);
    if (updated) Factions.get().log(new Object[] { messageLog });
  }
}
