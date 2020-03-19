package dev.titanlabs.punishment.storage;

import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.StrippedUser;
import dev.titanlabs.punishment.storage.adapters.BanAdapter;
import me.hyfe.simplespigot.plugin.SimplePlugin;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

public class StrippedUserStorage extends Storage<StrippedUser> {

    public StrippedUserStorage(SimplePlugin plugin) {
        super(plugin, backend -> backend.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("users")));

        this.addAdapter(Ban.class, new BanAdapter());
    }

    @Override
    public Serializer<StrippedUser> serializer() {
        return null;
    }

    @Override
    public Deserializer<StrippedUser> deserializer() {
        return (json, gson) -> {
            Ban lastKnownActiveBan = gson.fromJson(json.get("lastKnownActiveBan").getAsString(), Ban.class);
            System.out.println("AA: ".concat(String.valueOf(lastKnownActiveBan)));
            return new StrippedUser(lastKnownActiveBan);
        };
    }
}
