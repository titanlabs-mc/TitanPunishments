package dev.titanlabs.punishment.commands.unban.subs;

import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.cache.OfflineUserCache;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Optional;
import java.util.UUID;

public class UnBanPlayerSub extends SubCommand<CommandSender> {
    private final OfflineUserCache offlineUserCache;
    private final PunishmentApi punishmentApi;
    private final UserCache userCache;
    private final Lang lang;

    public UnBanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.offlineUserCache = plugin.getOfflineUserCache();
        this.punishmentApi = plugin.getLocalPunishmentApi();
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(String.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        String inputString = this.parseArgument(strings, 0);
        Optional<UUID> optionalTargetUuid = this.offlineUserCache.get(inputString);
        System.out.println("1");
        if (optionalTargetUuid.isPresent()) {
            System.out.println("2");
            this.userCache.modifyUser(optionalTargetUuid.get(), target -> {
                System.out.println("3");
                if (target.isBanned()) {
                    System.out.println("4");
                    this.punishmentApi.unban(target, sender, PunishmentEndReason.MANUAL, false);
                    System.out.println("5");
                    return;
                }
                this.lang.get("unban.not-banned").to(sender);
            });
            return;
        }
        this.lang.get("could-not-find-user").to(sender);
    }
}
