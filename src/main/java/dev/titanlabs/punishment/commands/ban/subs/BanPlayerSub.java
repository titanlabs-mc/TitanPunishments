package dev.titanlabs.punishment.commands.ban.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class BanPlayerSub extends SubCommand<CommandSender> {
    private final Lang lang;

    public BanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.lang = plugin.getLang();

        this.addArgument(User.class, "player");
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
            UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : UUID.fromString("CONSOLE");
            UUID subjectUniqueId = target.getUuid();
            target.ban(new Ban(executorUniqueId, subjectUniqueId));

            this.lang.get(preBanned ? "banned-player-permanent-overwrite" : "banned-player-permanent", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);

            // Kick the player
            return;
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}

