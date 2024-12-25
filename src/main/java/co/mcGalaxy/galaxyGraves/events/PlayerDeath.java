package co.mcGalaxy.galaxyGraves.events;

import co.mcGalaxy.galaxyGraves.inventory.PlayerInventories;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private final PlayerInventories playerInventories = new PlayerInventories();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde) {
        Player player = pde.getEntity();

        playerInventories.saveInventory(player);
        player.sendMessage("You have died");
    }



}
