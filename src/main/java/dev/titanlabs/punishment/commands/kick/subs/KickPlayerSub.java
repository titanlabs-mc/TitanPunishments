package dev.titanlabs.punishment.commands.kick.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class KickPlayerSub extends SubCommand<CommandSender> {
    private final PunishmentApi punishmentApi;
    private final Lang lang;

    public KickPlayerSub(PunishmentPlugin plugin, boolean isConsole) {
        super(plugin, "", isConsole);
        this.punishmentApi = plugin.getLocalPunishmentApi();
        this.lang = plugin.getLang();

        this.inheritPermission();
        this.addArgument(User.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Optional<User> optionalTarget = this.parseArgument(strings, 0);
        if (optionalTarget.isPresent()) {
            User target = optionalTarget.get();
            if (target.getPlayer().isOnline()) {
                UUID executorUuid = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
                this.punishmentApi.kickUser(target, new Kick(executorUuid, target.getPlayer().getUniqueId()), sender, false);
                return;
            }
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
