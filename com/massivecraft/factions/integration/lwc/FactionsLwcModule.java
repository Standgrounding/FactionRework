package com.massivecraft.factions.integration.lwc;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import com.griefcraft.scripting.JavaModule;
import com.griefcraft.scripting.Module.Result;
import com.griefcraft.scripting.event.LWCProtectionInteractEvent;
import com.griefcraft.scripting.event.LWCProtectionRegisterEvent;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.engine.EnginePermBuild;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.SoundEffect;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.SmokeUtil;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;










public class FactionsLwcModule
  extends JavaModule
{
  private Factions plugin;
  private LWC lwc;
  
  public FactionsLwcModule(Factions plugin)
  {
    this.plugin = plugin;
  }
  







  public void onRegisterProtection(LWCProtectionRegisterEvent event)
  {
    if (!getlwcMustHaveBuildRightsToCreate) { return;
    }
    

    if (EnginePermBuild.canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getBlock()), true)) { return;
    }
    
    event.setCancelled(true);
  }
  


  public void onProtectionInteract(LWCProtectionInteractEvent event)
  {
    if (!getlwcRemoveIfNoBuildRights) { return;
    }
    
    Protection protection = event.getProtection();
    Block block = protection.getBlock();
    PS ps = PS.valueOf(block);
    
    String ownerName = protection.getOwner();
    String ownerId = IdUtil.getId(ownerName);
    MPlayer mowner = MPlayer.get(ownerId);
    if (mowner == null) { return;
    }
    


    if (EnginePermBuild.canPlayerBuildAt(mowner, ps, false)) { return;
    }
    
    protection.remove();
    




    event.setResult(Module.Result.CANCEL);
    

    Location location = block.getLocation();
    SmokeUtil.spawnCloudSimple(location);
    
    SoundEffect.valueOf("DOOR_OPEN", 1.0F, 1.0F).run(location);
    

    Player player = event.getPlayer();
    String message = Txt.parse("<i>Factions removed <h>%s's <i>LWC. They lacked build rights.", new Object[] { mowner.getDisplayName(player) });
    MixinMessage.get().messageOne(player, message);
  }
}
