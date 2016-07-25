package com.broswen.forcefield;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Created by Brad S on 7/25/2016.
 */
public class FieldManager {

    Plugin plugin;
    List<Field> fields;
    public FieldManager(Plugin plugin){
        this.plugin = plugin;

        loadFields();
    }

    private void loadFields(){
        int i = 0;
        ConfigurationSection configSec = plugin.getConfig().getConfigurationSection("fields");

        for(String s : configSec.getKeys(false)){
            String[] strings = plugin.getConfig().getString("fields." + s + ".area").split(":");
            Location loc1 = deserializeLocation(strings[0]);
            Location loc2 = deserializeLocation(strings[1]);
            fields.add(new Field(plugin, s, loc1, loc2,
                    plugin.getConfig().getBoolean("fields." + s + ".visualize"),
                    plugin.getConfig().getBoolean("fields." + s + ".sound")));
            i++;
        }
        Bukkit.getLogger().info("ForceFields: loaded " + i + " fields.");
    }

    public List<Field> getFields(){
        return fields;
    }

    private Location deserializeLocation(String s){
        String[] strings = s.split(",");
        return new Location(Bukkit.getWorld(strings[0]), Double.valueOf(strings[1]), Double.valueOf(strings[2]), Double.valueOf(strings[3]));
    }
}
