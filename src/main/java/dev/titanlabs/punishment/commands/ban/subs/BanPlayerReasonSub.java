package dev.titanlabs.punishment.commands.ban.subs;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.service.punishment.PunishmentUtils;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class BanPlayerReasonSub extends SubCommand<CommandSender> {
    private final Lang lang;
    private final PunishmentApi punishmentApi;

    public BanPlayerReasonSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole, true);
        this.lang = plugin.getLang();
        this.punishmentApi = plugin.getLocalPunishmentApi();

        this.addArgument(User.class, "player");
        this.addArgument(String.class, "reason-placer");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalTarget = this.parseArgument(strings, 0);
        if (optionalTarget.isPresent()) {
            User target = optionalTarget.get();
            boolean preBanned = target.isBanned();
            if (preBanned) {
                if (!sender.hasPermission("titanpunish.ban.override")) {
                    this.lang.get("ban-overwrite-fail", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
                    return;
                }
                target.getActiveBan().setEndReason(PunishmentEndReason.MANUAL);
                this.lang.get("ban-overwritten-message", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
            }

            String reason = String.join(" ", this.getEnd(strings));
            boolean silent = PunishmentUtils.silent(reason);
            String finalReason = silent ? PunishmentUtils.fixSilent(reason) : reason;

            UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
            UUID subjectUniqueId = target.getUuid();
            this.punishmentApi.banUser(target, new Ban(executorUniqueId, subjectUniqueId, finalReason), sender, silent);
            return;
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
