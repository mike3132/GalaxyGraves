package co.mcGalaxy.galaxyGraves.grave;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Grave {

    private final Location location;
    private final Npc npc;
    private final Model model;
    private final Entity entity;
    private final UUID uuid;
    private final ItemStack[] itemStacks;

    private final boolean useModelEngine = GalaxyGraves.getInstance().getConfig().getBoolean("Model-Engine-Grave");

    public Grave(Player player, UUID uuid) {
        this.location = player.getLocation();
        this.location.setPitch(0);
        this.location.setYaw(0);
        this.npc = new Npc(player, player.getLocation(), player.getName(), player.getName());
        this.model = new Model(player.getLocation());
        this.entity = new Entity(player.getLocation());
        this.uuid = player.getUniqueId();
        this.itemStacks = player.getInventory().getContents();
    }

    public void create() {
        this.npc.spawn(npc.getLocation());
        if (useModelEngine) {
            this.model.spawn(model.getLocation());
        }
        this.entity.spawn(entity.getLocation());
    }

    public void remove() {
        this.npc.remove(npc.getLocation());
        if (useModelEngine) {
            this.model.remove(model.getLocation());
        }
        this.entity.remove(entity.getLocation());
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

    public Entity getInteractEntity() {
        return entity;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemStack[] getItemStacks() {
        return itemStacks;
    }
}
