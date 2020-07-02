package dev.titanlabs.punishment;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.api.implementation.PunishmentApiImpl;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.manager.ActionManager;
import dev.titanlabs.punishment.manager.TrackManager;
import dev.titanlabs.punishment.menus.MenuFactory;
import dev.titanlabs.punishment.menus.service.MenuIllustrator;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.plugin.SpigotPlugin;
import me.hyfe.simplespigot.storage.StorageSettings;
import org.bukkit.event.HandlerList;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PunishmentPlugin extends SpigotPlugin {
    protected static PunishmentApi api;
    private PunishmentApi localApi;
    private UserStorage userStorage;
    private UserCache userCache;
    private ActionManager actionManager;
    private TrackManager trackManager;
    private MenuIllustrator menuIllustrator;
    private MenuFactory menuFactory;
    private Cache<String, Map<Integer, Set<Action>>> actionCache;

    public void load() {
        this.setStorageSettings();

        this.userStorage = new UserStorage(this);
        this.userCache = new UserCache(this);
        this.actionManager = new ActionManager(this);
        this.trackManager = new TrackManager(this);

        this.localApi = new PunishmentApiImpl();
        api = this.localApi;

        this.menuIllustrator = new MenuIllustrator();
        this.menuFactory = new MenuFactory(this);

        this.actionCache = CacheBuilder.newBuilder().expireAfterAccess(20, TimeUnit.SECONDS).build();

        this.userCache.loadOnline();
    }

    public void unload() {

    }

    public void configRelations() {
        this.getConfigStore()
                .config("settings", Path::resolve, true)
                .common("storage-type", "settings", config -> config.string("storage-options.storage-method"));
    }

    private void setStorageSettings() {
        Config config = this.getConfig("settings");
        StorageSettings storageSettings = this.getStorageSettings();
        storageSettings.setAddress(config.string("storage-options.address"));
        storageSettings.setDatabase(config.string("storage-options.database"));
        storageSettings.setPrefix(config.string("storage-options.prefix"));
        storageSettings.setUsername(config.string("storage-options.username"));
        storageSettings.setPassword(config.string("storage-options.password"));
        storageSettings.setConnectionTimeout(config.integer("storage-options.pool-settings.connection-timeout"));
        storageSettings.setMaximumLifetime(config.integer("storage-options.pool-settings.maximum-lifetime"));
        storageSettings.setMaximumPoolSize(config.integer("storage-options.pool-settings.maximum-pool-size"));
        storageSettings.setMinimumIdle(config.integer("storage-options.pool-settings.minimum-idle"));
        storageSettings.setProperties(Maps.newHashMap());
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

    public PunishmentApi getLocalApi() {
        return this.localApi;
    }

    public static PunishmentApi getApi() {
        return api;
    }

    public Config getConfig(String name) {
        return this.getConfigStore().getConfig(name);
    }

    public UserStorage getUserStorage() {
        return this.userStorage;
    }

    public UserCache getUserCache() {
        return this.userCache;
    }

    public ActionManager getActionManager() {
        return this.actionManager;
    }

    public TrackManager getTrackManager() {
        return this.trackManager;
    }

    public MenuIllustrator getMenuIllustrator() {
        return this.menuIllustrator;
    }

    public MenuFactory getMenuFactory() {
        return this.menuFactory;
    }

    public Cache<String, Map<Integer, Set<Action>>> getActionCache() {
        return this.actionCache;
    }
}
