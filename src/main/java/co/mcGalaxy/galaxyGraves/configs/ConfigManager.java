package co.mcGalaxy.galaxyGraves.configs;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public enum ConfigManager {

    MESSAGES, INVENTORIES;

    private final File file;
    private FileConfiguration configuration;

    ConfigManager() {
        this.file = new File(GalaxyGraves.getInstance().getDataFolder(), this.toString().toUpperCase(Locale.ROOT) + ".yml");
    }

    public File getFile() {
        return file;
    }

    public void reload() {
        configuration = null;
    }

    public FileConfiguration get() {
        if (configuration == null) {
            configuration = YamlConfiguration.loadConfiguration(file);
        }
        return configuration;
    }

    public void save() {
        if (configuration == null) return;
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void create() {
        GalaxyGraves.getInstance().saveResource(this.toString().toLowerCase(Locale.ROOT) + ".yml", false);
    }

    public static void reloadAll() {
        for (ConfigManager value : values()) {
            value.reload();
        }
    }

    // Nothing below this line is put into memory, It's all directly written to and read from the config file.
    public File getFileFromConfig() {
        return new File(GalaxyGraves.getInstance().getDataFolder(), this.toString().toLowerCase(Locale.ROOT) + ".yml");
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration configuration) {
        try {
            configuration.save(getFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
