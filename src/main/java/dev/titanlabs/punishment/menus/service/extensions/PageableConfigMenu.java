package dev.titanlabs.punishment.menus.service.extensions;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.menus.PageMethods;
import dev.titanlabs.punishment.menus.service.MenuIllustrator;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.menu.PageableMenu;
import me.hyfe.simplespigot.text.Replace;
import me.hyfe.simplespigot.text.Replacer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.UnaryOperator;

public abstract class PageableConfigMenu<T> extends PageableMenu<T> implements PageMethods {
    protected final PunishmentPlugin plugin;
    protected final Config config;
    protected final MenuIllustrator illustrator;
    private Map<String, Runnable> customActions;

    public PageableConfigMenu(PunishmentPlugin plugin, Config config, Player player, UnaryOperator<Replacer> titleReplacer) {
        super(player, titleReplacer.apply(new Replacer()).applyTo(config.string("menu-title")), config.integer("menu-rows"));
        this.plugin = plugin;
        this.config = config;
        this.illustrator = plugin.getMenuIllustrator();
    }

    @Override
    public void redraw() {
        this.drawPageableItems(() -> this.drawConfigItems(null));
    }

    public void drawConfigItems(Replace replace) {
        this.illustrator.draw(this, this.config, this.plugin.getMenuFactory(), this.player, this.plugin.getActionManager(), this.customActions, replace);
    }

    public void customAction(String value, Runnable runnable) {
        this.customActions.put(value, runnable);
    }
}