package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.util.Txt;



public class CmdFactionsSeeChunk
  extends FactionsCommand
{
  public CmdFactionsSeeChunk()
  {
    addAliases(new String[] { "sc" });
    

    addParameter(TypeBooleanOn.get(), "active", "toggle");
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  





  public void perform()
    throws MassiveException
  {
    boolean old = msender.isSeeingChunk();
    boolean targetDefault = !old;
    boolean target = ((Boolean)readArg(Boolean.valueOf(targetDefault))).booleanValue();
    String targetDesc = Txt.parse(target ? "<g>ON" : "<b>OFF");
    

    if (target == old)
    {
      msg("<i>See Chunk is already %s<i>.", new Object[] { targetDesc });
      return;
    }
    

    msender.setSeeingChunk(target);
    

    msg("<i>See Chunk is now %s<i>.", new Object[] { targetDesc });
  }
}
