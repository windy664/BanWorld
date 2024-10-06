package org.windy.banworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BanWorld extends JavaPlugin implements Listener {
    private List<String> blacklistedWorlds;
    private List<String> whitelistedWorlds;
    private boolean blacklistEnabled;
    private boolean whitelistEnabled;
    private String prefix;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        
        config = this.getConfig();
        prefix = config.getString("prefix", "§b[§bBanWorld§b]§f ");
    }

    @EventHandler
    public void playerTeleport(PlayerTeleportEvent event) {
        judgeworlds(event);
    }

    @EventHandler
    public void playerPortal(PlayerPortalEvent event) {
        judgeworlds(event);
    }
    @EventHandler
    private void judgeworlds(PlayerTeleportEvent event){
        FileConfiguration config = getConfig();
        List<String> whiteList = BanWorld.this.getConfig().getStringList("whitelisted_worlds");
        List<String> blacklist = BanWorld.this.getConfig().getStringList("blacklisted_worlds");
        if (whitelistEnabled && blacklistEnabled) {
            System.err.println("whitelist_enabled 和 blacklist_enabled 不能同时启用");
        }else {
            String worldName = event.getTo().getWorld().getName();
            if (config.getBoolean("whitelist_enabled", true)) {
                boolean status = whiteList.contains(worldName);
                if (!status) {
                    Player player = event.getPlayer();
                    status = player.hasPermission("world." + worldName);
                    if (!status) {
                        String message = ChatColor.RED + "你没有前往 " + worldName + " 世界的权限";
                        player.sendMessage(message);
                        event.setCancelled(true);
                        logger("已阻止" + player.getName() + "前往" + worldName);
                    }
                }
            } else if (config.getBoolean("blacklist_enabled", true)) {
                boolean status = blacklist.contains(worldName);
                if (status) {
                    Player player = event.getPlayer();
                    status = player.hasPermission("world." + worldName);
                    if (!status) {
                        String message = ChatColor.RED + "你没有前往 " + worldName + " 世界的权限";
                        player.sendMessage(message);
                        event.setCancelled(true);
                        logger("已阻止" + player.getName() + "前往" + worldName);
                    }
                }
            }
        }
    }

        public void logger(String message) {
        if (config.getBoolean("debug", false)) {
            this.getServer().getConsoleSender().sendMessage(prefix + message);
        }
    }
}
