package dev.titanlabs.punishment.cache;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.UserStorage;
import me.hyfe.simplespigot.cache.FutureCache;
import me.hyfe.simplespigot.uuid.FastUUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserCache extends FutureCache<UUID, User> {
    private final UserStorage storage;

    public UserCache(PunishmentPlugin plugin) {
        super(plugin);
        this.storage = plugin.getUserStorage();
    }

    public CompletableFuture<User> load(UUID uuid) {
        return this.get(uuid).thenApply(maybeUser -> {
            if (!maybeUser.isPresent()) {
                User user = this.storage.load(FastUUID.toString(uuid));
                return this.set(uuid, user == null ? new User(uuid) : user);
            }
            return maybeUser.get();
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    public void asyncModifyAll(Consumer<User> consumer) {
        this.plugin.runAsync(() -> {
            for (User user : this.getSubCache().asMap().values()) {
                consumer.accept(user);
            }
            for (User user : this.storage.loadAll()) {
                if (!this.getSubCache().asMap().containsKey(user.getUuid())) {
                    consumer.accept(user);
                    this.storage.save(user.getUuid().toString(), user);
                }
            }
        });
    }

    public void unload(UUID uuid, boolean invalidate) {
        this.get(uuid).thenAccept(maybeUser -> {
            maybeUser.ifPresent(user -> this.storage.save(FastUUID.toString(user.getUuid()), user));
            if (invalidate) {
                this.invalidate(uuid);
            }
        });
    }

    public void loadOnline() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.load(player.getUniqueId());
        }
    }

    public void flush() {
        for (User user : this.getSubCache().asMap().values()) {
            this.storage.save(FastUUID.toString(user.getUuid()), user);
        }
    }
}
