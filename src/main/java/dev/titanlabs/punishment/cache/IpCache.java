package dev.titanlabs.punishment.cache;

import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.storage.IpStorage;
import me.hyfe.simplespigot.cache.FutureCache;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IpCache extends FutureCache<String, IpAddress> {
    private final IpStorage storage;

    public IpCache(PunishmentPlugin plugin) {
        super(plugin);
        this.storage = plugin.getIpStorage();
    }

    public CompletableFuture<IpAddress> get(Player player) {
        return this.load(player);
    }

    public CompletableFuture<IpAddress> load(Player player) {
        return this.load(player.getAddress().getAddress().getHostAddress());
    }

    public CompletableFuture<IpAddress> load(String address) {
        return this.get(address).thenApply(optionalIpAddress -> {
            if (!optionalIpAddress.isPresent()) {
                IpAddress ipAddress = this.storage.load(address);
                if (ipAddress == null) {
                    return this.set(address, new IpAddress(address));
                }
                return this.set(address, ipAddress);
            }
            return optionalIpAddress.get();
        });
    }

    public Set<IpAddress> loadIps(List<String> addresses) {
        Set<IpAddress> result = Sets.newHashSet();
        for (String address : addresses) {
            this.getSync(address).ifPresent(result::add);
        }
        return result;
    }

    public void unload(IpAddress ipAddress) {
        this.storage.save(ipAddress.getAddress(), ipAddress);
        this.invalidate(ipAddress.getAddress());
    }
}
