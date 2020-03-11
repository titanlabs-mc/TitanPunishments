package dev.titanlabs.punishment.commands.ban;

import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.commands.ban.subs.BanPlayerReasonSub;
import dev.titanlabs.punishment.commands.ban.subs.BanPlayerSub;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class BanCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public BanCommand(Punishment plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();

        this.setSubCommands(
                new BanPlayerSub(plugin, "titanpunish.ban", true),
                new BanPlayerReasonSub(plugin, "titanpunish.ban", true)
        );
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] strings) {
        this.lang.get("ban-command-usage").to(commandSender);
    }
}
