package dev.titanlabs.punishment.storage;

import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.objects.user.IpAddress;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

public class IpStorage extends Storage<IpAddress> {

    public IpStorage(Punishment plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("ips")));
    }

    @Override
    public Serializer<IpAddress> serializer() {
        return null;
    }

    @Override
    public Deserializer<IpAddress> deserializer() {
        return null;
    }
}
