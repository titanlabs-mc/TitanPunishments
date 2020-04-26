package dev.titanlabs.punishment;

import dev.titanlabs.punishment.api.implementation.PunishmentApiImpl;

import java.nio.file.Path;

public class Source extends PunishmentPlugin {

    @Override
    public void load() {
        Source.api = new PunishmentApiImpl();
    }

    @Override
    public void unload() {

    }

    @Override
    public void configRelations() {
        this.getConfigStore()
                .config("settings", Path::resolve, true);
    }
}
