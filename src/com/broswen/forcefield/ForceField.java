package com.broswen.forcefield;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Brad S on 7/25/2016.
 */
public class ForceField extends JavaPlugin{

    FieldManager fm;

    @Override
    public void onEnable(){
        loadConfig();
        fm = new FieldManager(this);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new UpdateEvent(UpdateType.SECOND));
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable(){

    }

    private void loadConfig(){
        saveDefaultConfig();
        saveConfig();
    }

}
