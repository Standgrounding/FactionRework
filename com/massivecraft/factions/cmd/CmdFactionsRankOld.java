package com.massivecraft.factions.cmd;

import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.cmd.type.TypeMPlayer;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.util.MUtil;









public class CmdFactionsRankOld
  extends FactionsCommand
{
  public final String rankName;
  
  public CmdFactionsRankOld(String rank)
  {
    rankName = rank.toLowerCase();
    setSetupEnabled(false);
    

    addAliases(new String[] { rankName });
    

    addParameter(TypeMPlayer.get(), "player");
    addParameter(TypeFaction.get(), "faction", "their");
    

    setVisibility(Visibility.INVISIBLE);
  }
  





  public void perform()
  {
    getcmdFactionsRank.execute(sender, MUtil.list(new String[] {
      argAt(0), rankName, 
      
      argAt(1) }));
  }
}
