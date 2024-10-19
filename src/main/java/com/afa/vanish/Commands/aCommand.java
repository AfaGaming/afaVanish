package com.afa.vanish.Commands;

import com.afa.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class aCommand implements CommandExecutor {
    private Vanish main;
    public aCommand(Vanish main) {
        this.main = main;
    }
    private String msg = main.getConfig().getString("message");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            ((Player) commandSender).sendMessage(msg);
        } else {
            Bukkit.getLogger().info(msg);
        }
        return false;
    }
}
