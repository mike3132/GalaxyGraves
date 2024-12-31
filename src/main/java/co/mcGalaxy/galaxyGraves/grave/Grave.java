package co.mcGalaxy.galaxyGraves.grave;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class Grave {

    private final Location location;
    private final Npc npc;
    private final Model model;
    private final UUID uuid;
    private final ItemStack[] itemStacks;


    public Grave(Player player, UUID uuid) {
        this.location = player.getLocation();
        this.npc = new Npc(player, player.getLocation(), player.getName(), player.getName());
        this.model = new Model(player.getLocation());
        this.uuid = player.getUniqueId();
        this.itemStacks = player.getInventory().getContents();
    }

    public void create() {
        this.npc.spawn(npc.getLocation());
        this.model.spawn(model.getLocation());
    }

    public void remove() {
        this.npc.remove(npc.getLocation());
        this.model.remove(model.getLocation());
    }

    public Location getLocation() {
        return location;
    }

    public Npc getNpc() {
        return npc;
    }

    public Model getModel() {
        return model;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemStack[] getItemStacks() {
        return itemStacks;
    }
}
