package org.windy.banworld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BanWorld extends JavaPlugin implements Listener {
    private List<String> blacklistedWorlds;
    private List<String> whitelistedWorlds;
    private boolean blacklistEnabled;
    private boolean whitelistEnabled;
    private World defaultWorld;

    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private void loadConfig() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        blacklistedWorlds = config.getStringList("blacklisted_worlds");
        whitelistedWorlds = config.getStringList("whitelisted_worlds");
        blacklistEnabled = config.getBoolean("blacklist_enabled", true);
        whitelistEnabled = config.getBoolean("whitelist_enabled", false);
        defaultWorld = Bukkit.getWorld(config.getString("default_world", "world"));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        World targetWorld = event.getTo().getWorld();
        if (isBlacklisted(targetWorld) && blacklistEnabled) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("You are not allowed to teleport to this world.");
            event.getPlayer().teleport(defaultWorld.getSpawnLocation());
        } else if (!isWhitelisted(targetWorld) && whitelistEnabled) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("You are not allowed to teleport to this world.");
            event.getPlayer().teleport(defaultWorld.getSpawnLocation());
        }
    }

    private boolean isBlacklisted(World world) {
        return blacklistedWorlds.contains(world.getName());
    }

    private boolean isWhitelisted(World world) {
        return whitelistedWorlds.contains(world.getName());
    }
}
