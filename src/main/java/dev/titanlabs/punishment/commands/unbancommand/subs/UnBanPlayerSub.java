package dev.titanlabs.punishment.commands.unbancommand.subs;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class UnBanPlayerSub extends SubCommand<CommandSender> {
    private final Lang lang;

    public UnBanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.lang = plugin.getLang();

        this.addArgument(User.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalUser = this.parseArgument(strings, 0);
        if (optionalUser.isPresent()) {
            User target = optionalUser.get();
            if (!target.isBanned()) {
                this.lang.get("target-not-banned", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
                return;
            }
            target.unban(PunishmentEndReason.MANUAL);
            this.lang.get("unbanned-message", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
            return;
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
