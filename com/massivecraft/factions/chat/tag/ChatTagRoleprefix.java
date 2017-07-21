package com.massivecraft.factions.chat.tag;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;



public class ChatTagRoleprefix
  extends ChatTag
{
  private ChatTagRoleprefix() { super("factions_roleprefix"); }
  private static ChatTagRoleprefix i = new ChatTagRoleprefix();
  public static ChatTagRoleprefix get() { return i; }
  






  public String getReplacement(CommandSender sender, CommandSender recipient)
  {
    MPlayer usender = MPlayer.get(sender);
    

    Faction faction = usender.getFaction();
    if (faction.isNone()) { return "";
    }
    return usender.getRole().getPrefix();
  }
}
