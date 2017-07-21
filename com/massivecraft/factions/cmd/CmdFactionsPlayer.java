package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsRemovePlayerMillis;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.Progressbar;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;





public class CmdFactionsPlayer
  extends FactionsCommand
{
  public CmdFactionsPlayer()
  {
    addParameter(TypeMPlayer.get(), "player", "you");
  }
  





  public void perform()
    throws MassiveException
  {
    MPlayer mplayer = (MPlayer)readArg(msender);
    

    message(Txt.titleize("Player " + mplayer.describeTo(msender)));
    

    double progressbarQuota = 0.0D;
    double playerPowerMax = mplayer.getPowerMax();
    if (playerPowerMax != 0.0D)
    {
      progressbarQuota = mplayer.getPower() / playerPowerMax;
    }
    
    int progressbarWidth = (int)Math.round(mplayer.getPowerMax() / mplayer.getPowerMaxUniversal() * 100.0D);
    msg("<a>Power: <v>%s", new Object[] { Progressbar.HEALTHBAR_CLASSIC.withQuota(progressbarQuota).withWidth(progressbarWidth).render() });
    

    msg("<a>Power: <v>%.2f / %.2f", new Object[] { Double.valueOf(mplayer.getPower()), Double.valueOf(mplayer.getPowerMax()) });
    

    if (mplayer.hasPowerBoost())
    {
      double powerBoost = mplayer.getPowerBoost();
      String powerBoostType = powerBoost > 0.0D ? "bonus" : "penalty";
      msg("<a>Power Boost: <v>%f <i>(a manually granted %s)", new Object[] { Double.valueOf(powerBoost), powerBoostType });
    }
    



    String stringTillMax = "";
    double powerTillMax = mplayer.getPowerMax() - mplayer.getPower();
    if (powerTillMax > 0.0D)
    {
      long millisTillMax = (powerTillMax * 3600000.0D / mplayer.getPowerPerHour());
      LinkedHashMap<TimeUnit, Long> unitcountsTillMax = TimeDiffUtil.unitcounts(millisTillMax, TimeUnit.getAllButMillis());
      unitcountsTillMax = TimeDiffUtil.limit(unitcountsTillMax, 2);
      String unitcountsTillMaxFormated = TimeDiffUtil.formatedVerboose(unitcountsTillMax, "<i>");
      stringTillMax = Txt.parse(" <i>(%s <i>left till max)", new Object[] { unitcountsTillMaxFormated });
    }
    
    msg("<a>Power per Hour: <v>%.2f%s", new Object[] { Double.valueOf(mplayer.getPowerPerHour()), stringTillMax });
    

    msg("<a>Power per Death: <v>%.2f", new Object[] { Double.valueOf(mplayer.getPowerPerDeath()) });
    

    if (getremovePlayerMillisDefault <= 0L) { return;
    }
    EventFactionsRemovePlayerMillis event = new EventFactionsRemovePlayerMillis(false, mplayer);
    event.run();
    msg("<i>Automatic removal after %s <i>of inactivity:", new Object[] { format(event.getMillis()) });
    for (Map.Entry<String, Long> causeMillis : event.getCauseMillis().entrySet())
    {
      String cause = (String)causeMillis.getKey();
      long millis = ((Long)causeMillis.getValue()).longValue();
      msg("<a>%s<a>: <v>%s", new Object[] { cause, format(millis) });
    }
  }
  




  public static String format(long millis)
  {
    LinkedHashMap<TimeUnit, Long> unitcounts = TimeDiffUtil.unitcounts(millis, TimeUnit.getAllBut(new TimeUnit[] { TimeUnit.MILLISECOND, TimeUnit.WEEK, TimeUnit.MONTH }));
    return TimeDiffUtil.formatedVerboose(unitcounts);
  }
}
