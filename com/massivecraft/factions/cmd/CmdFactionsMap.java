package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanYes;
import com.massivecraft.massivecore.ps.PS;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;




public class CmdFactionsMap
  extends FactionsCommand
{
  public CmdFactionsMap()
  {
    addParameter(TypeBooleanYes.get(), "on/off", "once");
    

    addRequirements(new Requirement[] { RequirementIsPlayer.get() });
  }
  




  public void perform()
    throws MassiveException
  {
    if (!argIsSet())
    {
      showMap(48, 17);
      return;
    }
    
    if (((Boolean)readArg(Boolean.valueOf(!msender.isMapAutoUpdating()))).booleanValue())
    {

      showMap(48, 8);
      

      msender.setMapAutoUpdating(Boolean.valueOf(true));
      msg("<i>Map auto update <green>ENABLED.");

    }
    else
    {
      msender.setMapAutoUpdating(Boolean.valueOf(false));
      msg("<i>Map auto update <red>DISABLED.");
    }
  }
  
  public void showMap(int width, int height)
  {
    Location location = me.getLocation();
    List<Object> message = BoardColl.get().getMap(msenderFaction, PS.valueOf(location), location.getYaw(), width, height);
    message(message);
  }
}
