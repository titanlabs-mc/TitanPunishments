package dev.titanlabs.punishment.actions.types;

import dev.titanlabs.punishment.actions.Action;
import org.bukkit.entity.Player;

public class MenuAction extends Action {

    public MenuAction(String type, String condition, String value) {
        super(type, condition, value);
    }

    public void execute(Player player) {

    }
}
