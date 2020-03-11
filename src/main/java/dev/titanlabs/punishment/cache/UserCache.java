package dev.titanlabs.punishment.cache;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.cache.FutureCache;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserCache extends FutureCache<UUID, User> {
    private final UserStorage userStorage;

    public UserCache(PunishmentPlugin plugin) {
        super(plugin);
        this.userStorage = plugin.getUserStorage();
    }

    public CompletableFuture<User> load(UUID uuid) {
        return this.get(uuid).thenApply(optionalUser -> {
            Bukkit.broadcastMessage("a");
            if (!optionalUser.isPresent()) {
                Bukkit.broadcastMessage("b");
                User user = this.userStorage.load(FastUUID.toString(uuid));
                if (user == null) {
                    return this.set(uuid, new User(uuid));
                }
                return this.set(uuid, user);
            }
            return optionalUser.get();
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public void unload(UUID uuid) {
        this.get(uuid).thenAccept(optionalUser -> {
            optionalUser.ifPresent(user -> this.userStorage.save(uuid.toString(), user));
            this.invalidate(uuid);
        });
    }
}
