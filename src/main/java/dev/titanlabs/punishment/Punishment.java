package dev.titanlabs.punishment;

import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.cache.listener.ConnectionListener;
import dev.titanlabs.punishment.commands.ban.BanCommand;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.storage.IpStorage;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.plugin.SpigotPlugin;

import java.nio.file.Path;

public final class Punishment extends SpigotPlugin {
    private Lang lang;
    private UserStorage userStorage;
    private UserCache userCache;
    private IpStorage ipStorage;

    @Override
    public void onEnable() {
        this.registerConfigs();

        this.userStorage = new UserStorage(this);
        this.ipStorage = new IpStorage(this);
        this.userCache = new UserCache(this);

        this.registerCommands(
                new BanCommand(this, "ban", "titanpunish.ban", true)
        );

        this.registerListeners(
                new ConnectionListener(this)
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Lang getLang() {
        return this.lang;
    }

    public UserStorage getUserStorage() {
        return this.userStorage;
    }

    public UserCache getUserCache() {
        return this.userCache;
    }

    public IpStorage getIpStorage() {
        return this.ipStorage;
    }

    public Config getConfig(String name) {
        return this.getConfigStore().getConfig(name);
    }

    public void registerConfigs() {
        this.getConfigStore()
                .config("settings", Path::resolve, true)
                .common("storageMethod", "settings", config -> config.string("storage.storage-method"));
        this.lang = new Lang(this);
    }
}
