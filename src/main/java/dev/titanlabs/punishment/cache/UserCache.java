package dev.titanlabs.punishment.cache;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.cache.FutureCache;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserCache extends FutureCache<UUID, User> {
    private final UserStorage storage;

    public UserCache(PunishmentPlugin plugin) {
        super(plugin);
        this.storage = plugin.getUserStorage();
    }

    public CompletableFuture<User> load(UUID uuid) {
        return this.get(uuid).thenApply(optionalUser -> {
            if (!optionalUser.isPresent()) {
                User user = this.storage.load(FastUUID.toString(uuid));
                if (user == null) {
                    return this.set(uuid, new User(uuid));
                } else {
                    return this.set(uuid, user);
                }
            }
            return optionalUser.get();
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    public void unload(UUID uuid) {
        this.get(uuid).thenAccept(optionalUser -> {
            optionalUser.ifPresent(user -> this.storage.save(uuid.toString(), user));
            this.invalidate(uuid);
        });
    }
}
