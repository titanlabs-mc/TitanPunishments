package dev.titanlabs.punishment.listeners;

import dev.titanlabs.punishment.PunishmentPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PreLoginListener implements Listener {

    public PreLoginListener(PunishmentPlugin plugin) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {

    }
}
