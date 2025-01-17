package co.mcGalaxy.galaxyGraves.managers;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.grave.Grave;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GraveManager {

    private final Table<UUID, Location, Grave> graves = HashBasedTable.create();

    public GraveManager() {
    }

    public void add(Grave grave) {
        this.graves.put(grave.getUuid(), grave.getLocation(), grave);
    }

    public void remove(Grave grave) {
        this.graves.remove(grave.getUuid(), grave.getLocation());
    }

    public Table<UUID, Location, Grave> getGraves() {
        return graves;
    }

    public void saveGrave(UUID uuid, Grave grave) {
        String fileName = "Graves-Backups/" + uuid.toString() + ".yml";
        File file = new File(GalaxyGraves.getInstance().getDataFolder(), fileName);
        try {

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                Files.createFile(file.toPath());
            }
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Player player = Bukkit.getPlayer(grave.getUuid());

            int index = configuration.getKeys(false).size();
            String identifier = "grave-" + index;

            configuration.set(identifier + ".date", getDate());
            configuration.set(identifier + ".time", getTime());
            configuration.set(identifier + ".location.world", grave.getLocation().getWorld().getName());
            configuration.set(identifier + ".location.x", grave.getLocation().getBlockX());
            configuration.set(identifier + ".location.y", grave.getLocation().getBlockY());
            configuration.set(identifier + ".location.z", grave.getLocation().getBlockZ());
            configuration.set(identifier + ".player-name", player.getName());
            configuration.set(identifier + ".player-xp", player.getExp());
            configuration.set(identifier + ".player-inventory",
                    Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).toList());
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBackups(Player player, UUID uuid, int index) {
        String fileName = "Graves-Backups/" + uuid.toString() + ".yml";
        File file = new File(GalaxyGraves.getInstance().getDataFolder(), fileName);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        List<String> keys = new ArrayList<>(configuration.getKeys(false));

        if (index < 0 || index >= keys.size()) {
            PlayerMessage.sendMessage(player, "Admin-Recover-Backup-Invalid-Index");
            return;
        }

        String key = keys.get(index);

        List<?> itemsList = configuration.getList(key + ".player-inventory");

        if (itemsList == null) return;

        for (Object itemObj : itemsList) {
            if (!(itemObj instanceof ItemStack itemStack)) {
                PlayerMessage.sendPlayerMessageWithoutConfig(player, "&4CRITICAL ERROR: &cPlease check console for more information");
                GalaxyGraves.getInstance().getLogger().severe("Invalid item in backup list: " + itemObj);
                continue;
            }
            player.getInventory().addItem(itemStack);
        }

        PlayerMessage.sendMessage(player, "Admin-Recover-Backup");
    }


    private String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return now.format(formatter);
    }

    private String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }

}
