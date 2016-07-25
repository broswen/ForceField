package com.broswen.forcefield;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Brad S on 7/25/2016.
 */
public class UpdateEvent extends Event{

    UpdateType type;
    public UpdateEvent(UpdateType type){
        this.type = type;
    }

    public UpdateType getType(){
        return type;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() { return null;}
}
