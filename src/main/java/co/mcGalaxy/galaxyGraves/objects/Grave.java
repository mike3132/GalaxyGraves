package co.mcGalaxy.galaxyGraves.objects;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Grave {

    private final Location location;
    private final Npc npc;
    private final Model model;

    public Grave(Player player) {
        this.location = player.getLocation();
        this.npc = new Npc(player, player.getLocation(), player.getName());
        this.model = new Model(player.getLocation());
    }

    public void create() {
        this.npc.spawn(npc.getLocation());
        this.model.spawn(model.getLocation());
    }

    public void remove() {
        if (GalaxyGraves.getInstance().graveManager.getGraves().isEmpty()) {
            Bukkit.broadcastMessage("Graves map is empty");
            return;
        }
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
}
