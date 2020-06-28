package dev.titanlabs.punishment.cache.listener;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {
    private final UserCache userCache;

    public ConnectionListener(PunishmentPlugin plugin) {
        this.userCache = plugin.getUserCache();
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.userCache.load(player.getUniqueId());
    }
}
