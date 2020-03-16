package dev.titanlabs.punishment.listeners;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final UserCache userCache;

    public JoinListener(PunishmentPlugin plugin) {
        this.userCache = plugin.getUserCache();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.userCache.get(player.getUniqueId()).thenAccept(optionalUser -> {
            optionalUser.ifPresent(user -> {
                if (user.isBanned()) {
                    player.kickPlayer("BANNED MESSAGE");
                }
            });
        });
    }
}
