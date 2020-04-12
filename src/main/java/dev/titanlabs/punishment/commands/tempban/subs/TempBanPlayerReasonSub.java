package dev.titanlabs.punishment.commands.tempban.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class TempBanPlayerReasonSub extends SubCommand<CommandSender> {
    private final UserCache userCache;
    private final Lang lang;

    public TempBanPlayerReasonSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(OfflinePlayer.class, "player");
        this.addArgument(String.class, "time-placer");
        this.addArgument(String.class, "reason-placer");
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {

    }
}
