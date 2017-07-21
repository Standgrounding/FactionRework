package com.massivecraft.factions;

import org.bukkit.ChatColor;



public class Const
{
  public static final int MAP_WIDTH = 48;
  public static final int MAP_HEIGHT = 8;
  public static final int MAP_HEIGHT_FULL = 17;
  public static final char[] MAP_KEY_CHARS = "\\/#?ç¬£$%=&^ABCDEFGHJKLMNOPQRSTUVWXYZÄÖÜÆØÅ1234567890abcdeghjmnopqrsuvwxyÿzäöüæøåâêîûô".toCharArray();
  public static final String MAP_KEY_WILDERNESS = ChatColor.GRAY.toString() + "-";
  public static final String MAP_KEY_SEPARATOR = ChatColor.AQUA.toString() + "+";
  public static final String MAP_KEY_OVERFLOW = ChatColor.MAGIC.toString() + "-" + ChatColor.RESET.toString();
  public static final String MAP_OVERFLOW_MESSAGE = MAP_KEY_OVERFLOW + ": Too Many Factions (>" + MAP_KEY_CHARS.length + ") on this Map.";
  public static final String BASENAME = "factions";
  public static final String BASENAME_ = "factions_";
  public static final String SHOW_ID_FACTION_ID = "factions_id";
  public static final String SHOW_ID_FACTION_DESCRIPTION = "factions_description";
  public static final String SHOW_ID_FACTION_AGE = "factions_age";
  public static final String SHOW_ID_FACTION_FLAGS = "factions_flags";
  public static final String SHOW_ID_FACTION_POWER = "factions_power";
  public static final String SHOW_ID_FACTION_LANDVALUES = "factions_landvalue";
  public static final String SHOW_ID_FACTION_BANK = "factions_bank";
  public static final String SHOW_ID_FACTION_FOLLOWERS = "factions_followers";
  public static final int SHOW_PRIORITY_FACTION_ID = 1000;
  public static final int SHOW_PRIORITY_FACTION_DESCRIPTION = 2000;
  public static final int SHOW_PRIORITY_FACTION_AGE = 3000;
  public static final int SHOW_PRIORITY_FACTION_FLAGS = 4000;
  public static final int SHOW_PRIORITY_FACTION_POWER = 5000;
  public static final int SHOW_PRIORITY_FACTION_LANDVALUES = 6000;
  public static final int SHOW_PRIORITY_FACTION_BANK = 7000;
  public static final int SHOW_PRIORITY_FACTION_FOLLOWERS = 9000;
  
  public Const() {}
}
