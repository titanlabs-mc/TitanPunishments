package dev.titanlabs.punishment;

import me.hyfe.simplespigot.plugin.SpigotPlugin;

public abstract class PunishmentPlugin extends SpigotPlugin {

    public abstract void load();

    public abstract void unload();

    @Override
    public void onEnable() {
        this.load();
    }

    @Override
    public void onDisable() {
        this.unload();
    }

    public void reload() {

    }
}
