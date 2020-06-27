package dev.titanlabs.punishment;

import dev.titanlabs.punishment.actions.ActionManager;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.menus.MenuFactory;
import dev.titanlabs.punishment.menus.service.MenuIllustrator;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.plugin.SpigotPlugin;
import org.bukkit.event.HandlerList;

import java.nio.file.Path;

public class PunishmentPlugin extends SpigotPlugin {
    protected static PunishmentApi api;
    private UserCache userCache;
    private ActionManager actionManager;
    private MenuIllustrator menuIllustrator;
    private MenuFactory menuFactory;

    public void load() {
        this.userCache = new UserCache(this);
        this.actionManager = new ActionManager(this);
        this.menuIllustrator = new MenuIllustrator();
        this.menuFactory = new MenuFactory(this);

        this.userCache.loadOnline();
    }

    public void unload() {

    }

    public void configRelations() {
        this.getConfigStore()
                .config("settings", Path::resolve, true);
    }

    @Override
    public void onEnable() {
        this.configRelations();
        this.load();
    }

    @Override
    public void onDisable() {
        this.unload();
    }

    public void reload() {
        HandlerList.unregisterAll(this);
        this.getConfigStore().reloadReloadableConfigs();
        this.unload();
        this.load();
        System.gc();
    }

    public static PunishmentApi getApi() {
        return api;
    }

    public Config getConfig(String name) {
        return this.getConfigStore().getConfig(name);
    }

    public UserCache getUserCache() {
        return this.userCache;
    }

    public ActionManager getActionManager() {
        return this.actionManager;
    }

    public MenuIllustrator getMenuIllustrator() {
        return this.menuIllustrator;
    }

    public MenuFactory getMenuFactory() {
        return this.menuFactory;
    }
}
