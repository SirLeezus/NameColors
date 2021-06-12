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
            if (plugin.getPU().supports(9)) {
                if (!player.isGlowing()) player.setGlowing(true);
            } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_ERROR_GLOW_VERSION.getConfigValue(null));
        }
        plugin.getPU().registerNameTag(player);
        plugin.getPU().setDisplayNameColorEssentials(e.getPlayer());
    }
}
