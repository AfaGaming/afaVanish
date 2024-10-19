package com.afa.vanish;

import com.afa.vanish.Commands.aCommand;
import com.afa.vanish.Commands.vanishCommand;
import com.afa.vanish.Events.chestEvent;
import com.afa.vanish.Events.joinEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Vanish extends JavaPlugin {

    private List<UUID> vanishedPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (!getConfig().getBoolean("plugin-enabled")) {
            // Disabled plugin
            try {
                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);
                CommandMap map = (CommandMap) field.get(Bukkit.getServer());
                getCommand("vanish").unregister(map);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        }
        // Plugin startup logic
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            map.getCommand("vanish").unregister(map);


            getCommand("vanish").setExecutor(new vanishCommand(this));
            getCommand("vanish").setTabCompleter(new vanishCommand(this));
            getCommand("a").setExecutor(new aCommand(this));
            Bukkit.getPluginManager().registerEvents(new joinEvent(this), this);
            Bukkit.getPluginManager().registerEvents(new chestEvent(), this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public List<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }
}
