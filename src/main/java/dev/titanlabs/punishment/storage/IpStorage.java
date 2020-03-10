package dev.titanlabs.punishment.storage;

import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.objects.user.IpAddress;
import me.hyfe.simplespigot.json.TypeTokens;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

import java.util.Set;
import java.util.UUID;

public class IpStorage extends Storage<IpAddress> {

    public IpStorage(Punishment plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("ips")));
    }

    @Override
    public Serializer<IpAddress> serializer() {
        return (ipAddress, json, gson) -> {
            json.addProperty("address", ipAddress.getAddress());
            json.addProperty("uuids", gson.toJson(ipAddress.getUniqueIds()));
            return json;
        };
    }

    @Override
    public Deserializer<IpAddress> deserializer() {
        return (json, gson) -> {
            String address = json.get("address").getAsString();
            Set<UUID> uniqueIds = gson.fromJson(json.get("uuids"), TypeTokens.findType());
            return new IpAddress(address, uniqueIds);
        };
    }
}
