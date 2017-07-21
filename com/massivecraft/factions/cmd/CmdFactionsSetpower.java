package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import com.massivecraft.factions.event.EventFactionsPowerChange.PowerChangeReason;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;




public class CmdFactionsSetpower
  extends FactionsCommand
{
  public CmdFactionsSetpower()
  {
    addAliases(new String[] { "sp", "setpower" });
    

    addParameter(TypeMPlayer.get(), "player");
    addParameter(TypeDouble.get(), "power");
    

    addRequirements(new Requirement[] { RequirementHasPerm.get(Perm.SETPOWER) });
  }
  





  public void perform()
    throws MassiveException
  {
    MPlayer mplayer = (MPlayer)readArg();
    double power = ((Double)readArg()).doubleValue();
    

    double oldPower = mplayer.getPower();
    double newPower = mplayer.getLimitedPower(power);
    

    double difference = Math.abs(newPower - oldPower);
    double maxDifference = 0.1D;
    if (difference < maxDifference)
    {
      msg("%s's <i>power is already <h>%.2f<i>.", new Object[] { mplayer.getDisplayName(msender), Double.valueOf(newPower) });
      return;
    }
    

    EventFactionsPowerChange event = new EventFactionsPowerChange(sender, mplayer, EventFactionsPowerChange.PowerChangeReason.COMMAND, newPower);
    event.run();
    if (event.isCancelled()) { return;
    }
    
    msg("<i>You changed %s's <i>power from <h>%.2f <i>to <h>%.2f<i>.", new Object[] { mplayer.getDisplayName(msender), Double.valueOf(oldPower), Double.valueOf(newPower) });
    if (msender != mplayer)
    {
      mplayer.msg("%s <i>changed your power from <h>%.2f <i>to <h>%.2f<i>.", new Object[] { msender.getDisplayName(mplayer), Double.valueOf(oldPower), Double.valueOf(newPower) });
    }
    

    mplayer.setPower(Double.valueOf(newPower));
  }
}
