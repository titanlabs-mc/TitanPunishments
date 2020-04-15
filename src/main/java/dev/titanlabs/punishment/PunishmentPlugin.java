package dev.titanlabs.punishment;

import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.OfflineUserCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.cache.listener.ConnectionListener;
import dev.titanlabs.punishment.commands.ban.BanCommand;
import dev.titanlabs.punishment.commands.ipinfo.IpInfoCommand;
import dev.titanlabs.punishment.commands.kick.KickCommand;
import dev.titanlabs.punishment.commands.tempban.TempBanCommand;
import dev.titanlabs.punishment.commands.titanpunish.TitanPunishCommand;
import dev.titanlabs.punishment.commands.unban.UnBanCommand;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.listeners.ChatListener;
import dev.titanlabs.punishment.listeners.PlayerPreLoginListener;
import dev.titanlabs.punishment.registry.ArgumentRegistry;
import dev.titanlabs.punishment.service.Time;
import dev.titanlabs.punishment.storage.IpStorage;
import dev.titanlabs.punishment.storage.OfflineUserStorage;
import dev.titanlabs.punishment.storage.StrippedUserStorage;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.plugin.SpigotPlugin;

import java.nio.file.Path;

public final class PunishmentPlugin extends SpigotPlugin {
    private static PunishmentApi externalPunishmentApi;
    private Lang lang;
    private StrippedUserStorage strippedUserStorage;
    private UserStorage userStorage;
    private OfflineUserStorage offlineUserStorage;
    private OfflineUserCache offlineUserCache;
    private IpStorage ipStorage;
    private UserCache userCache;
    private IpCache ipCache;
    private PunishmentApi localPunishmentApi;

    public static PunishmentApi getExternalPunishmentApi() {
        return externalPunishmentApi;
    }

    @Override
    public void onEnable() {
        this.registerConfigs();

        this.strippedUserStorage = new StrippedUserStorage(this);
        this.ipStorage = new IpStorage(this);
        this.offlineUserStorage = new OfflineUserStorage(this);
        this.ipCache = new IpCache(this);
        this.userStorage = new UserStorage(this);
        this.userCache = new UserCache(this);
        this.offlineUserCache = new OfflineUserCache(this);

        this.localPunishmentApi = new PunishmentApi(this);
        externalPunishmentApi = this.localPunishmentApi;

        this.registerRegistries(
                new ArgumentRegistry(this)
        );

        this.registerCommands(
                new BanCommand(this, "ban", "titanpunish.ban", true),
                new IpInfoCommand(this, "ipinfo", "titanpunish.ipinfo", true),
                new KickCommand(this, "kick", "titanpunish.kick", true),
                new TempBanCommand(this, "tempban", "titanpunish.tempban", true),
                new TitanPunishCommand(this, "titanpunish", "", true),
                new UnBanCommand(this, "unban", "titanpunish.unban", true)
        );

        this.registerListeners(
                new ConnectionListener(this),
                new PlayerPreLoginListener(this),
                new ChatListener(this)
        );
        new Time(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Lang getLang() {
        return this.lang;
    }

    public StrippedUserStorage getStrippedUserStorage() {
        return this.strippedUserStorage;
    }

    public UserStorage getUserStorage() {
        return this.userStorage;
    }

    public OfflineUserStorage getOfflineUserStorage() {
        return this.offlineUserStorage;
    }

    public OfflineUserCache getOfflineUserCache() {
        return this.offlineUserCache;
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

    public PunishmentApi getLocalPunishmentApi() {
        return this.localPunishmentApi;
    }

    public static PunishmentApi getPunishmentApi() {
        return externalPunishmentApi;
    }
}
