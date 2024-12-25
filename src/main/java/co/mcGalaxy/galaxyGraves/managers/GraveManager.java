package co.mcGalaxy.galaxyGraves.managers;

import co.mcGalaxy.galaxyGraves.modelengine.MegModel;
import co.mcGalaxy.galaxyGraves.utils.Grave;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GraveManager {

    private final Map<Location, Grave> graves = new HashMap<>();

    private final UUID player;
    private final Location location;
    private final ItemStack[] inventory;
    private final MegModel model;

    public GraveManager(UUID player, Location location, ItemStack[] inventory) {
        this.player = player;
        this.location = location;
        this.inventory = inventory;
        this.model = new MegModel();
    }

    public void registerGrave(Grave grave) {
        this.graves.put(location, grave);
        grave.spawn(location);
        Bukkit.broadcastMessage("Putting grave at " + location);
    }

    public void unRegisterGrave(Grave grave) {
        this.graves.remove(grave.getLocation());
        grave.remove(grave.getLocation());
    }


    public Map<Location, Grave> getGraves() {
        return graves;
    }

    public void open(Player player) {
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public Location getLocation() {
        return location;
    }

    public MegModel getModel() {
        return model;
    }

    public UUID getPlayer() {
        return player;
    }


}
