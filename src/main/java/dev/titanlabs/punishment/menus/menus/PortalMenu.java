package dev.titanlabs.punishment.menus.menus;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.menus.service.extensions.ConfigMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PortalMenu extends ConfigMenu {

    public PortalMenu(PunishmentPlugin plugin, Player player) {
        super(plugin, plugin.getConfig("portal-menu"), player);
    }

    @Override
    public void redraw() {
        this.drawConfigItems(replacer -> replacer
                .set("online_players", Bukkit.getOnlinePlayers().size()));
    }
}
