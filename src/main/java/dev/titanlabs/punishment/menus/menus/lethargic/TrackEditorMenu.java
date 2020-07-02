package dev.titanlabs.punishment.menus.menus.lethargic;

import dev.titanlabs.punishment.objects.PunishmentTrack;
import me.hyfe.simplespigot.menu.Menu;
import org.bukkit.entity.Player;

public class TrackEditorMenu extends Menu {
    private final PunishmentTrack track;

    public TrackEditorMenu(Player player, PunishmentTrack track) {
        super(player, "Editing ".concat(track.getName()).concat("..."), 3);
        this.track = track;
    }

    @Override
    public void redraw() {

    }
}
