package dev.titanlabs.punishment;

import dev.titanlabs.punishment.api.PunishmentApi;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.plugin.SpigotPlugin;
import org.bukkit.event.HandlerList;

public abstract class PunishmentPlugin extends SpigotPlugin {
    protected static PunishmentApi api;

    public abstract void load();

    public abstract void unload();

    public abstract void configRelations();

    @Override
    public void onEnable() {
        this.configRelations();
        this.load();
    }

    @Override
    public void onDisable() {
        this.unload();
    }

    public static PunishmentApi getApi() {
        return api;
    }

    public Config getConfig(String name) {
        return this.getConfigStore().getConfig(name);
    }

    public void reload() {
        HandlerList.unregisterAll(this);
        this.getConfigStore().reloadReloadableConfigs();
        this.unload();
        this.load();
        System.gc();
    }
}
