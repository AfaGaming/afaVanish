package com.afa.vanish.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class chestEvent implements Listener {
    private HashMap<UUID, Inventory> openedChests = new HashMap<>();

    @EventHandler
    public void onChestOpen(InventoryOpenEvent e) {
        if (!(e.getInventory().getType().equals(InventoryType.CHEST))) return;

        Player player = (Player) e.getPlayer();
        Inventory chest = e.getInventory();
        Inventory newInventory = Bukkit.createInventory(player, chest.getSize());

        // Copy the chest items to the new inventory
        for (int i = 0; i < chest.getSize(); i++) {
            ItemStack item = chest.getItem(i);
            newInventory.setItem(i, item);
        }

        // Store the original chest in the map with the player as the key
        openedChests.put(player.getUniqueId(), chest);

        // Cancel the original chest opening and open the new inventory
        e.setCancelled(true);
        player.openInventory(newInventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();

        // Check if the player had opened a duplicate chest inventory
        if (openedChests.containsKey(player.getUniqueId())) {
            Inventory newInventory = e.getInventory(); // The duplicate inventory being closed
            Inventory originalChest = openedChests.get(player.getUniqueId()); // The original chest inventory

            // Copy the items from the duplicate back to the original chest
            for (int i = 0; i < newInventory.getSize(); i++) {
                originalChest.setItem(i, newInventory.getItem(i));
            }

            // Remove the player from the map as we've restored the chest contents
            openedChests.remove(player.getUniqueId());
        }
    }
}
