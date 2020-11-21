package lee.code.namecolors;

import com.earth2me.essentials.Essentials;
import lombok.Getter;
import net.luckperms.api.LuckPermsProvider;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;
import java.util.logging.Level;

public class Utility {

    @Getter private Scoreboard colorBoard;

    public void registerScoreboard() {

        NameColors plugin = NameColors.getPlugin();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        colorBoard = manager.getMainScoreboard();

        if (plugin.getData().getHeathDisplaySupport()) {

            if (supports(13)) {

                if (colorBoard.getObjective("health") != null) colorBoard.getObjective("health").unregister();
                Objective o = colorBoard.registerNewObjective("health", "health", format("&c❤"), RenderType.HEARTS);
                o.setDisplaySlot(DisplaySlot.BELOW_NAME);

            } else {
                if (colorBoard.getObjective("health") != null) colorBoard.getObjective("health").unregister();
                Objective o = colorBoard.registerNewObjective("health", "health");
                o.setDisplayName(format("&c❤"));
                o.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
        }
    }
    //color formatting string
    public String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public void registerNameTag(Player player) {
        NameColors plugin = NameColors.getPlugin();

        ChatColor color = ChatColor.BLUE;

        if (plugin.getData().getPermissionSystemSupport()) {

            if (player.isOp()) {
                color = plugin.getData().getOPColor();
            } else {

                for (ChatColor permColor : ChatColor.values()) {
                    if (player.hasPermission("color." + permColor.name())) {
                        color = permColor;
                        break;
                    }
                }
            }
        } else if (plugin.getData().getLuckPermsSupport()) {
            color = getLuckPermRankColor(player.getUniqueId());
        }

        if (colorBoard.getTeam(player.getName()) != null) colorBoard.getTeam(player.getName()).unregister();
        Team t = colorBoard.registerNewTeam(player.getName());
        if (supports(13)) t.setColor(color);
        t.setPrefix(format("" + color));

        if (plugin.getData().getLuckPermsSupport()) {
            String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
            String suffix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getSuffix();

            if (!supports(13)) {
                if (prefix != null && prefix.length() + 2 > 16) {
                    Bukkit.getLogger().log(Level.SEVERE, "[NamePlateColors] The prefix (" + prefix + color + ") is sadly over 16 total characters and your server version does not support any higher.");
                    return;

                } else if (suffix != null && suffix.length() > 16) {
                    Bukkit.getLogger().log(Level.SEVERE, "[NamePlateColors] The suffix (" + suffix + ") is sadly over 16 total characters and your server version does not support any longer.");
                    return;
                }
            }

            if (prefix != null) t.setPrefix(format(prefix + color));
            if (suffix != null) t.setSuffix(format(suffix));
        }
    }

    public ChatColor getLuckPermRankColor(UUID uuid) {
        NameColors plugin = NameColors.getPlugin();
        String rank = LuckPermsProvider.get().getUserManager().getUser(uuid).getPrimaryGroup();
        return plugin.getData().getLuckPermRankColor(rank);
    }

    public void setDisplayNameColorEssentials(Player player) {
        NameColors plugin = NameColors.getPlugin();
        if (plugin.getData().getEssentialsColorSupport()) {
            Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
            if (plugin.getData().getLuckPermsSupport()) {
                ess.getUser(player).setNickname(getLuckPermRankColor(player.getUniqueId()) + player.getName());
            } else if (plugin.getData().getPermissionSystemSupport()) {

                if (player.isOp()) {
                    ess.getUser(player).setNickname(plugin.getData().getOPColor() + player.getName());
                } else {

                    for (ChatColor permColor : ChatColor.values()) {
                        if (player.hasPermission("color." + permColor.name())) {
                            ess.getUser(player).setNickname(permColor + player.getName());
                            return;
                        }
                    }
                }
            }
        }
    }

    //XSeries version checker
    private final int VERSION = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));

    public boolean supports(int version) {
        return VERSION >= version;
    }

    public String getMajorVersion(String version) {
        Validate.notEmpty(version, "Cannot get major Minecraft version from null or empty string");

        // getVersion()
        int index = version.lastIndexOf("MC:");
        if (index != -1) {
            version = version.substring(index + 4, version.length() - 1);
        } else if (version.endsWith("SNAPSHOT")) {
            // getBukkitVersion()
            index = version.indexOf('-');
            version = version.substring(0, index);
        }

        // 1.13.2, 1.14.4, etc...
        int lastDot = version.lastIndexOf('.');
        if (version.indexOf('.') != lastDot) version = version.substring(0, lastDot);

        return version;
    }

}
