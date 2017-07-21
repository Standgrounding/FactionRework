package com.massivecraft.factions.integration.herochat;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.ChannelManager;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;
import com.massivecraft.factions.chat.ChatFormatter;
import com.massivecraft.factions.entity.MConf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class EngineHerochat extends com.massivecraft.massivecore.Engine
{
  public EngineHerochat() {}
  
  private static EngineHerochat i = new EngineHerochat();
  public static EngineHerochat get() { return i; }
  





  public void setActiveInner(boolean active)
  {
    if (!active) { return;
    }
    Herochat.getChannelManager().addChannel(new ChannelFactionsFaction());
    Herochat.getChannelManager().addChannel(new ChannelFactionsAllies());
  }
  





  @EventHandler(priority=EventPriority.NORMAL)
  public void onChannelChatEvent(ChannelChatEvent event)
  {
    if (!getchatParseTags) { return;
    }
    String format = event.getFormat();
    


    format = format.replace("{default}", event.getChannel().getFormatSupplier().getStandardFormat());
    
    format = ChatFormatter.format(format, event.getSender().getPlayer(), null);
    event.setFormat(format);
  }
}
