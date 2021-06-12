package lee.code.namecolors;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class Data {

    private final HashMap<String, ChatColor> luckPermRankColor = new HashMap<>();
    @Getter private Boolean essentialsColorSupport;
    @Getter private Boolean luckPermsSupport;
    @Getter private Boolean permissionSystemSupport;
    @Getter private Boolean heathDisplaySupport;
    @Getter private Boolean autoGlow;
    @Getter private ChatColor oPColor;

    private void setLuckPermRankColor(String rank, ChatColor color) {
        luckPermRankColor.put(rank, color);
    }
    public ChatColor getLuckPermRankColor(String rank) {
        return luckPermRankColor.getOrDefault(rank, ChatColor.BLUE);
    }

    public void loadData() {
        loadLuckPermRankColors();
        loadConfigBooleans();
    }

    private void loadLuckPermRankColors() {
        NameColors plugin = NameColors.getPlugin();
        ConfigurationSection config = plugin.getFile("config").getData().getConfigurationSection("luckperms-ranks");
        if (config != null) {
            for (String key : config.getKeys(false)) {
                ConfigurationSection keySection = plugin.getFile("config").getData().getConfigurationSection("luckperms-ranks." + key);
                if (keySection != null) {
                    String sColor = keySection.getString("color");
                    if (sColor != null) sColor = sColor.toUpperCase();
                    setLuckPermRankColor(key, ChatColor.valueOf(sColor));
                }
            }
        }
    }

    private void loadConfigBooleans() {
        NameColors plugin = NameColors.getPlugin();
        essentialsColorSupport = plugin.getFile("config").getData().getBoolean("essentials-color-support");
        luckPermsSupport = plugin.getFile("config").getData().getBoolean("luckperms-support");
        permissionSystemSupport = plugin.getFile("config").getData().getBoolean("permission-system");
        heathDisplaySupport = plugin.getFile("config").getData().getBoolean("display-health");
        autoGlow = plugin.getFile("config").getData().getBoolean("auto-glow");
        oPColor = ChatColor.valueOf(plugin.getFile("config").getData().getString("op-permission-system-color"));
    }
}
