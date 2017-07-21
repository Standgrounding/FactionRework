package com.massivecraft.factions.chat.tag;

import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;




public class ChatTagName
  extends ChatTag
{
  private ChatTagName() { super("factions_name"); }
  private static ChatTagName i = new ChatTagName();
  public static ChatTagName get() { return i; }
  






  public String getReplacement(CommandSender sender, CommandSender recipient)
  {
    MPlayer usender = MPlayer.get(sender);
    

    Faction faction = usender.getFaction();
    if (faction.isNone()) { return "";
    }
    return faction.getName();
  }
}
