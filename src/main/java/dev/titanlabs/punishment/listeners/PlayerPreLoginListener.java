package dev.titanlabs.punishment.listeners;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.user.StrippedUser;
import dev.titanlabs.punishment.storage.StrippedUserStorage;
import me.hyfe.simplespigot.service.General;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.TimeUnit;

public class PlayerPreLoginListener implements Listener {
    private final StrippedUserStorage storage;
    private final Lang lang;

    public PlayerPreLoginListener(PunishmentPlugin plugin) {
        this.storage = plugin.getStrippedUserStorage();
        this.lang = plugin.getLang();
    }

    @EventHandler
    public void beforeLogin(AsyncPlayerPreLoginEvent event) {
        StrippedUser user = this.storage.load(FastUUID.toString(event.getUniqueId()));

        if (user != null && user.isBanned()) {
            StringBuilder configLocation = new StringBuilder("ban-kick-message");
            if (user.getBan().isTemporary()) {
                configLocation.append("-temporary");
            } else {
                configLocation.append("-permanent");
            }
            if (user.getBan().getReason() != null) {
                configLocation.append("-reason");
            }
            event.setKickMessage(this.lang.get(configLocation.toString(), replacer -> replacer
                    .set("reason", user.getBan().getReason())
                    .set("length", General.formatSeconds(user.getBan().getRemainingTime(TimeUnit.SECONDS)))).compatibleString());
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
        }
    }
}
