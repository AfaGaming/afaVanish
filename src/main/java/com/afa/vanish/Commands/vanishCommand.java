package com.afa.vanish.Commands;

import com.afa.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class vanishCommand implements CommandExecutor, TabCompleter {
    private Vanish main;

    public vanishCommand(Vanish main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (!(player.hasPermission("vanish.use"))) return false;

        if (args.length == 1) {
            if (args[0] == "reload") {
                main.reloadConfig();
                player.sendMessage(ChatColor.GREEN + "Config reloaded!");
            } else if (Bukkit.getPlayer(args[0]) != null) return false;
            Player target = Bukkit.getPlayer(args[0]);

            if (main.getVanishedPlayers().contains(target.getUniqueId())) {
                // they are vanished
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    main.getVanishedPlayers().remove(target.getUniqueId());
                    player1.showPlayer(main, target);
                }
                player.sendMessage(ChatColor.RED + "You unvanished " + target.getName() + ".");
                target.sendMessage(ChatColor.RED + player.getName() + " unvanished you.");
            } else {
                // they are not vanished
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    main.getVanishedPlayers().add(target.getUniqueId());
                    player1.hidePlayer(main, target);
                }
                player.sendMessage(ChatColor.GREEN + "You vanished " + target.getName() + ".");
                target.sendMessage(ChatColor.GREEN + player.getName() + " vanished you.");
            }
        } else {
            if (main.getVanishedPlayers().contains(player.getUniqueId())) {
                // They are vanished
                main.getVanishedPlayers().remove(player.getUniqueId());
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.showPlayer(main, player);
                }
                player.sendMessage(ChatColor.RED + "You are no longer vanished!");
            } else {
                // They are not vanished
                main.getVanishedPlayers().add(player.getUniqueId());
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.hidePlayer(main, player);
                }
                player.sendMessage(ChatColor.GREEN + "You are now vanished!");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player loopplayer : Bukkit.getOnlinePlayers()) {
                playerNames.add(loopplayer.getName());
            }
            return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}