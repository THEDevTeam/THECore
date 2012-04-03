package com.thedevteam.thecore.materials;

import org.spout.api.material.Material;
import org.spout.api.material.MaterialRegistry;

/**
 *
 *
 */
public abstract class Materials {
    
    @SuppressWarnings("unchecked")
    protected final static <T extends Material> T register(T material) {
        String name = material.getName();
        Material registered = MaterialRegistry.get(name);
        if(registered != null){
            try{
               T t = (T) material.getClass().cast(registered);
               return t;
            }
            catch(ClassCastException e){
            }
        }
        return MaterialRegistry.register(material);
    }
}
