package com.massivecraft.factions.cmd;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.ps.PS;
import java.util.Set;




public abstract class CmdFactionsSetX
  extends FactionsCommand
{
  private String formatOne = null;
  public String getFormatOne() { return formatOne; }
  public void setFormatOne(String formatOne) { this.formatOne = formatOne; }
  
  private String formatMany = null;
  public String getFormatMany() { return formatMany; }
  public void setFormatMany(String formatMany) { this.formatMany = formatMany; }
  
  private boolean claim = true;
  public boolean isClaim() { return claim; }
  public void setClaim(boolean claim) { this.claim = claim; }
  
  private int factionArgIndex = 0;
  public int getFactionArgIndex() { return factionArgIndex; }
  public void setFactionArgIndex(int factionArgIndex) { this.factionArgIndex = factionArgIndex; }
  




  public CmdFactionsSetX(boolean claim)
  {
    setClaim(claim);
    setSetupEnabled(false);
  }
  





  public void perform()
    throws MassiveException
  {
    Faction newFaction = getNewFaction();
    Set<PS> chunks = getChunks();
    

    msender.tryClaim(newFaction, chunks, getFormatOne(), getFormatMany());
  }
  



  public abstract Set<PS> getChunks()
    throws MassiveException;
  



  public Faction getNewFaction()
    throws MassiveException
  {
    if (isClaim())
    {
      return (Faction)readArgAt(getFactionArgIndex(), msenderFaction);
    }
    

    return FactionColl.get().getNone();
  }
}
