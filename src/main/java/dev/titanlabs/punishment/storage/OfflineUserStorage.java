package dev.titanlabs.punishment.storage;

import com.eatthepath.uuid.FastUUID;
import me.hyfe.simplespigot.plugin.SimplePlugin;
import me.hyfe.simplespigot.service.tuple.ImmutablePair;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

import java.util.UUID;

public class OfflineUserStorage extends Storage<ImmutablePair<String, UUID>> {

    public OfflineUserStorage(SimplePlugin plugin) {
        super(plugin, backend -> backend.create("json", path -> path.resolve("data"), "name-binds"));
    }

    @Override
    public Serializer<ImmutablePair<String, UUID>> serializer() {
        return (immutablePair, json, gson) -> {
            json.addProperty("username", immutablePair.getKey());
            json.addProperty("uuid", FastUUID.toString(immutablePair.getValue()));
            return json;
        };
    }

    @Override
    public Deserializer<ImmutablePair<String, UUID>> deserializer() {
        return (json, gson) -> {
            String name = json.get("username").getAsString();
            UUID uuid = FastUUID.parseUUID(json.get("uuid").getAsString());
            return new ImmutablePair<>(name, uuid);
        };
    }
}
