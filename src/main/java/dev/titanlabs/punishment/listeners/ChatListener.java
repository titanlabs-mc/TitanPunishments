package dev.titanlabs.punishment.listeners;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final UserCache userCache;

    public ChatListener(PunishmentPlugin plugin) {
        this.userCache = plugin.getUserCache();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        this.userCache.get(player.getUniqueId()).thenAccept(optionalUser -> {
            optionalUser.ifPresent(user -> {
                if (user.isMuted()) {
                    event.setCancelled(true);
                }
            });
        });
    }
}
