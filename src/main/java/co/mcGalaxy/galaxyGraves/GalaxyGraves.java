package co.mcGalaxy.galaxyGraves;

import co.mcGalaxy.galaxyGraves.utils.HexUtils;
import co.mcGalaxy.galaxyGraves.commands.GraveCommand;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.events.GraveEvent;
import co.mcGalaxy.galaxyGraves.managers.GraveManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GalaxyGraves extends JavaPlugin {

    private static GalaxyGraves INSTANCE;
    public GraveManager graveManager = new GraveManager();

    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(messageFormatter("&2Enabled"));

        // Event register
        Bukkit.getPluginManager().registerEvents(new GraveEvent(this), this);

        // Command register
        registerCommands();

        // Config loader
        ConfigManager.MESSAGES.create();
        saveDefaultConfig();

        // Api checker

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(messageFormatter("&4Disabled"));

    }

    public static GalaxyGraves getInstance() {
        return INSTANCE;
    }

    private void registerCommands() {
        new GraveCommand(this);
    }

    private String messageFormatter(String key) {
        return HexUtils.colorify("#ba63f7[<g:#0099ff:#EEEEEE>Galaxy Graves#ba63f7] #121212> " + key);
    }

}
