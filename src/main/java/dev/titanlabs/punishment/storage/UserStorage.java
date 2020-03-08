package dev.titanlabs.punishment.storage;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

import java.util.UUID;

public class UserStorage extends Storage<User> {

    public UserStorage(Punishment plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("users")));
    }

    @Override
    public Serializer<User> serializer() {
        return null;
    }

    @Override
    public Deserializer<User> deserializer() {
        return(json, gson) -> {
            UUID uuid = FastUUID.parseUUID(json.get("uuid").getAsString());
            return new User(uuid);
        };
    }
}
