package co.mcGalaxy.galaxyGraves;

import co.mcGalaxy.galaxyGraves.commands.TabComplete;
import co.mcGalaxy.galaxyGraves.events.EntityEvent;
import co.mcGalaxy.galaxyGraves.utils.HexUtils;
import co.mcGalaxy.galaxyGraves.commands.GraveCommand;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.events.GraveEvent;
import co.mcGalaxy.galaxyGraves.managers.GraveManager;
import co.mcGalaxy.galaxyGraves.utils.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GalaxyGraves extends JavaPlugin {

    private static GalaxyGraves INSTANCE;
    public GraveManager graveManager = new GraveManager();
    private SchedulerUtils schedulerUtils;

    @Override
    public void onEnable() {
        INSTANCE = this;
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(messageFormatter("&2Enabled"));

        // Event register
        Bukkit.getPluginManager().registerEvents(new GraveEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityEvent(this), this);

        // Command register
        registerCommands();

        // Config loader
        ConfigManager.MESSAGES.create();
        saveDefaultConfig();

        // Loads grave scheduler
        this.schedulerUtils = new SchedulerUtils(this);

        // Api checker

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(messageFormatter("&4Disabled"));

        this.schedulerUtils.disable();
    }

    public static GalaxyGraves getInstance() {
        return INSTANCE;
    }

    private void registerCommands() {
        new GraveCommand(this);
        new TabComplete();
    }

    private String messageFormatter(String key) {
        return HexUtils.colorify("#ba63f7[<g:#0099ff:#EEEEEE>Galaxy Graves#ba63f7] #121212> " + key);
    }

    public SchedulerUtils getGraveScheduler() {
        return this.schedulerUtils;
    }

}
