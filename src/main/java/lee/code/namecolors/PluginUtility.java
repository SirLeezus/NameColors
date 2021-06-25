package lee.code.namecolors;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import lombok.Getter;
import net.luckperms.api.LuckPermsProvider;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class PluginUtility {

    @Getter private Scoreboard colorBoard;

    public void registerScoreboard() {
        NameColors plugin = NameColors.getPlugin();
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        if (manager != null) {
            colorBoard = manager.getMainScoreboard();
            if (plugin.getData().getHeathDisplaySupport()) {
                if (getColorBoard().getObjective("health") == null) {
                    if (supports(13)) {
                        Objective o = getColorBoard().registerNewObjective("health", "health", format("&c❤"), RenderType.HEARTS);
                        o.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    } else {
                        Objective o = colorBoard.registerNewObjective("health", "health");
                        o.setDisplayName(format("&c❤"));
                        o.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    }
                }
            }
        }
    }
    //color formatting string
    public String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public void registerNameTag(Player player) {
        NameColors plugin = NameColors.getPlugin();
        ChatColor color = ChatColor.WHITE;
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

        Team team = getColorBoard().getTeam(player.getName());
        if (team != null) team.unregister();
        Team t = getColorBoard().registerNewTeam(player.getName());
        if (supports(13)) t.setColor(color);
        t.setPrefix(format("" + color));
        t.addEntry(player.getName());

        if (plugin.getData().getLuckPermsSupport()) {
            net.luckperms.api.model.user.User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                String prefix = user.getCachedData().getMetaData().getPrefix();
                String suffix = user.getCachedData().getMetaData().getSuffix();

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
    }

    public ChatColor getLuckPermRankColor(UUID uuid) {
        NameColors plugin = NameColors.getPlugin();
        net.luckperms.api.model.user.User user = LuckPermsProvider.get().getUserManager().getUser(uuid);
        String rank = "null";
        if (user != null) rank = user.getPrimaryGroup();
        return plugin.getData().getLuckPermRankColor(rank);
    }

    public void setDisplayNameColorEssentials(Player player) {
        NameColors plugin = NameColors.getPlugin();
        if (plugin.getData().getEssentialsColorSupport()) {
            Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
            if (ess != null) {
                User user = ess.getUser(player);
                if (plugin.getData().getLuckPermsSupport()) {
                    user.setNickname(getLuckPermRankColor(player.getUniqueId()) + player.getName());
                } else if (plugin.getData().getPermissionSystemSupport()) {
                    if (player.isOp()) {
                        user.setNickname(plugin.getData().getOPColor() + player.getName());
                    } else {
                        for (ChatColor permColor : ChatColor.values()) {
                            if (player.hasPermission("color." + permColor.name())) {
                                user.setNickname(permColor + player.getName());
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public List<String> getOnlinePlayers() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    //XSeries version checker - credit to him
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
