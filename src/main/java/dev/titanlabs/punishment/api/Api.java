package dev.titanlabs.punishment.api;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Api {
    private final UserCache userCache;
    private final IpCache ipCache;

    public Api(PunishmentPlugin plugin) {
        this.userCache = plugin.getUserCache();
        this.ipCache = plugin.getIpCache();
    }

    public CompletableFuture<Optional<User>> getUser(UUID uuid) {
        return this.userCache.get(uuid);
    }

    public CompletableFuture<Optional<IpAddress>> getIpAddress(String address) {
        return this.ipCache.get(address);
    }

    public void banUser(User user, Ban ban) {
        user.ban(ban);

    }
}
