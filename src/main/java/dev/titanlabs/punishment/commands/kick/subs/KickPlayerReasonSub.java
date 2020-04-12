package dev.titanlabs.punishment.commands.kick.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.service.punishment.PunishmentUtils;
import me.hyfe.simplespigot.command.command.SubCommand;
import me.hyfe.simplespigot.text.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class KickPlayerReasonSub extends SubCommand<CommandSender> {
    private final Lang lang;

    public KickPlayerReasonSub(PunishmentPlugin plugin, boolean isConsole) {
        super(plugin, "", isConsole, true);
        this.lang = plugin.getLang();
        this.inheritPermission();

        this.addArgument(User.class, "player");
        this.addArgument(String.class, "reason-placer");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalTarget = this.parseArgument(strings, 0);
        if (optionalTarget.isPresent()) {
            User target = optionalTarget.get();
            if (target.getPlayer().isOnline()) {
                String reason = String.join(" ", this.getEnd(strings));
                boolean silent = PunishmentUtils.silent(reason);
                String finalReason = silent ? PunishmentUtils.fixSilent(reason) : reason;

                UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
                UUID targetUniqueId = target.getPlayer().getUniqueId();

                target.kick(new Kick(executorUniqueId, targetUniqueId, reason));
                target.getPlayer().kickPlayer(this.lang.get("kick.reason.kick-message", replacer -> replacer
                        .set("player", target.getPlayer().getName())
                        .set("reason", finalReason)).compatibleString());

                Text.sendMessage(sender, (silent ? this.lang.get("silent-prefix").compatibleString() : "").concat(this.lang.get("kick.reason.executor-message").compatibleString()));
                this.lang.get("kick.reason.executor-message").to(sender);
                return;
            }
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}