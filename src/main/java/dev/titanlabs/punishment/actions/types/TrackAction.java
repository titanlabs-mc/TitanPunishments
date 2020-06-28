package dev.titanlabs.punishment.actions.types;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.manager.TrackManager;
import org.bukkit.entity.Player;

public class TrackAction extends Action {
    private final TrackManager trackManager;


    public TrackAction(String type, PunishmentPlugin plugin, String condition, String value) {
        super(type, condition, value);
        this.trackManager = plugin.getTrackManager();
    }

    public void execute(Player player) {

    }


}
