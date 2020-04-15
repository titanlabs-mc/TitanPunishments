package dev.titanlabs.punishment.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.storage.OfflineUserStorage;
import me.hyfe.simplespigot.service.tuple.ImmutablePair;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OfflineUserCache {
    private final Cache<String, UUID> nameCache;
    private final OfflineUserStorage offlineUserStorage;

    public OfflineUserCache(PunishmentPlugin plugin) {
        this.nameCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
        this.offlineUserStorage = plugin.getOfflineUserStorage();
    }

    public Optional<UUID> get(String name) {
        UUID uuid = this.nameCache.getIfPresent(name);
        if (uuid == null) {
            ImmutablePair<String, UUID> userInfo = this.offlineUserStorage.load(name);
            if (userInfo == null) {
                return Optional.empty();
            }
            uuid = userInfo.getValue();
            this.nameCache.put(name, userInfo.getValue());
        }
        return Optional.of(uuid);
    }

    public void add(String name, UUID uuid) {
        this.offlineUserStorage.save(name, new ImmutablePair<>(name, uuid));
    }
}
