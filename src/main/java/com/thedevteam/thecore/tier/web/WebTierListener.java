package com.thedevteam.thecore.tier.web;

import com.thedevteam.thecore.tier.event.BlockPlaceFailEvent;
import com.thedevteam.thecore.tier.event.CraftFailEvent;
import org.spout.api.Spout;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.event.inventory.PlayerInventoryCraftEvent;

/**
 *
 *
 */
public class WebTierListener implements Listener{
    private final WebTier parent;

    public WebTierListener(WebTier parent) {
        this.parent = parent;
    }
    
    @EventHandler(order = Order.LATEST)
    public void onCraftEvent(PlayerInventoryCraftEvent event){
        double lack = 1 - parent.getValue(event.getResult().getMaterial());
        if(Math.random() < lack){
            event.setCancelled(true);
            Spout.getEventManager().callEvent(new CraftFailEvent(event));
        }
    }
    
    @EventHandler(order = Order.LATEST)
    public void onBlockPlace(BlockChangeEvent event){
        double lack = 1 - parent.getValue(event.getSnapshot().getMaterial());
        if(Math.random() < lack){
            event.setCancelled(true);
            Spout.getEventManager().callEvent(new BlockPlaceFailEvent(event));
        }
    }
}
