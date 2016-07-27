package com.broswen.forcefield;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Brad S on 7/25/2016.
 */
public class UpdateEvent extends Event{
    private static final HandlerList handlers = new HandlerList();

    UpdateType type;
    public UpdateEvent(UpdateType type){
        this.type = type;
    }

    public UpdateType getType(){
        return type;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
