package lee.code.namecolors.commands.subcommands;

import lee.code.namecolors.NameColors;
import lee.code.namecolors.commands.SubCommand;
import lee.code.namecolors.files.defaults.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Update extends SubCommand {

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Force update a player's color.";
    }

    @Override
    public String getSyntax() {
        return "/namecolors update &f<player>";
    }

    @Override
    public String getPermission() {
        return "color.command.update";
    }

    @Override
    public void perform(Player player, String[] args) {
        NameColors plugin = NameColors.getPlugin();

        if  (args.length > 1) {
            String target = args[1];
            for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                if (oPlayer.getName().equals(target)) {
                    plugin.getPU().registerNameTag(oPlayer);
                    plugin.getPU().setDisplayNameColorEssentials(oPlayer);
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_UPDATE.getConfigValue(new String[] { oPlayer.getName() }));
                    return;
                }
            }
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MESSAGE_UPDATE.getConfigValue(new String[] { target }));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        NameColors plugin = NameColors.getPlugin();

        if  (args.length > 1) {
            String target = args[1];
            for (Player oPlayer : Bukkit.getOnlinePlayers()) {
                if (oPlayer.getName().equals(target)) {
                    plugin.getPU().registerNameTag(oPlayer);
                    plugin.getPU().setDisplayNameColorEssentials(oPlayer);
                    console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_UPDATE.getConfigValue(new String[] { oPlayer.getName() }));
                    return;
                }
            }
            console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_MESSAGE_UPDATE.getConfigValue(new String[] { target }));
        }
    }
}

