package dev.titanlabs.punishment.commands.unban.subs;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class UnBanPlayerSub extends SubCommand<CommandSender> {
    private final PunishmentApi punishmentApi;
    private final UserCache userCache;
    private final Lang lang;

    public UnBanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.punishmentApi = plugin.getLocalPunishmentApi();
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
            if (!target.isBanned()) {
                this.lang.get("unban.not-banned", replacer -> replacer.set("player", target.getPlayer().getName())).to(sender);
                return;
            }
            this.punishmentApi.unban(target, sender, PunishmentEndReason.MANUAL, false);
            target.unban(PunishmentEndReason.MANUAL);
            this.lang.get("unban.executor-message", replacer -> replacer.set("player", player.getName())).to(sender);
        });
    }
}
