package com.massivecraft.factions.chat.tag;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class ChatTagRelcolor
  extends ChatTag
{
  private ChatTagRelcolor() { super("factions_relcolor"); }
  private static ChatTagRelcolor i = new ChatTagRelcolor();
  public static ChatTagRelcolor get() { return i; }
  






  public String getReplacement(CommandSender sender, CommandSender recipient)
  {
    if (recipient == null) { return null;
    }
    
    MPlayer usender = MPlayer.get(sender);
    MPlayer urecipient = MPlayer.get(recipient);
    
    return urecipient.getRelationTo(usender).getColor().toString();
  }
}
