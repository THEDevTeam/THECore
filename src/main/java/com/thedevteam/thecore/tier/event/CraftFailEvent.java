package com.thedevteam.thecore.tier.event;

import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;
import org.spout.api.event.inventory.PlayerInventoryCraftEvent;

/**
 *
 *
 */
public class CraftFailEvent extends Event{
    private static HandlerList handlers = new HandlerList();
    private PlayerInventoryCraftEvent event;

    public CraftFailEvent(PlayerInventoryCraftEvent event) {
        this.event = event;
    }
        
    @Override
    public HandlerList getHandlers() {
            return handlers;
    }

    public static HandlerList getHandlerList() {
            return handlers;
    }
}
