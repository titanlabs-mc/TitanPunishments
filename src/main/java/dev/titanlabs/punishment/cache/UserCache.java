package dev.titanlabs.punishment.cache;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.cache.FutureCache;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserCache extends FutureCache<UUID, User> {
    private final PunishmentPlugin plugin;
    private final UserStorage storage;

    public UserCache(PunishmentPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
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


    public CompletableFuture<User> getWithoutLoad(UUID uuid) {
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
        });
    }

    public void unload(UUID uuid) {
        this.get(uuid).thenAccept(optionalUser -> {
            optionalUser.ifPresent(user -> this.storage.save(uuid.toString(), user));
            this.invalidate(uuid);
        });
    }

    public void modifyUser(UUID uuid, Consumer<User> consumer) {
        this.get(uuid).thenAccept(optionalUser -> {
            if (optionalUser.isPresent()) {
                consumer.accept(optionalUser.get());
                return;
            }
            User user = this.storage.load(FastUUID.toString(uuid));
            consumer.accept(user);
            this.storage.save(FastUUID.toString(uuid), user);
        });
    }

    public void modifyUsers(Set<UUID> uuids, Consumer<User> consumer) {
        for (UUID uuid : uuids) {
            this.modifyUser(uuid, consumer);
        }
    }
}
