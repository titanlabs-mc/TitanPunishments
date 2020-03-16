package dev.titanlabs.punishment.commands.unbancommand;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.commands.unbancommand.subs.UnBanPlayerSub;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class UnBanCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public UnBanCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();

        this.setSubCommands(
                new UnBanPlayerSub(plugin, "titanpunish.unban", true)
        );
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        this.lang.get("unban-command-usage").to(sender);
    }
}
