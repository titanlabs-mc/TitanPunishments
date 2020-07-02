package dev.titanlabs.punishment.listeners;

import dev.titanlabs.punishment.PunishmentPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener(PunishmentPlugin plugin) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
    }
}
