package co.mcGalaxy.galaxyGraves.events;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.grave.Grave;
import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class GraveEvent implements Listener {

    GalaxyGraves plugin;

    public GraveEvent(GalaxyGraves plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde) {
        Player player = pde.getEntity();
        Grave grave = new Grave(player, player.getUniqueId());
        grave.create();
        this.plugin.graveManager.add(grave);
    }


    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent pc) {
        Player player = pc.getPlayer();
        Entity entity = pc.getRightClicked();
        Location entityLocation = entity.getLocation();
        Grave foundGrave = null;

        if (!(entity instanceof Pig)) return;
        if (!this.plugin.graveManager.getGraves().containsColumn(entityLocation)) return;

        for (Grave grave : this.plugin.graveManager.getGraves().values()) {
            if (entityLocation.distance(grave.getLocation()) < 1) {
                foundGrave = grave;
                break;
            }
        }

        if (foundGrave == null) {
            //TODO: Keep this internal but make it look better
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&4ERROR: &cThis isn't registered as a grave");
            return;
        }
        //TODO: Make this a config message
        PlayerMessage.sendPlayerMessageWithoutConfig(player, "&2Successfully found registered grave");
        final ItemStack[] itemStacks = foundGrave.getItemStacks();
        player.getInventory().setContents(itemStacks);
    }

    @EventHandler
    public void onPistonMove(BlockPistonExtendEvent pe) {
        Block block = pe.getBlock();

        for (Grave grave : this.plugin.graveManager.getGraves().values()) {
            if (block.getLocation().distance(grave.getLocation()) > 10) continue;
            //TODO: Make this a config message
            Bukkit.broadcastMessage("Piston within 10 of grave, so canceling the event");
            pe.setCancelled(true);
        }
    }

}
