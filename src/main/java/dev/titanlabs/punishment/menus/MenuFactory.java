package dev.titanlabs.punishment.menus;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.menus.menus.PortalMenu;
import me.hyfe.simplespigot.menu.Menu;
import org.bukkit.entity.Player;

public class MenuFactory {
    private final PunishmentPlugin plugin;

    public MenuFactory(PunishmentPlugin plugin) {
        this.plugin = plugin;
    }

    public Menu createMenu(String menuName, Player player) {
        switch (menuName.toLowerCase()) {
            case "portal":
                return new PortalMenu(player);
        }
        return null;
    }
}
