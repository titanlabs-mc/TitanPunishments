package dev.titanlabs.punishment.commands.tempban;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.commands.tempban.subs.TempBanPlayerReasonSub;
import dev.titanlabs.punishment.commands.tempban.subs.TempBanPlayerSub;
import dev.titanlabs.punishment.config.Lang;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class TempBanCommand extends SimpleCommand<CommandSender> {
    private final Lang lang;

    public TempBanCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.lang = plugin.getLang();

        this.setSubCommands(
                new TempBanPlayerSub(plugin, "titanpunish.tempban", true),
                new TempBanPlayerReasonSub(plugin, "titanpunish.tempban", true)
        );
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        this.lang.get("tempban.usage").to(sender);
    }
}
