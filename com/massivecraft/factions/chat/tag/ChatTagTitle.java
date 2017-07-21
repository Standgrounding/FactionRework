package com.massivecraft.factions.chat.tag;

import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;




public class ChatTagTitle
  extends ChatTag
{
  private ChatTagTitle() { super("factions_title"); }
  private static ChatTagTitle i = new ChatTagTitle();
  public static ChatTagTitle get() { return i; }
  






  public String getReplacement(CommandSender sender, CommandSender recipient)
  {
    MPlayer usender = MPlayer.get(sender);
    
    if (!usender.hasTitle()) return "";
    return usender.getTitle();
  }
}
