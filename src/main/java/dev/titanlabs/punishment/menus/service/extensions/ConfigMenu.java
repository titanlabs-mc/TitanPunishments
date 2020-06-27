package dev.titanlabs.punishment.menus.service.extensions;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.menus.service.MenuIllustrator;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.menu.Menu;
import me.hyfe.simplespigot.text.Replace;
import me.hyfe.simplespigot.text.Replacer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.UnaryOperator;

public abstract class ConfigMenu extends Menu {
    protected final PunishmentPlugin plugin;
    protected final Config config;
    protected final MenuIllustrator illustrator;
    private Map<String, Runnable> dynamicActions;

    public ConfigMenu(PunishmentPlugin plugin, Config config, Player player) {
        super(player, config.string("menu-title"), config.integer("menu-rows"));
        this.plugin = plugin;
        this.config = config;
        this.illustrator = plugin.getMenuIllustrator();
    }

    public ConfigMenu(PunishmentPlugin plugin, Config config, Player player, UnaryOperator<Replacer> replacer) {
        super(player, replacer.apply(new Replacer()).applyTo(config.string("menu-title")), config.integer("menu-rows"));
        this.plugin = plugin;
        this.config = config;
        this.illustrator = plugin.getMenuIllustrator();
    }

    @Override
    public void redraw() {
        this.drawConfigItems(null);
    }

    public void drawConfigItems(Replace replace) {
        this.illustrator.draw(this, this.config, this.plugin.getMenuFactory(), this.player, this.plugin.getActionManager(), this.dynamicActions, replace);
    }

    public void dynamicAction(String value, Runnable runnable) {
        this.dynamicActions.put(value, runnable);
    }
}