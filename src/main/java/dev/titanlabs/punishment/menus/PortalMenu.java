package dev.titanlabs.punishment.menus;

import me.hyfe.simplespigot.menu.Menu;
import org.bukkit.entity.Player;

// STATIC, no configurability
public class PortalMenu extends Menu {

    public PortalMenu(Player player) {
        super(player, "portal", 1);
    }

    @Override
    public void redraw() {

    }
}
