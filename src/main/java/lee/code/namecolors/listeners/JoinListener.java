package lee.code.namecolors.listeners;

import lee.code.namecolors.NameColors;
import lee.code.namecolors.files.defaults.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        NameColors plugin = NameColors.getPlugin();
        Player player = e.getPlayer();
        if (plugin.getData().getAutoGlow()) {

            if (!plugin.getUtility().supports(9)) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_ERROR_GLOW_VERSION.getConfigValue(null));
                return;
            }
            if (!player.isGlowing()) player.setGlowing(true);
        }
        plugin.getUtility().registerNameTag(player);
        plugin.getUtility().getColorBoard().getTeam(player.getName()).addEntry(player.getName());
        plugin.getUtility().setDisplayNameColorEssentials(e.getPlayer());
    }
}
