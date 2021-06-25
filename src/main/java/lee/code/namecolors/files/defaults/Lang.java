package lee.code.namecolors.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Lang {
    PREFIX("PREFIX", "&2&lNameColors &e➔ &r"),
    ERROR_NO_PERMISSION("ERROR_NO_PERMISSION", "&cYou sadly do not have permission for this."),
    MESSAGE_HELP_DIVIDER("MESSAGE_HELP_DIVIDER", "&e▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    MESSAGE_HELP_TITLE("MESSAGE_HELP_TITLE", "      &6-== &a&l&nPlayerNameColors Help&r &6==-"),
    MESSAGE_HELP_SUB_COMMAND("MESSAGE_HELP_SUB_COMMAND", "&3{0}&b. &e{1} &c| &7{2}"),
    ERROR_NOT_A_CONSOLE_COMMAND("ERROR_NOT_A_CONSOLE_COMMAND", "&cThis is not a console command."),
    MESSAGE_COMMAND_GLOW_OFF("MESSAGE_COMMAND_GLOW_OFF", "&6You have toggled your glow &c&lOFF&6."),
    MESSAGE_COMMAND_GLOW_ON("MESSAGE_COMMAND_GLOW_ON", "&6You have toggled your glow &2&lON&6."),
    MESSAGE_RELOAD("MESSAGE_RELOAD", "&aThe plugin has been reloaded."),
    MESSAGE_UPDATE("MESSAGE_UPDATE", "&aColor update successful for the player &6{0}&a!"),
    ERROR_MESSAGE_UPDATE("ERROR_MESSAGE_UPDATE", "&cThe player &6{0} &cis not online."),
    MESSAGE_ERROR_GLOW_VERSION("MESSAGE_ERROR_GLOW_VERSION", "&cSadly glow is not supported on your server version."),
    ;

    @Getter private final String path;
    @Getter private final String def;
    @Setter private static FileConfiguration file;

    public String getDefault() {
        return def;
    }

    public String getConfigValue(final String[] args) {
        String fileValue = file.getString(this.path, this.def);
        if (fileValue == null) fileValue = "";
        String value = ChatColor.translateAlternateColorCodes('&', fileValue);
        if (args == null) return value;
        else if (args.length == 0) return value;
        for (int i = 0; i < args.length; i++) value = value.replace("{" + i + "}", args[i]);
        return value;
    }
}