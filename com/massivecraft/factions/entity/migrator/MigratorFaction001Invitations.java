package com.massivecraft.factions.entity.migrator;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.store.migrator.MigratorFieldConvert;
import com.massivecraft.massivecore.store.migrator.MigratorFieldRename;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;




public class MigratorFaction001Invitations
  extends MigratorRoot
{
  private static MigratorFaction001Invitations i = new MigratorFaction001Invitations();
  public static MigratorFaction001Invitations get() { return i; }
  
  private MigratorFaction001Invitations() {
    super(Faction.class);
    addInnerMigrator(MigratorFieldRename.get("invitedPlayerIds", "invitations"));
    addInnerMigrator(new MigratorFaction001InvitationsField(null));
  }
  



  public class MigratorFaction001InvitationsField
    extends MigratorFieldConvert
  {
    private MigratorFaction001InvitationsField()
    {
      super();
    }
    




    public Object migrateInner(JsonElement idList)
    {
      JsonObject ret = new JsonObject();
      


      if (!idList.isJsonNull())
      {

        if (!idList.isJsonArray()) { throw new IllegalArgumentException(idList.toString());
        }
        
        for (JsonElement playerId : idList.getAsJsonArray())
        {
          String id = playerId.getAsString();
          

          JsonObject invitation = new JsonObject();
          

          ret.add(id, invitation);
        }
      }
      
      return ret;
    }
  }
}
