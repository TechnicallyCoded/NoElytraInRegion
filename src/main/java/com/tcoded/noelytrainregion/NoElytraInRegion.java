package com.tcoded.noelytrainregion;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NoElytraInRegion extends JavaPlugin {

    private List<String> noElytraRegionNames;
    private List<ProtectedRegion> noElytraRegions;
    private RegionContainer regionContainer;
    private boolean resetVelocity;

    @Override
    public void onEnable() {
        // Config
        saveDefaultConfig();
        loadConfigValues();
        loadRegionNames();

        // WorldGuard
        WorldGuard instance = WorldGuard.getInstance();
        regionContainer = instance.getPlatform().getRegionContainer();
        loadRegions();

        // Commands
        PluginCommand noElytraPlCmd = getCommand("noelytra");
        if (noElytraPlCmd != null) noElytraPlCmd.setExecutor(new CommandNoElytra(this));

        // Listeners
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public void loadConfigValues() {
        resetVelocity = getConfig().getBoolean("reset-velocity");
    }

    public void loadRegionNames() {
        noElytraRegionNames = getConfig().getStringList("no-elytra-regions");
    }

    public void loadRegions() {
        this.noElytraRegions = new ArrayList<>();

        for (String regionName : noElytraRegionNames) {
            String[] parts = regionName.split(":");

            World world = getServer().getWorld(parts[0]);
            if (world == null) {
                getLogger().warning(String.format("World %s does not exist!", parts[0]));
                continue;
            }

            RegionManager regionManager = regionContainer.get(new BukkitWorld(world));
            if (regionManager == null) {
                getLogger().warning(String.format("World %s is not valid for WorldGuard!", parts[0]));
                continue;
            }

            ProtectedRegion region = regionManager.getRegion(parts[1]);
            if (region == null) {
                getLogger().warning(String.format("Region %s does not exist in world %s!", parts[1], parts[0]));
                continue;
            }

            noElytraRegions.add(region);
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public List<ProtectedRegion> getNoElytraRegions() {
        return noElytraRegions;
    }

    public boolean shouldResetVelocity() {
        return resetVelocity;
    }
}
