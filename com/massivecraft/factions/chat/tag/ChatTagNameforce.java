package com.massivecraft.factions.chat.tag;

import com.massivecraft.factions.chat.ChatTag;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.command.CommandSender;




public class ChatTagNameforce
  extends ChatTag
{
  private ChatTagNameforce() { super("factions_nameforce"); }
  private static ChatTagNameforce i = new ChatTagNameforce();
  public static ChatTagNameforce get() { return i; }
  






  public String getReplacement(CommandSender sender, CommandSender recipient)
  {
    MPlayer usender = MPlayer.get(sender);
    
    Faction faction = usender.getFaction();
    return faction.getName();
  }
}
