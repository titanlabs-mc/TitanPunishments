package dev.titanlabs.punishment.cache;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.IpStorage;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.cache.FutureCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserCache extends FutureCache<UUID, User> {
    private final UserStorage storage;
    private final IpStorage ipStorage;

    public UserCache(Punishment plugin) {
        super(plugin);
        this.storage = plugin.getUserStorage();
        this.ipStorage = plugin.getIpStorage();
    }

    public CompletableFuture<User> load(UUID uuid) {
        return this.get(uuid).thenApply(optionalUser -> {
            if (!optionalUser.isPresent()) {
                User user = this.storage.load(FastUUID.toString(uuid));
                if (user == null) {
                    Player player = Bukkit.getPlayer(uuid);
                    return this.set(uuid, new User(uuid, this.ipStorage.load(player.getAddress().getAddress().getHostAddress())));
                }
                return this.set(uuid, user);
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
}
