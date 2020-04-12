package dev.titanlabs.punishment.commands.tempban.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.api.PunishmentApi;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.service.Time;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TempBanPlayerSub extends SubCommand<CommandSender> {
    private final UserCache userCache;
    private final PunishmentApi punishmentApi;
    private final Lang lang;

    public TempBanPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.userCache = plugin.getUserCache();
        this.punishmentApi = plugin.getLocalPunishmentApi();
        this.lang = plugin.getLang();

        this.addArgument(OfflinePlayer.class, "player");
        this.addArgument(String.class, "time-placer");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        OfflinePlayer targetPlayer = this.parseArgument(strings, 0);
        String time = this.parseArgument(strings, 0);
        long parsedTime = Time.parseTimeInput(time);
        if (parsedTime == 0) {
            this.lang.get("tempban.invalid-time").to(sender);
            return;
        }
        this.userCache.get(targetPlayer.getUniqueId()).thenAccept(optionalUser -> {
            optionalUser.ifPresent(user -> {
                UUID executorUniqueId = sender instanceof Player ? ((Player) sender).getUniqueId() : null;
                UUID subjectUniqueId = user.getUuid();
                this.punishmentApi.banUser(user, new Ban(executorUniqueId, subjectUniqueId, parsedTime), sender, false);
            });
        });
    }
}
