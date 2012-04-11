package com.thedevteam.thecore.tier;

import org.spout.api.material.Material;

/**
 *
 * @deprecated Currently has no uses. Unless use is found, will be deleted.
 */
public class Attainable {
    private final Material material;
    private double value = 0;

    public Attainable(final Material material) {
        this.material = material;
    }
    
    public double getValue(){
        return value;
    }
    
    public void setValue(double value){
        this.value = value;
    }

    /**
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }
    
    
}
