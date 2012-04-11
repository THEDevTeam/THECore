package com.thedevteam.thecore.tier.event;

import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;
import org.spout.api.event.block.BlockChangeEvent;

/**
 *
 *
 */
public class BlockPlaceFailEvent extends Event{
    private static HandlerList handlers = new HandlerList();
    private BlockChangeEvent event;

    public BlockPlaceFailEvent(BlockChangeEvent event) {
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
