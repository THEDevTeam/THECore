package com.thedevteam.thecore.pipes;

import org.spout.api.inventory.ItemStack;

/**
 * Interface for anything that can receive items.
 *
 */
public interface ItemDestination {
    
    public void give(ItemStack item);
    
}
