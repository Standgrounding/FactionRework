package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.Rel;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.command.type.enumeration.TypeEnum;
import com.massivecraft.massivecore.util.MUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;





public class TypeRank
  extends TypeEnum<Rel>
{
  public static final Set<String> NAMES_PROMOTE = new MassiveSet(new String[] { "Promote", "+", "Plus", "Up" });
  





  public static final Set<String> NAMES_DEMOTE = new MassiveSet(new String[] { "Demote", "-", "Minus", "Down" });
  











  private static final TypeRank i = new TypeRank(null);
  public static TypeRank get() { return i; }
  


  static
  {
    Map<Rel, TypeRank> result = new MassiveMap();
    for (Rel rel : Rel.values())
    {
      if (rel.isRank())
        result.put(rel, new TypeRank(rel));
    }
    result.put(null, i);
    instances = Collections.unmodifiableMap(result); }
  
  public static TypeRank get(Rel rank) { return (TypeRank)instances.get(rank); }
  

  public TypeRank(Rel rank)
  {
    super(Rel.class);
    if ((rank != null) && (!rank.isRank())) throw new IllegalArgumentException(rank + " is not a valid rank");
    startRank = rank;
    

    List<Rel> all = MUtil.list(Rel.values());
    for (Iterator<Rel> it = all.iterator(); it.hasNext();)
    {
      if (!((Rel)it.next()).isRank()) { it.remove();
      }
    }
    setAll(all);
  }
  

  private static final Map<Rel, TypeRank> instances;
  
  private final Rel startRank;
  public Rel getStartRank()
  {
    return startRank;
  }
  




  public String getName()
  {
    return "rank";
  }
  

  public String getNameInner(Rel value)
  {
    return value.getName();
  }
  


  public Set<String> getNamesInner(Rel value)
  {
    Set<String> ret = new MassiveSet();
    

    ret.addAll(value.getNames());
    

    Rel start = getStartRank();
    if (start != null)
    {
      if ((value == Rel.LEADER) && (start == Rel.OFFICER)) { ret.addAll(NAMES_PROMOTE);
      }
      if ((value == Rel.OFFICER) && (start == Rel.MEMBER)) ret.addAll(NAMES_PROMOTE);
      if ((value == Rel.OFFICER) && (start == Rel.LEADER)) { ret.addAll(NAMES_DEMOTE);
      }
      if ((value == Rel.MEMBER) && (start == Rel.RECRUIT)) ret.addAll(NAMES_PROMOTE);
      if ((value == Rel.MEMBER) && (start == Rel.OFFICER)) { ret.addAll(NAMES_DEMOTE);
      }
      if ((value == Rel.RECRUIT) && (start == Rel.MEMBER)) { ret.addAll(NAMES_DEMOTE);
      }
    }
    
    return ret;
  }
}
