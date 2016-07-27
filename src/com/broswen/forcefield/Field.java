package com.broswen.forcefield;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticleEffect;

import java.util.Collection;

/**
 * Created by Brad S on 7/25/2016.
 */
public class Field implements Listener{

    Location loc1, loc2;
    boolean visualize, sound;
    String name, message;
    Plugin plugin;

    public Field(Plugin plugin, String name, Location loc1, Location loc2, boolean visualize, boolean sound, String message){
        this.name = name;
        this.plugin = plugin;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.visualize = visualize;
        this.sound = sound;
        this.message = message;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private boolean isInside(Location loc){
        Double maxX = Math.max(loc1.getX(), loc2.getX());
        Double maxY = Math.max(loc1.getY(), loc2.getY());
        Double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        Double minX = Math.min(loc1.getX(), loc2.getX());
        Double minY = Math.min(loc1.getY(), loc2.getY());
        Double minZ = Math.min(loc1.getZ(), loc2.getZ());
        if((loc.getX() < maxX && loc.getX() > minX) && (loc.getY() < maxY && loc.getY() > minY) && (loc.getZ() < maxZ && loc.getZ() > minZ)){
            return true;
        }
        return false;
    }

    private void bouncePlayer(Player p, Location loc1, Location loc2){
        Vector v = loc1.toVector().subtract(loc2.toVector()).normalize();
        if(v.getY() < 0.5){
            v.setY(v.getY() + 0.2);
        }
        p.setVelocity(v);
        p.setFallDistance(0);

        if(visualize){
            ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation().getX(), p.getLocation().getY()+1, p.getLocation().getZ(),
                    0.4, 0.5, 0.4, 0, 4, 100);
        }
    }

    private void playSound(Player p){
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
    }

    private void visualizeField(){
        Double maxX = Math.max(loc1.getX(), loc2.getX());
        Double maxY = Math.max(loc1.getY(), loc2.getY());
        Double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        Double minX = Math.min(loc1.getX(), loc2.getX());
        Double minY = Math.min(loc1.getY(), loc2.getY());
        Double minZ = Math.min(loc1.getZ(), loc2.getZ());
        for(double x = minX; x < maxX; x++){
            for(double y = minY; y < maxY; y++){
                for(double z = minZ; z < maxZ; z++){
                    ParticleEffect.PORTAL.send(Bukkit.getOnlinePlayers(),x,y,z,0.5,0.5,0.5,0,1,100);
                }
            }
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e){
        if(e.getType().equals(UpdateType.SECOND)){
            if(visualize){
                visualizeField();
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();

        if(!isInside(e.getTo())) return;

        if(p.hasPermission("forcefield." + name)) return;

        bouncePlayer(p, e.getFrom(), e.getTo());

        if(sound) playSound(p);

        if(message != null){
            p.sendMessage(message);
        }
    }
}
