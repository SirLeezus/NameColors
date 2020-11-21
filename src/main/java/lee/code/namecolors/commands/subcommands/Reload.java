package lee.code.namecolors.commands.subcommands;

import lee.code.namecolors.NameColors;
import lee.code.namecolors.commands.SubCommand;
import lee.code.namecolors.files.defaults.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin.";
    }

    @Override
    public String getSyntax() {
        return "/namecolors reload";
    }

    @Override
    public String getPermission() {
        return "color.command.reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        NameColors plugin = NameColors.getPlugin();
        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        NameColors plugin = NameColors.getPlugin();
        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }
}
