package com.massivecraft.factions.cmd;


public class CmdFactionsAccessView
  extends CmdFactionsAccessAbstract
{
  public CmdFactionsAccessView() {}
  

  public void innerPerform()
  {
    sendAccessInfo();
  }
}
