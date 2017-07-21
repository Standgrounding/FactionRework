package com.massivecraft.factions.util;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;







public class RelationUtil
{
  private static final String UNKNOWN_RELATION_OTHER = "A server admin";
  private static final String UNDEFINED_FACTION_OTHER = "ERROR";
  private static final String OWN_FACTION = "your faction";
  private static final String SELF = "you";
  
  public RelationUtil() {}
  
  public static String describeThatToMe(RelationParticipator that, RelationParticipator me, boolean ucfirst)
  {
    String ret = "";
    
    if (that == null) { return "A server admin";
    }
    Faction thatFaction = getFaction(that);
    if (thatFaction == null) { return "ERROR";
    }
    Faction myFaction = getFaction(me);
    
    boolean isSameFaction = thatFaction == myFaction;
    
    if ((that instanceof Faction))
    {
      String thatFactionName = thatFaction.getName();
      if (thatFaction.isNone())
      {
        ret = thatFactionName;
      }
      else if (((me instanceof MPlayer)) && (isSameFaction))
      {
        ret = "your faction";
      }
      else
      {
        ret = thatFactionName;
      }
    }
    else if ((that instanceof MPlayer))
    {
      MPlayer mplayerthat = (MPlayer)that;
      if (that == me)
      {
        ret = "you";
      }
      else if (isSameFaction)
      {
        ret = mplayerthat.getNameAndTitle(myFaction);
      }
      else
      {
        ret = mplayerthat.getNameAndFactionName();
      }
    }
    
    if (ucfirst) { ret = Txt.upperCaseFirst(ret);
    }
    return getColorOfThatToMe(that, me).toString() + ret;
  }
  
  public static String describeThatToMe(RelationParticipator that, RelationParticipator me)
  {
    return describeThatToMe(that, me, false);
  }
  




  public static Rel getRelationOfThatToMe(RelationParticipator that, RelationParticipator me)
  {
    return getRelationOfThatToMe(that, me, false);
  }
  
  public static Rel getRelationOfThatToMe(RelationParticipator that, RelationParticipator me, boolean ignorePeaceful)
  {
    Faction myFaction = getFaction(me);
    if (myFaction == null) { return Rel.NEUTRAL;
    }
    Faction thatFaction = getFaction(that);
    if (thatFaction == null) { return Rel.NEUTRAL;
    }
    if (myFaction.equals(thatFaction))
    {
      if ((that instanceof MPlayer)) return ((MPlayer)that).getRole();
      return Rel.MEMBER;
    }
    
    MFlag flagPeaceful = MFlag.getFlagPeaceful();
    if ((!ignorePeaceful) && ((thatFaction.getFlag(flagPeaceful)) || (myFaction.getFlag(flagPeaceful)))) { return Rel.TRUCE;
    }
    
    Rel theirWish = thatFaction.getRelationWish(myFaction);
    Rel myWish = myFaction.getRelationWish(thatFaction);
    return theirWish.isLessThan(myWish) ? theirWish : myWish;
  }
  




  public static Faction getFaction(RelationParticipator rp)
  {
    if ((rp instanceof Faction)) { return (Faction)rp;
    }
    if ((rp instanceof MPlayer)) { return ((MPlayer)rp).getFaction();
    }
    
    return null;
  }
  




  public static ChatColor getColorOfThatToMe(RelationParticipator that, RelationParticipator me)
  {
    Faction thatFaction = getFaction(that);
    if ((thatFaction != null) && (thatFaction != getFaction(me)))
    {
      if (thatFaction.getFlag(MFlag.getFlagFriendlyire())) { return getcolorFriendlyFire;
      }
      if (!thatFaction.getFlag(MFlag.getFlagPvp())) return getcolorNoPVP;
    }
    return getRelationOfThatToMe(that, me).getColor();
  }
}
