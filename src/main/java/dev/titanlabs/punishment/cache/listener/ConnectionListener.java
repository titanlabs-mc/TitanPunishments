package dev.titanlabs.punishment.cache.listener;

import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.cache.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    private final UserCache userCache;

    public ConnectionListener(Punishment plugin) {
        this.userCache = plugin.getUserCache();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.userCache.load(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.userCache.unload(player.getUniqueId());
    }
}
