package dev.titanlabs.punishment.api;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.punishments.Punishment;
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
        Player targetPlayer = Bukkit.getPlayer(user.getName());
        this.plugin.runSync(() -> {
            if (targetPlayer != null && targetPlayer.isOnline()) {
                String path = basePath.concat("kick-message");
                targetPlayer.kickPlayer(this.lang.get(path, replacer -> replacer
                        .set("reason", ban.getReason())
                        .set("executor", executor.getName())
                        .set("player", user.getName())).compatibleString());
            }
        });
        this.sendOtherMessages(basePath, user, executor, ban, silent);
    }

    public void kickUser(User user, Kick kick, CommandSender executor, boolean silent) {
        user.kick(kick);
        String basePath = "kick.".concat(kick.getReason() == null ? "no-reason." : "reason.");
        Player targetPlayer = Bukkit.getPlayer(user.getName());
        this.plugin.runSync(() -> targetPlayer.kickPlayer(this.lang.get(basePath.concat("kick-message"), replacer -> replacer
                .set("reason", kick.getReason())
                .set("executor", executor.getName())
                .set("player", targetPlayer.getName())).compatibleString()));
        this.sendOtherMessages(basePath, user, executor, kick, silent);
    }

    public void unban(User user, CommandSender executor, PunishmentEndReason endReason, boolean silent) {
        System.out.println("A");
        user.unban(endReason);
        System.out.println("B");
        String basePath = "unban.";
        System.out.println("C");
        this.sendOtherMessages(basePath, user, executor, null, silent);
        System.out.println("D");
    }

    private void sendOtherMessages(String basePath, User user, CommandSender executor, Punishment punishment, boolean silent) {
        this.plugin.runSync(() -> {
            String broadcastMessage = this.lang.get(basePath.concat("broadcast-message"), replacer -> replacer
                    .set("executor", executor.getName())
                    .set("player", user.getName())
                    .set("reason", punishment.getReason())).compatibleString();

            this.lang.get(basePath.concat("executor-message"), replacer -> replacer
                    .set("executor", executor.getName())
                    .set("player", user.getName())
                    .set("reason", punishment.getReason())).to(executor);
            if (!silent) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Text.sendMessage(player, broadcastMessage);
                }
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("titanpunish.staff")) {
                        Text.sendMessage(player, this.lang.get("silent-prefix").compatibleString().concat(broadcastMessage));
                    }
                }
            }
        });
    }
}
