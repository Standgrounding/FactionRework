package com.massivecraft.factions.chat.modifier;

import com.massivecraft.factions.chat.ChatModifier;
import org.bukkit.command.CommandSender;





public class ChatModifierLp
  extends ChatModifier
{
  private ChatModifierLp() { super("lp"); }
  private static ChatModifierLp i = new ChatModifierLp();
  public static ChatModifierLp get() { return i; }
  





  public String getModified(String subject, CommandSender sender, CommandSender recipient)
  {
    if (subject.equals("")) return subject;
    return " " + subject;
  }
}
