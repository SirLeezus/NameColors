package lee.code.namecolors.commands.subcommands;

import lee.code.namecolors.NameColors;
import lee.code.namecolors.commands.SubCommand;
import lee.code.namecolors.files.defaults.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Glow extends SubCommand {

    @Override
    public String getName() {
        return "glow";
    }

    @Override
    public String getDescription() {
        return "Toggle your glow.";
    }

    @Override
    public String getSyntax() {
        return "/namecolors glow &f<on/off> ";
    }

    @Override
    public String getPermission() {
        return "color.command.glow";
    }

    @Override
    public void perform(Player player, String[] args) {
        NameColors plugin = NameColors.getPlugin();

        if (plugin.getPU().supports(9)) {
            if (args.length == 1) {
                if (player.isGlowing()) {
                    player.setGlowing(false);
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) +Lang.MESSAGE_COMMAND_GLOW_OFF.getConfigValue(null));
                } else if (!player.isGlowing()) {
                    player.setGlowing(true);
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_GLOW_ON.getConfigValue(null));
                }
            } else if (args.length > 1) {
                if (args[1].equals("on")) {
                    if (!player.isGlowing()) player.setGlowing(true);
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_GLOW_ON.getConfigValue(null));
                } else if (args[1].equals("off")) {
                    if (player.isGlowing()) player.setGlowing(false);
                    player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_GLOW_OFF.getConfigValue(null));
                }
            }
        } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_ERROR_GLOW_VERSION.getConfigValue(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
