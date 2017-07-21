package com.massivecraft.factions.chat.modifier;

import com.massivecraft.factions.chat.ChatModifier;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;




public class ChatModifierParse
  extends ChatModifier
{
  private ChatModifierParse() { super("parse"); }
  private static ChatModifierParse i = new ChatModifierParse();
  public static ChatModifierParse get() { return i; }
  





  public String getModified(String subject, CommandSender sender, CommandSender recipient)
  {
    return Txt.parse(subject);
  }
}
