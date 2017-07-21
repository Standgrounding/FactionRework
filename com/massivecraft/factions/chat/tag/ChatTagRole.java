package com.massivecraft.factions.chat.tag;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;



public class ChatTagRole
  extends ChatTag
{
  private ChatTagRole() { super("factions_role"); }
  private static ChatTagRole i = new ChatTagRole();
  public static ChatTagRole get() { return i; }
  






  public String getReplacement(CommandSender sender, CommandSender recipient)
  {
    MPlayer usender = MPlayer.get(sender);
    
    return Txt.upperCaseFirst(usender.getRole().toString().toLowerCase());
  }
}
