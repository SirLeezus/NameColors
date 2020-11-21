package lee.code.namecolors;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class Data {

    //get luck perm rank color
    private final HashMap<String, ChatColor> luckPermRankColor = new HashMap<>();

    //essentials nickname support
    @Getter private Boolean essentialsColorSupport;

    //luckperms support
    @Getter private Boolean luckPermsSupport;

    //permission system support
    @Getter private Boolean permissionSystemSupport;

    //health display support
    @Getter private Boolean heathDisplaySupport;

    //health display support
    @Getter private Boolean autoGlow;

    //OP color
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
        for (String key : plugin.getFile("config").getData().getConfigurationSection("luckperms-ranks").getKeys(false)) {
            ConfigurationSection keySection = plugin.getFile("config").getData().getConfigurationSection("luckperms-ranks." + key);
            setLuckPermRankColor(key, ChatColor.valueOf(keySection.getString("color").toUpperCase()));
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
