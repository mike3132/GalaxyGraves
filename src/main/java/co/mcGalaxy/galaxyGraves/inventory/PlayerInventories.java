package co.mcGalaxy.galaxyGraves.inventory;

import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerInventories {

    public void saveInventory(Player player) {
        FileConfiguration config = ConfigManager.INVENTORIES.getConfig();
        config.set(player.getUniqueId().toString(), player.getInventory().getContents());
        ConfigManager.INVENTORIES.save(config);
    }

    public void loadInventory(Player player) {
        FileConfiguration config = ConfigManager.INVENTORIES.getConfig();
        final ItemStack[] itemStacks = Objects.requireNonNull(config.getList(player.getUniqueId().toString()))
                .stream().map(o -> (ItemStack) o).toArray(ItemStack[]::new);
        player.getInventory().setContents(itemStacks);
    }




}
