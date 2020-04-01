package dev.titanlabs.punishment.commands.kick;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class KickCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public KickCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        this.lang.get("kick-command-usage").to(sender);
    }
}
