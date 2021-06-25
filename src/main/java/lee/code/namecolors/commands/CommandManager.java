package lee.code.namecolors.commands;

import lee.code.namecolors.NameColors;
import lee.code.namecolors.commands.subcommands.Glow;
import lee.code.namecolors.commands.subcommands.Reload;
import lee.code.namecolors.commands.subcommands.Update;
import lee.code.namecolors.files.defaults.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new Glow());
        subCommands.add(new Reload());
        subCommands.add(new Update());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        NameColors plugin = NameColors.getPlugin();

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                for (SubCommand subCommand : subCommands) {
                    if (args[0].equalsIgnoreCase(subCommand.getName())) {
                        if (p.hasPermission(subCommand.getPermission())) subCommand.perform(p, args);
                        else
                            p.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_PERMISSION.getConfigValue(null));
                        return true;
                    }
                }
            }

            int number = 1;
            List<String> lines = new ArrayList<>();
            lines.add(Lang.MESSAGE_HELP_DIVIDER.getConfigValue(null));
            lines.add(Lang.MESSAGE_HELP_TITLE.getConfigValue(null));
            lines.add("&r");

            for (SubCommand subCommand : subCommands) {
                if (p.hasPermission(subCommand.getPermission())) {
                    lines.add(Lang.MESSAGE_HELP_SUB_COMMAND.getConfigValue(new String[]{String.valueOf(number), subCommand.getSyntax(), subCommand.getDescription()}));
                    number++;
                }
            }
            lines.add("&r");
            lines.add(Lang.MESSAGE_HELP_DIVIDER.getConfigValue(null));

            for (String line : lines) p.sendMessage(plugin.getPU().format(line));
            return true;

        }

        if (args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.performConsole(sender, args);
                    return true;
                }
            }

        }
        return true;
    }
}
