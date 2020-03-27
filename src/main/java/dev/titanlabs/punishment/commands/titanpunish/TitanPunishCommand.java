package dev.titanlabs.punishment.commands.titanpunish;

import dev.titanlabs.punishment.PunishmentPlugin;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import me.hyfe.simplespigot.text.Text;
import org.bukkit.command.CommandSender;

public class TitanPunishCommand extends SimpleCommand<CommandSender> {
    private final PunishmentPlugin plugin;

    public TitanPunishCommand(PunishmentPlugin plugin, String command, String permission, boolean isConsole) {
        super(plugin, command, permission, isConsole);
        this.plugin = plugin;
    }

    @Override
    public void onExecute(CommandSender sender, String[] strings) {
        Text.sendMessage(sender, "&cRunning TitanPunish v".concat(this.plugin.getDescription().getVersion()).concat(" by Zak Shearman and Hyfe at TitanLabs.\n" +
                "&chttps://titanlabs.dev/"));
    }
}
