package dev.titanlabs.punishment.actions.types;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.manager.TrackManager;
import dev.titanlabs.punishment.objects.PunishmentTrack;
import dev.titanlabs.punishment.objects.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class TrackAction extends Action {
    private final TrackManager trackManager;
    private final UserCache userCache;
    private PunishmentTrack track;

    public TrackAction(String type, PunishmentPlugin plugin, String condition, String value) {
        super(type, condition, value);
        this.trackManager = plugin.getTrackManager();
        this.userCache = plugin.getUserCache();
        this.setupTrack();
    }

    public void execute(Player player) {
        this.userCache.get(player.getUniqueId()).thenAccept(optionalUser -> {
            if (!optionalUser.isPresent()) {
                return;
            }
            User user = optionalUser.get();
            user.punishForTrack(this.track);
        });
    }

    private void setupTrack() {
        this.trackManager.get(this.value).thenAccept(optionalTrack -> {
            if (!optionalTrack.isPresent()) {
                Bukkit.getLogger().log(Level.SEVERE, "You have a track action using the track ID ".concat(this.value).concat(" but no track with that ID exists."));
                return;
            }
            this.track = optionalTrack.get();
        });
    }
}
