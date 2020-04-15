package dev.titanlabs.punishment.commands.ipinfo.subs;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.UserCache;
import dev.titanlabs.punishment.config.Lang;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.command.command.SubCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class IpInfoPlayerSub extends SubCommand<CommandSender> {
    private final UserCache userCache;
    private final Lang lang;

    public IpInfoPlayerSub(PunishmentPlugin plugin, String permission, boolean isConsole) {
        super(plugin, permission, isConsole);
        this.userCache = plugin.getUserCache();
        this.lang = plugin.getLang();

        this.addArgument(OfflinePlayer.class, "player");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        OfflinePlayer offlineTargetPlayer = this.parseArgument(args, 0);
        this.userCache.get(offlineTargetPlayer.getUniqueId()).thenAccept(optionalUser -> {
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                this.lang.get("ipinfo.player.info", replacer -> replacer
                        .set("address", user.getIpAddress().getAddress())
                        .set("player", user.getPlayer().getName())).to(sender);
                return;
            }
            this.lang.get("could-not-find-user").to(sender);
        });
    }
}
