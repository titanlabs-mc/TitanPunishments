package dev.titanlabs.punishment;

import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.cache.listener.ConnectionListener;
import dev.titanlabs.punishment.commands.ban.BanCommand;
import dev.titanlabs.punishment.commands.titanpunish.TitanPunishCommand;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.registry.ArgumentRegistry;
import dev.titanlabs.punishment.storage.IpStorage;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.plugin.SpigotPlugin;

import java.nio.file.Path;

public final class Punishment extends SpigotPlugin {
    private Lang lang;
    private UserStorage userStorage;
    private IpStorage ipStorage;
    private UserCache userCache;
    private IpCache ipCache;

    @Override
    public void onEnable() {
        this.registerConfigs();

        this.userStorage = new UserStorage(this);
        this.ipStorage = new IpStorage(this);
        this.ipCache = new IpCache(this);
        this.userCache = new UserCache(this);

        this.registerCommands(
                new BanCommand(this, "ban", "titanpunish.ban", true),
                new TitanPunishCommand(this, "titanpunish", "", true)
        );

        this.registerListeners(
                new ConnectionListener(this)
        );

        this.registerRegistries(
                new ArgumentRegistry(this)
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

    public IpStorage getIpStorage() {
        return this.ipStorage;
    }

    public UserCache getUserCache() {
        return this.userCache;
    }

    public IpCache getIpCache() {
        return this.ipCache;
    }

    public Config getConfig(String name) {
        return this.getConfigStore().getConfig(name);
    }

    public void registerConfigs() {
        this.getConfigStore()
                .config("settings", Path::resolve, true)
                .config("lang", Path::resolve, true)
                .common("storageMethod", "settings", config -> config.string("storage.storage-method"));
        this.lang = new Lang(this);
    }
}
