package dev.titanlabs.punishment.cache;

import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.storage.IpStorage;
import me.hyfe.simplespigot.cache.FutureCache;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class IpCache extends FutureCache<String, IpAddress> {
    private final IpStorage storage;

    public IpCache(Punishment plugin) {
        super(plugin);
        this.storage = plugin.getIpStorage();
    }

    public CompletableFuture<IpAddress> get(Player player) {
        return this.load(player);
    }

    public CompletableFuture<IpAddress> load(Player player) {
        String address = player.getAddress().getAddress().getHostAddress();
        return this.get(address).thenApply(optionalIpAddress -> {
            if (!optionalIpAddress.isPresent()) {
                IpAddress ipAddress = this.storage.load(address);
                if (ipAddress == null) {
                    return this.set(address, new IpAddress(address, player.getUniqueId()));
                }
                return this.set(address, ipAddress);
            }
            return optionalIpAddress.get();
        });
    }

    public void unload(IpAddress ipAddress) {
        this.storage.save(ipAddress.getAddress(), ipAddress);
        this.invalidate(ipAddress.getAddress());
    }
}
