package com.tcoded.noelytrainregion;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandNoElytra implements CommandExecutor {

    private final NoElytraInRegion plugin;

    public CommandNoElytra(NoElytraInRegion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /noelytra reload");
            return true;
        }

        String arg0Lower = args[0].toLowerCase();

        if (arg0Lower.equals("reload")) {
            plugin.reloadConfig();
            plugin.loadRegionNames();
            plugin.loadRegions();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
        }
        else {
            sender.sendMessage(ChatColor.RED + "Usage: /noelytra reload");
        }

        return true;
    }
}
