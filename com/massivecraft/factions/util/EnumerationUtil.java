package com.massivecraft.factions.util;

import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.collections.BackstringSet;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;





public class EnumerationUtil
{
  public static final BackstringSet<Material> MATERIALS_EDIT_ON_INTERACT = new BackstringSet(Material.class, new Object[] { "DIODE_BLOCK_OFF", "DIODE_BLOCK_ON", "NOTE_BLOCK", "CAULDRON", "SOIL", "DAYLIGHT_DETECTOR", "DAYLIGHT_DETECTOR_INVERTED", "REDSTONE_COMPARATOR_OFF", "REDSTONE_COMPARATOR_ON" });
  




  public EnumerationUtil() {}
  




  public static boolean isMaterialEditOnInteract(Material material)
  {
    return (MATERIALS_EDIT_ON_INTERACT.contains(material)) || (getmaterialsEditOnInteract.contains(material));
  }
  




  public static final BackstringSet<Material> MATERIALS_EDIT_TOOL = new BackstringSet(Material.class, new Object[] { "FIREBALL", "FLINT_AND_STEEL", "BUCKET", "WATER_BUCKET", "LAVA_BUCKET", "ARMOR_STAND", "END_CRYSTAL", "CHEST", "SIGN_POST", "TRAPPED_CHEST", "SIGN", "WOOD_DOOR", "IRON_DOOR" });
  

















  public static boolean isMaterialEditTool(Material material)
  {
    return (MATERIALS_EDIT_TOOL.contains(material)) || (getmaterialsEditTools.contains(material));
  }
  





  public static final BackstringSet<Material> MATERIALS_DOOR = new BackstringSet(Material.class, new Object[] { "WOODEN_DOOR", "ACACIA_DOOR", "BIRCH_DOOR", "DARK_OAK_DOOR", "JUNGLE_DOOR", "SPRUCE_DOOR", "TRAP_DOOR", "FENCE_GATE", "ACACIA_FENCE_GATE", "BIRCH_FENCE_GATE", "DARK_OAK_FENCE_GATE", "JUNGLE_FENCE_GATE", "SPRUCE_FENCE_GATE" });
  














  public static boolean isMaterialDoor(Material material)
  {
    return (MATERIALS_DOOR.contains(material)) || (getmaterialsDoor.contains(material));
  }
  




  public static final BackstringSet<Material> MATERIALS_CONTAINER = new BackstringSet(Material.class, new Object[] { "DISPENSER", "CHEST", "FURNACE", "BURNING_FURNACE", "JUKEBOX", "BREWING_STAND", "ENCHANTMENT_TABLE", "ANVIL", "BEACON", "TRAPPED_CHEST", "HOPPER", "DROPPER", "BLACK_SHULKER_BOX", "BLUE_SHULKER_BOX", "BROWN_SHULKER_BOX", "CYAN_SHULKER_BOX", "GRAY_SHULKER_BOX", "GREEN_SHULKER_BOX", "LIGHT_BLUE_SHULKER_BOX", "LIME_SHULKER_BOX", "MAGENTA_SHULKER_BOX", "ORANGE_SHULKER_BOX", "PINK_SHULKER_BOX", "PURPLE_SHULKER_BOX", "RED_SHULKER_BOX", "SILVER_SHULKER_BOX", "WHITE_SHULKER_BOX", "YELLOW_SHULKER_BOX" });
  































  public static boolean isMaterialContainer(Material material)
  {
    return (MATERIALS_CONTAINER.contains(material)) || (getmaterialsContainer.contains(material));
  }
  





  public static final BackstringSet<EntityType> ENTITY_TYPES_EDIT_ON_INTERACT = new BackstringSet(EntityType.class, new Object[] { "ITEM_FRAME", "ARMOR_STAND" });
  



  public static boolean isEntityTypeEditOnInteract(EntityType entityType)
  {
    return (ENTITY_TYPES_EDIT_ON_INTERACT.contains(entityType)) || (getentityTypesEditOnInteract.contains(entityType));
  }
  





  public static final BackstringSet<EntityType> ENTITY_TYPES_EDIT_ON_DAMAGE = new BackstringSet(EntityType.class, new Object[] { "ITEM_FRAME", "ARMOR_STAND", "ENDER_CRYSTAL" });
  




  public static boolean isEntityTypeEditOnDamage(EntityType entityType)
  {
    return (ENTITY_TYPES_EDIT_ON_DAMAGE.contains(entityType)) || (getentityTypesEditOnDamage.contains(entityType));
  }
  




  public static final BackstringSet<EntityType> ENTITY_TYPES_CONTAINER = new BackstringSet(EntityType.class, new Object[] { "MINECART_CHEST", "MINECART_HOPPER" });
  



  public static boolean isEntityTypeContainer(EntityType entityType)
  {
    return (ENTITY_TYPES_CONTAINER.contains(entityType)) || (getentityTypesContainer.contains(entityType));
  }
  




  public static final BackstringSet<EntityType> ENTITY_TYPES_MONSTER = new BackstringSet(EntityType.class, new Object[] { "BLAZE", "CAVE_SPIDER", "CREEPER", "ELDER_GUARDIAN", "ENDERMAN", "ENDERMITE", "ENDER_DRAGON", "EVOKER", "GUARDIAN", "GHAST", "GIANT", "HUSK", "MAGMA_CUBE", "PIG_ZOMBIE", "POLAR_BEAR", "SILVERFISH", "SHULKER", "SKELETON", "SLIME", "SPIDER", "STRAY", "VINDICATOR", "VEX", "WITCH", "WITHER", "WITHER_SKELETON", "ZOMBIE", "ZOMBIE_VILLAGER" });
  





























  public static boolean isEntityTypeMonster(EntityType entityType)
  {
    return (ENTITY_TYPES_MONSTER.contains(entityType)) || (getentityTypesMonsters.contains(entityType));
  }
  




  public static final BackstringSet<EntityType> ENTITY_TYPES_ANIMAL = new BackstringSet(EntityType.class, new Object[] { "BAT", "CHICKEN", "COW", "DONKEY", "HORSE", "LLAMA", "MULE", "MUSHROOM_COW", "OCELOT", "PIG", "RABBIT", "SHEEP", "SKELETON_HORSE", "SQUID", "WOLF", "ZOMBIE_HORSE" });
  

















  public static boolean isEntityTypeAnimal(EntityType entityType)
  {
    return (ENTITY_TYPES_ANIMAL.contains(entityType)) || (getentityTypesAnimals.contains(entityType));
  }
}
