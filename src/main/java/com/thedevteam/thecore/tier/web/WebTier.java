package com.thedevteam.thecore.tier.web;

import com.thedevteam.thecore.tier.TierGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.spout.api.material.Material;

/**
 *
 *
 */
public class WebTier {
    private static Map<String, TierGroup> groups = new HashMap<String, TierGroup>();
    private static Map<Material, List<TierGroup>> groupsIn = new HashMap<Material, List<TierGroup>>();
    private static Map<Material, Double> values = new HashMap<Material, Double>();
    
    public static void addGroup(TierGroup group){
        groups.put(group.name, group);
        for(Material m : group.getMaterials()){
            if(groupsIn.get(m) == null) groupsIn.put(m, new ArrayList<TierGroup>());
            groupsIn.get(m).add(group);
            if(values.get(m) == null) values.put(m, 0d);
        }
    }
    
    public static void addMaterial(Material m, TierGroup... tierGroups){
        if(values.get(m) == null) values.put(m, 0d);
        for(TierGroup g : tierGroups){
            groups.put(g.name, g);
            if(groupsIn.get(m) == null) groupsIn.put(m, new ArrayList<TierGroup>());
            groupsIn.get(m).add(g);
            g.addMaterial(m);
        }
    }
    
    public static void setValue(Material original, double value){
        if(!values.containsKey(original)) return;
        if(value>1) value = 1;
        if(value<0) value = 0;
        double toAdd = value - values.get(original);
        addValue(original, toAdd);
    }
    
    public static void addValue(Material original, double value){
        if(!values.containsKey(original)) return;
        values.put(original, values.get(original) + value);
        for(TierGroup g : groupsIn.get(original)){
            double toAddToEach = (value/g.getMaterials().size());
            for(Material material : g.getMaterials()){
                if (material == original){
                    continue;
                }
                values.put(material, values.get(material) + toAddToEach);
                if(values.get(material) > 1) values.put(material, 1d);
                if(values.get(material) < 0) values.put(material, 0d);
            }
        }        
    }
    
    public static double getValue(Material original){
        if(values.containsKey(original)){
            return values.get(original);
        }
        return Double.POSITIVE_INFINITY;
    }
}
