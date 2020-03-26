package dev.titanlabs.punishment.commands.ban.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanPlayerSub extends SubCommand<CommandSender> {
    private final UserCache userCache;
    private final Lang lang;

    public BanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(OfflinePlayer.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        OfflinePlayer player = this.parseArgument(strings, 0);
        this.userCache.modifyUser(player.getUniqueId(), target -> {
            if (target == null) {
                this.lang.get("could-not-find-user").to(sender);
                return;
            }
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
        });
    }
}

