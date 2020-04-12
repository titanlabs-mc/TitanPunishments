package dev.titanlabs.punishment.api;

import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PunishmentApi {
    private final PunishmentPlugin plugin;
    private final UserCache userCache;
    private final IpCache ipCache;
    private final Lang lang;

    public PunishmentApi(PunishmentPlugin plugin) {
        this.plugin = plugin;
        this.userCache = plugin.getUserCache();
        this.ipCache = plugin.getIpCache();
        this.lang = plugin.getLang();
    }

    public CompletableFuture<Optional<User>> getUser(UUID uuid) {
        return this.userCache.get(uuid);
    }

    public CompletableFuture<Optional<IpAddress>> getIpAddress(String address) {
        return this.ipCache.get(address);
    }

    public void banUser(User user, Ban ban, CommandSender executor, boolean silent) {
        user.ban(ban);
        String basePath = (ban.isTemporary() ? "tempban." : "ban.").concat(ban.getReason() == null ? "no-reason." : "reason.");
        String broadcastPath = basePath.concat("broadcast-message");
        this.plugin.runSync(() -> {
            if (user.getPlayer().isOnline()) {
                String path = basePath.concat("kick-message");
                user.getPlayer().kickPlayer(this.lang.get(path).compatibleString());
            }
        });
        this.lang.get(basePath.concat("executor-message")).to(executor);
        if (!silent) {
            this.lang.get(broadcastPath).to(Sets.newHashSet(Bukkit.getOnlinePlayers()));
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("titanpunish.staff")) {
                    Text.sendMessage(player, this.lang.get("silent-prefix").compatibleString().concat(this.lang.get(broadcastPath).compatibleString()));
                }
            }
        }
    }
}
