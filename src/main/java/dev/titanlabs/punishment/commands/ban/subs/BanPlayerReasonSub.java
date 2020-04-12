package dev.titanlabs.punishment.commands.ban.subs;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.service.punishment.PunishmentUtils;
import me.hyfe.simplespigot.command.command.SubCommand;
import me.hyfe.simplespigot.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class BanPlayerReasonSub extends SubCommand<CommandSender> {
    private final Lang lang;

    public BanPlayerReasonSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole, true);
        this.lang = plugin.getLang();

        this.addArgument(User.class, "player");
        this.addArgument(String.class, "reason-placer");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalTarget = this.parseArgument(strings, 0);
        if (optionalTarget.isPresent()) {
            User target = optionalTarget.get();
            Player targetPlayer = target.getPlayer();
            boolean preBanned = target.isBanned();
            if (preBanned) {
                if (!sender.hasPermission("titanpunish.ban.override")) {
                    this.lang.get("ban-overwrite-fail", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
                    return;
                }
                target.getActiveBan().setEndReason(PunishmentEndReason.MANUAL);
            }

            String reason = String.join(" ", this.getEnd(strings));
            boolean silent = PunishmentUtils.silent(reason);
            String finalReason = silent ? PunishmentUtils.fixSilent(reason) : reason;

            UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
            UUID subjectUniqueId = target.getUuid();
            target.ban(new Ban(executorUniqueId, subjectUniqueId, reason));

            Bukkit.getScheduler().runTask(this.plugin, () -> {
                if (targetPlayer.isOnline() && targetPlayer.getPlayer() != null) {
                    targetPlayer.getPlayer().kickPlayer(this.lang.get("ban.reason.kick-message", replacer -> replacer
                            .set("player", targetPlayer.getName())
                            .set("reason", finalReason)).compatibleString());
                }
            });
            if (silent) {
                Text.sendMessage(sender, this.lang.get("silent-prefix").compatibleString()
                        .concat(this.lang.get("ban.reason.executor-message", replacer -> replacer
                                .set("player", target.getPlayer().getName())
                                .set("reason", finalReason)).compatibleString()));
                return;
            }
            this.lang.get("ban-overwritten-message").to(sender);
            return;
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
