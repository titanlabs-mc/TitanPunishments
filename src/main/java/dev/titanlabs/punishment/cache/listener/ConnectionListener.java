package dev.titanlabs.punishment.cache.listener;

import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.CompletableFuture;

public class ConnectionListener implements Listener {
    private final UserCache userCache;
    private final IpCache ipCache;

    public ConnectionListener(Punishment plugin) {
        this.userCache = plugin.getUserCache();
        this.ipCache = plugin.getIpCache();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CompletableFuture<User> completableUser = this.userCache.load(player.getUniqueId());
        CompletableFuture<IpAddress> completableIpAddress = this.ipCache.get(player);
        completableUser.thenAccept(user -> completableIpAddress.thenAccept(user::setIpAddress));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.userCache.unload(player.getUniqueId());
        this.ipCache.get(player.getAddress().getAddress().getHostAddress()).thenAccept(optionalIpAddress -> {
            if (optionalIpAddress.isPresent()) {
                IpAddress ipAddress = optionalIpAddress.get();
                ipAddress.registerOffline(player.getUniqueId());
                if (!ipAddress.hasOnlinePlayers()) {
                    this.ipCache.unload(ipAddress);
                }
            }
        });}
}
