package com.thedevteam.thecore.pipes;

import org.spout.api.inventory.ItemStack;

/**
 * Interface for anything that can give items.
 *
 */
public interface ItemSource {
    
    public ItemStack get();
    
}
