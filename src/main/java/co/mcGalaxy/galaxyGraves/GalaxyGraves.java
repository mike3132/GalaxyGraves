package co.mcGalaxy.galaxyGraves;

import co.mcGalaxy.galaxyGraves.commands.GravesCommand;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.events.PlayerDeath;
import co.mcGalaxy.galaxyGraves.events.PlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GalaxyGraves extends JavaPlugin {

    private static GalaxyGraves INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic

        // Event register
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

        // Command register
        registerCommands();

        // Config loader
        ConfigManager.MESSAGES.create();
        ConfigManager.INVENTORIES.create();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static GalaxyGraves getInstance() {
    return INSTANCE;
    }

    private void registerCommands() {
        new GravesCommand();
    }


}
