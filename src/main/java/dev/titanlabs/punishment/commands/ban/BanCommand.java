package dev.titanlabs.punishment.commands.ban;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.commands.ban.subs.BanPlayerReasonSub;
import dev.titanlabs.punishment.commands.ban.subs.BanPlayerSub;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class BanCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public BanCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();

        this.setSubCommands(
                new BanPlayerSub(plugin, "titanpunish.ban", true),
                new BanPlayerReasonSub(plugin, "titanpunish.ban", true)
        );
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        this.lang.get("ban.").to(sender);
    }
}
