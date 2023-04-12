package com.tcoded.noelytrainregion;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventListener implements Listener {

    private final NoElytraInRegion plugin;

    public EventListener(NoElytraInRegion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) return;

        // Check change block
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        // Check if player is in region
        if (plugin.getNoElytraRegions().stream().anyMatch(region -> region.contains(to.getBlockX(), to.getBlockY(), to.getBlockZ()))) {
            Player player = event.getPlayer();
            player.setGliding(false);
            player.sendMessage(ChatColor.RED + "You can't use an elytra in this region!");
        }
    }

}
