package com.afa.vanish.Events;

import com.afa.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class joinEvent implements Listener {
    private Vanish main;
    public joinEvent(Vanish main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (main.getVanishedPlayers().isEmpty()) return;

        Player player = e.getPlayer();
        for (UUID uuid : main.getVanishedPlayers()) {
            player.hidePlayer(Bukkit.getPlayer(uuid));
        }
    }
}
