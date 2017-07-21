package com.massivecraft.factions.chat;

import com.massivecraft.massivecore.collections.MassiveMap;
import java.util.Map;
import org.bukkit.command.CommandSender;





public abstract class ChatTag
  extends ChatActive
{
  private static final Map<String, ChatTag> idToTag = new MassiveMap();
  public static ChatTag getTag(String tagId) { return (ChatTag)idToTag.get(tagId); }
  




  public ChatTag(String id)
  {
    super(id);
  }
  





  public boolean isActive()
  {
    return idToTag.containsKey(getId());
  }
  

  public void setActive(boolean active)
  {
    if (active)
    {
      idToTag.put(getId(), this);
    }
    else
    {
      idToTag.remove(getId());
    }
  }
  
  public abstract String getReplacement(CommandSender paramCommandSender1, CommandSender paramCommandSender2);
}
