package lee.code.namecolors;

import lee.code.namecolors.commands.CommandManager;
import lee.code.namecolors.commands.TabCompletion;
import lee.code.namecolors.files.CustomFile;
import lee.code.namecolors.files.FileManager;
import lee.code.namecolors.files.defaults.Lang;
import lee.code.namecolors.listeners.JoinListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class NameColors extends JavaPlugin {

    @Getter private FileManager fileManager;
    @Getter private PluginUtility pU;
    @Getter private Data data;

    public void onEnable() {

        this.fileManager = new FileManager();
        this.pU = new PluginUtility();
        this.data = new Data();

        //load config
        fileManager.addConfig("config");
        fileManager.addConfig("lang");

        //load lang defaults
        loadDefaults();

        //load plugin data
        data.loadData();

        //register scoreboard
        pU.registerScoreboard();

        //Commands
        getCommand("namecolors").setExecutor(new CommandManager());
        getCommand("namecolors").setTabCompleter(new TabCompletion());

        //Listeners
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public void loadDefaults() {

        //lang
        Lang.setFile(fileManager.getConfig("lang").getData());

        for (Lang value : Lang.values()) fileManager.getConfig("lang").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("lang").getData().options().copyDefaults(true);
        fileManager.getConfig("lang").save();
    }

    //gets a file
    public CustomFile getFile(String file) {
        return fileManager.getConfig(file);
    }

    //saves a file
    public void saveFile(String file) {
        fileManager.getConfig(file).save();
    }

    //reloads all files
    public void reloadFiles() {
        fileManager.reloadAll();
    }

    public static NameColors getPlugin() {
        return NameColors.getPlugin(NameColors.class);
    }
}
