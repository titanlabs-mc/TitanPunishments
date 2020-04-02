package dev.titanlabs.punishment.commands.kick.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class KickPlayerSub extends SubCommand<CommandSender> {
    private final UserCache userCache;
    private final Lang lang;

    public KickPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(User.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalUser = this.parseArgument(strings, 0);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPlayer().isOnline()) {
                UUID executor = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
                user.kick(new Kick(executor, user.getPlayer().getUniqueId()));
                user.getPlayer().kickPlayer(this.lang.get("kick.no-reason.kick-message").compatibleString());
                this.lang.get("kick.no-reason.executor-message").to(sender);
                return;
            }
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
