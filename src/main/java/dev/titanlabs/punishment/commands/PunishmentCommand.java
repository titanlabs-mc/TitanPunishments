package dev.titanlabs.punishment.commands;

import dev.titanlabs.punishment.PunishmentPlugin;
import me.hyfe.simplespigot.command.command.SimpleCommand;
import org.bukkit.command.CommandSender;

public class PunishmentCommand extends SimpleCommand<CommandSender> {

    public PunishmentCommand(PunishmentPlugin plugin) {
        super(plugin, "punishment", "punishment.admin");
        this.setSubCommands(

        );
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {

    }
}
