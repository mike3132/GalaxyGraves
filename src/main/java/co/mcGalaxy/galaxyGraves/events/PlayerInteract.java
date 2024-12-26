package co.mcGalaxy.galaxyGraves.events;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.inventory.PlayerInventories;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteract implements Listener {

    private final PlayerInventories playerInventories = new PlayerInventories();

    GalaxyGraves plugin;

    public PlayerInteract(GalaxyGraves plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent pc) {
        Player player = pc.getPlayer();
        Entity entity = pc.getRightClicked();


    }

}
