package dev.titanlabs.punishment.listeners;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.StrippedUser;
import dev.titanlabs.punishment.storage.StrippedUserStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    private final StrippedUserStorage storage;

    public PlayerPreLoginListener(PunishmentPlugin plugin) {
        this.storage = plugin.getStrippedUserStorage();
    }

    @EventHandler
    public void beforeLogin(AsyncPlayerPreLoginEvent event) {
        StrippedUser user = this.storage.load(FastUUID.toString(event.getUniqueId()));
        System.out.println("AAAA");

        if (user.isBanned()) {
            event.setKickMessage("KICK MESSAGE");
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
        }
    }
}
