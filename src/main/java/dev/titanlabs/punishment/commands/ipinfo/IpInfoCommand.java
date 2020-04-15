package dev.titanlabs.punishment.commands.ipinfo;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.commands.ipinfo.subs.IpInfoPlayerAccountsSub;
import dev.titanlabs.punishment.commands.ipinfo.subs.IpInfoPlayerSub;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class IpInfoCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public IpInfoCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();

        this.setSubCommands(
                new IpInfoPlayerAccountsSub(plugin, "titanpunish.ipinfo", true),
                new IpInfoPlayerSub(plugin, "titanpunish.ipinfo", true)
        );
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        this.lang.get("ipinfo.usage").to(sender);
    }
}
