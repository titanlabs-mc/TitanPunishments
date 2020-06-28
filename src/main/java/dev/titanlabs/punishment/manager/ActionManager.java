package dev.titanlabs.punishment.manager;

import com.sun.istack.internal.Nullable;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.actions.types.*;
import org.bukkit.entity.Player;

public class ActionManager {
    private final PunishmentPlugin plugin;

    public ActionManager(PunishmentPlugin plugin) {
        this.plugin = plugin;
    }

    public void parseAndAct(String input, @Nullable Player player) {
        Action action = this.parse(input);
        if (action instanceof BanAction) {
            ((BanAction) action).execute(player);
        } else if (action instanceof KickAction) {
            ((KickAction) action).execute(player);
        } else if (action instanceof MenuAction) {
            ((MenuAction) action).execute(player);
        } else if (action instanceof MuteAction) {
            ((MuteAction) action).execute(player);
        } else if (action instanceof WarnAction) {
            ((WarnAction) action).execute(player);
        }
    }

    public Action parse(String input) {
        String condition = input.contains("(") && input.contains(")") ? input.substring(input.indexOf("(") + 1, input.indexOf(")")).toLowerCase() : "";
        String value = input.contains("{") && input.contains("}") ? input.substring(input.indexOf("{") + 1, input.indexOf("}")).toLowerCase() : "";
        switch (input.contains("[") && input.contains("]") ? input.substring(input.indexOf("[") + 1, input.indexOf("]")).toLowerCase() : "") {
            case "ban":
                return new BanAction("ban", this.plugin, condition, value);
            case "kick":
                return new KickAction("kick", this.plugin, condition, value);
            case "mute":
                return new MuteAction("mute", this.plugin, condition, value);
            case "warning":
            case "warn":
                return new WarnAction("warn", this.plugin, condition, value);
            default:
                return null;
        }
    }
}
