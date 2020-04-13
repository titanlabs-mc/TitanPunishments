package dev.titanlabs.punishment.commands.ipinfo;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class IpInfoCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public IpInfoCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        this.lang.get("ipinfo.usage").to(sender);
    }
}
