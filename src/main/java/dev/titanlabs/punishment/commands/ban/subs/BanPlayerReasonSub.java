package dev.titanlabs.punishment.commands.ban.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import me.hyfe.simplespigot.text.Text;
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
        this.addArgument(String.class, "reason");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalTarget = this.parseArgument(strings, 0);
        if (optionalTarget.isPresent()) {
            User target = optionalTarget.get();
            boolean preBanned = target.isBanned();
            if (preBanned && !sender.hasPermission("titanpunish.ban.override")) {
                this.lang.get("ban-failed-overwrite", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
                return;
            }


            String reason = String.join(" ", this.getEnd(strings));
            boolean silent = reason.endsWith("-s");

            UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : UUID.fromString("CONSOLE");
            UUID subjectUniqueId = target.getUuid();
            target.ban(new Ban(executorUniqueId, subjectUniqueId, reason));

            String finalReason = silent ? reason.substring(0, reason.length() - 2) : reason;
            if (silent) {
                Text.sendMessage(sender, this.lang.get("silent-prefix").compatibleString()
                        .concat(this.lang.get(preBanned ? "banned-player-permanent-overwrite-reason" : "banned-player-permanent-reason", replacer -> replacer
                                .set("player", target.getPlayer().getName())
                                .set("reason", finalReason)).compatibleString()));
                return;
            }
            this.lang.get(preBanned ? "banned-player-permanent-overwrite-reason" : "banned-player-permanent-reason", replacer -> replacer
                    .set("player", target.getPlayer().getName())
                    .set("reason", finalReason)).to(sender);
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
