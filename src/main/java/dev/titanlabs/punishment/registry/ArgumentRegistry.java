package dev.titanlabs.punishment.registry;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.CommandBase;
import me.hyfe.simplespigot.registry.Registry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Optional;

public class ArgumentRegistry implements Registry {
    private final CommandBase commandBase;
    private final IpCache ipCache;
    private final UserCache userCache;

    public ArgumentRegistry(PunishmentPlugin plugin) {
        this.commandBase = plugin.getCommandBase();
        this.ipCache = plugin.getIpCache();
        this.userCache = plugin.getUserCache();
    }

    @Override
    public void register() {
        this.commandBase.registerArgumentType(User.class, input -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(input);
            System.out.println(this.userCache.getSync(player.getUniqueId()));
            System.out.println(player.getName());
            System.out.println(player.hasPlayedBefore());
            return !player.hasPlayedBefore() ? Optional.empty() : this.userCache.getSync(player.getUniqueId());
        }).registerArgumentType(IpAddress.class, this.ipCache::getSync);
    }
}
