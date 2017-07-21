package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.Parameter;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.pager.Stringifier;
import java.util.List;






public class CmdFactionsFlagShow
  extends FactionsCommand
{
  public CmdFactionsFlagShow()
  {
    addParameter(TypeFaction.get(), "faction", "you");
    addParameter(Parameter.getPage());
  }
  





  public void perform()
    throws MassiveException
  {
    final Faction faction = (Faction)readArg(msenderFaction);
    int page = ((Integer)readArg()).intValue();
    

    String title = "Flags for " + faction.describeTo(msender);
    Pager<MFlag> pager = new Pager(this, title, Integer.valueOf(page), MFlag.getAll(), new Stringifier()
    {

      public String toString(MFlag mflag, int index)
      {
        return mflag.getStateDesc(faction.getFlag(mflag), true, true, true, true, true);

      }
      


    });
    List<String> pagerArgs = new MassiveList(new String[] {faction.getId(), String.valueOf(page) });
    
    pager.setArgs(pagerArgs);
    

    pager.messageAsync();
  }
}
