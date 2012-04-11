package com.thedevteam.thecore.tier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spout.api.material.Material;

/**
 *
 *
 */
public class TierGroup {
    private List<Material> materials = new ArrayList<Material>();
    public final String name;
    
    public TierGroup(String name, Material... materials) {
        this.name = name;
        this.materials.addAll(Arrays.asList(materials));
    }

    public TierGroup(String name) {
        this.name = name;
    }
    
    
    public void addMaterial(Material m){
        for(Material a : materials){
            if(a.getMaterial() == m){
                return;
            }
        }
        materials.add(m);
    }
    
    public List<Material> getMaterials(){
        return materials;
    }
    
    public void removeMaterial(final Material m){
        for(Material toMatch : materials){
            if(toMatch == m){
                materials.remove(toMatch);
                return;
            }
        }
    }
}
