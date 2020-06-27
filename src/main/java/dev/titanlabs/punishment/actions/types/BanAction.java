package dev.titanlabs.punishment.actions.types;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.cache.UserCache;
import org.bukkit.entity.Player;

public class BanAction extends Action {
    private final UserCache userCache;

    public BanAction(PunishmentPlugin plugin, String condition, String value) {
        super(condition, value);
        this.userCache = plugin.getUserCache();
    }

    public void execute(Player player) {

    }
}
