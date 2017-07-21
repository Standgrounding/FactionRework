package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.chat.ChatFormatter;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerToRecipientChat;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;



public class EngineChat
  extends Engine
{
  private static EngineChat i = new EngineChat();
  public static EngineChat get() { return i; }
  
  public EngineChat() {
    setPlugin(Factions.get());
  }
  





  public void setActiveInner(boolean active)
  {
    if (!active) { return;
    }
    if (getchatSetFormat)
    {
      Bukkit.getPluginManager().registerEvent(AsyncPlayerChatEvent.class, this, getchatSetFormatAt, new SetFormatEventExecutor(null), Factions.get(), true);
    }
    
    if (getchatParseTags)
    {
      Bukkit.getPluginManager().registerEvent(AsyncPlayerChatEvent.class, this, getchatParseTagsAt, new ParseTagsEventExecutor(null), Factions.get(), true);
    }
    
    if (getchatParseTags)
    {
      Bukkit.getPluginManager().registerEvent(EventMassiveCorePlayerToRecipientChat.class, this, EventPriority.NORMAL, new ParseRelcolorEventExecutor(null), Factions.get(), true);
    }
  }
  

  private class SetFormatEventExecutor
    implements EventExecutor
  {
    private SetFormatEventExecutor() {}
    
    public void execute(Listener listener, Event event)
      throws EventException
    {
      try
      {
        if (!(event instanceof AsyncPlayerChatEvent)) return;
        EngineChat.setFormat((AsyncPlayerChatEvent)event);
      }
      catch (Throwable t)
      {
        throw new EventException(t);
      }
    }
  }
  
  public static void setFormat(AsyncPlayerChatEvent event)
  {
    event.setFormat(getchatSetFormatTo);
  }
  

  private class ParseTagsEventExecutor
    implements EventExecutor
  {
    private ParseTagsEventExecutor() {}
    
    public void execute(Listener listener, Event event)
      throws EventException
    {
      try
      {
        if (!(event instanceof AsyncPlayerChatEvent)) return;
        EngineChat.parseTags((AsyncPlayerChatEvent)event);
      }
      catch (Throwable t)
      {
        throw new EventException(t);
      }
    }
  }
  
  public static void parseTags(AsyncPlayerChatEvent event)
  {
    Player player = event.getPlayer();
    if (MUtil.isntPlayer(player)) { return;
    }
    String format = event.getFormat();
    format = ChatFormatter.format(format, player, null);
    event.setFormat(format);
  }
  

  private class ParseRelcolorEventExecutor
    implements EventExecutor
  {
    private ParseRelcolorEventExecutor() {}
    
    public void execute(Listener listener, Event event)
      throws EventException
    {
      try
      {
        if (!(event instanceof EventMassiveCorePlayerToRecipientChat)) return;
        EngineChat.parseRelcolor((EventMassiveCorePlayerToRecipientChat)event);
      }
      catch (Throwable t)
      {
        throw new EventException(t);
      }
    }
  }
  
  public static void parseRelcolor(EventMassiveCorePlayerToRecipientChat event)
  {
    String format = event.getFormat();
    format = ChatFormatter.format(format, event.getSender(), event.getRecipient());
    event.setFormat(format);
  }
}
