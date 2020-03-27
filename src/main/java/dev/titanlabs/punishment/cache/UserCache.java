package dev.titanlabs.punishment.cache;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.cache.FutureCache;

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
                System.out.println("Man: ".concat(String.valueOf(user)));
                if (user == null) {
                    return this.set(uuid, new User(uuid));
                } else {
                    return this.set(uuid, user);
                }
            }
            System.out.println("ALREADY LOADED");
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
            System.out.println("1");
            if (optionalUser.isPresent()) {
                System.out.println("IS PRESENT");
                consumer.accept(optionalUser.get());
                return;
            }
            System.out.println("NOT PRESENT");
            User user = this.storage.load(FastUUID.toString(uuid));
            System.out.println("User: ".concat(String.valueOf(user)));
            consumer.accept(user);
            this.storage.save(FastUUID.toString(uuid), user);
        });
    }
}
