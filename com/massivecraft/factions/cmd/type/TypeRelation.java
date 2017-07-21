package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.Rel;




public class TypeRelation
  extends TypeRel
{
  private static TypeRelation i = new TypeRelation();
  public static TypeRelation get() { return i; }
  public TypeRelation() { setAll(new Rel[] { Rel.NEUTRAL, Rel.TRUCE, Rel.ALLY, Rel.ENEMY }); }
  





  public String getName()
  {
    return "relation";
  }
}
