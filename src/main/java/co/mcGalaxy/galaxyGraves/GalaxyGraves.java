package co.mcGalaxy.galaxyGraves;

import co.mcGalaxy.galaxyGraves.commands.GravesCommand;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.events.GraveEvent;
import co.mcGalaxy.galaxyGraves.managers.GraveManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class GalaxyGraves extends JavaPlugin {

    private static GalaxyGraves INSTANCE;
    public GraveManager graveManager = new GraveManager();

    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic

        // Event register
        Bukkit.getPluginManager().registerEvents(new GraveEvent(this), this);

        // Command register
        registerCommands();

        // Config loader
        ConfigManager.MESSAGES.create();
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
