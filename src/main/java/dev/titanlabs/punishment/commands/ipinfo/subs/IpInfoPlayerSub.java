package dev.titanlabs.punishment.commands.ipinfo.subs;

import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Set;
import java.util.UUID;

public class IpInfoPlayerSub extends SubCommand<CommandSender> {
    private final UserCache userCache;
    private final Lang lang;

    public IpInfoPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(OfflinePlayer.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        OfflinePlayer player = this.parseArgument(args, 0);
        this.userCache.get(player.getUniqueId()).thenAccept(optionalTarget -> {
            if (optionalTarget.isPresent()) {
                Set<User> associatedUsers = Sets.newHashSet();

                User target = optionalTarget.get();
                for (IpAddress ipAddress : target.getIpAddresses()) {
                    for (UUID uuid : ipAddress.getUniqueIds()) {
                        this.userCache.getSync(uuid).ifPresent(associatedUsers::add);
                    }
                }
                Set<User> bannedUsers = Sets.newHashSet();
                Set<User> mutedUsers = Sets.newHashSet();
                Set<User> onlineUsers = Sets.newHashSet();
                Set<User> offlineUsers = Sets.newHashSet();
                for (User user : associatedUsers) {
                    if (user.isBanned()) {
                        bannedUsers.add(user);
                    } else if (user.isMuted()) {
                        mutedUsers.add(user);
                    } else if (user.getPlayer().isOnline()) {
                        onlineUsers.add(user);
                    } else {
                        offlineUsers.add(user);
                    }
                }
                return;
            }
            this.lang.get("could-not-find-user").

                    to(sender);
        });
    }
}
