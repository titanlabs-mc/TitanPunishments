package dev.titanlabs.punishment.commands.ban.subs;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanPlayerSub extends SubCommand<CommandSender> {
    private final PunishmentPlugin plugin;
    private final UserCache userCache;
    private final Lang lang;

    public BanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.plugin = plugin;
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(OfflinePlayer.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        OfflinePlayer targetPlayer = this.parseArgument(strings, 0);
        this.userCache.modifyUser(targetPlayer.getUniqueId(), target -> {
            if (target == null) {
                this.lang.get("could-not-find-user").to(sender);
                return;
            }
            boolean preBanned = target.isBanned();
            if (preBanned) {
                if (!sender.hasPermission("titanpunish.ban.override")) {
                    this.lang.get("ban-overwrite-fail", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
                    return;
                }
                target.getActiveBan().setEndReason(PunishmentEndReason.MANUAL);
                this.lang.get("ban-overwritten-message", replacer -> replacer.set("player", targetPlayer.getName())).to(sender);
            }
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
                UUID subjectUniqueId = target.getUuid();
                target.ban(new Ban(executorUniqueId, subjectUniqueId));
                if (targetPlayer.getPlayer() == null) {
                    return;
                }
                if (targetPlayer.isOnline()) {
                    targetPlayer.getPlayer().kickPlayer(this.lang.get("ban.no-reason.kick-message").compatibleString());
                }
            });
            this.lang.get("ban.no-reason.executor-message", replacer -> replacer
                    .set("player", targetPlayer.getName())).to(sender);
        });
    }
}

