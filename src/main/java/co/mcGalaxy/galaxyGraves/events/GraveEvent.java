package co.mcGalaxy.galaxyGraves.events;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.grave.Grave;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class GraveEvent implements Listener {

    private final GalaxyGraves plugin;
    private final int pistonDistance = GalaxyGraves.getInstance().getConfig().getInt("Grave-Distance-To-Piston");
    private final boolean playerDeathMessage = GalaxyGraves.getInstance().getConfig().getBoolean("Player-Death-Message");
    private final boolean graveReturnItemsMessage = GalaxyGraves.getInstance().getConfig().getBoolean("Grave-Return-Item-Message");
    private final boolean playerClaimOther = GalaxyGraves.getInstance().getConfig().getBoolean("Player-Take-Others-Grave");

    public GraveEvent(GalaxyGraves plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde) {
        Player player = pde.getEntity();
        Grave grave = new Grave(player, player.getUniqueId());
        grave.create();
        this.plugin.graveManager.add(grave);
        this.plugin.graveManager.saveGrave(player.getUniqueId(), grave);
        if (playerDeathMessage) {
            PlayerMessage.sendMessage(player, "Player-Death-Message");
        }

        grave.getLocation().getWorld().strikeLightningEffect(grave.getLocation());
        pde.getDrops().clear();
    }


    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent pc) {
        Player player = pc.getPlayer();
        Entity entity = pc.getRightClicked();
        Location entityLocation = entity.getLocation();
        Grave foundGrave = null;

        if (!(entity instanceof Interaction)) return;

        if (!this.plugin.graveManager.getGraves().containsColumn(entityLocation)) return;

        for (Grave grave : this.plugin.graveManager.getGraves().values()) {
            if (entityLocation.distance(grave.getLocation()) < 1) {
                foundGrave = grave;
                break;
            }
        }

        if (foundGrave == null) {
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&4ERROR: &cI do not see this a registered grave");
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&bIf you believe this to be a grave then please contact your servers support system");
            return;
        }

        if (!playerClaimOther) {
            if (player.getUniqueId() != foundGrave.getUuid() && !player.hasPermission("GalaxyGraves.Grave.Bypass")) {
                PlayerMessage.sendMessage(player, "Grave-Not-Owned-By-Player");
                return;
            }

            ItemStack[] itemsList = foundGrave.getItemStacks();

            for (ItemStack itemStack : itemsList) {
                if (itemStack == null || itemStack.getType().isAir()) continue;
                player.getInventory().addItem(itemStack);
            }

            if (player.getUniqueId() != foundGrave.getUuid()) {
                Player target = Bukkit.getPlayer(foundGrave.getUuid());

                PlayerMessage.sendMessageWithTarget(player, "Grave-Owned-by-Other-Message", target.getName());
                foundGrave.remove();
                this.plugin.graveManager.remove(foundGrave);
                return;
            }

            if (graveReturnItemsMessage) {
                PlayerMessage.sendMessage(player, "Grave-Return-Item-Message");
            }
        }

        foundGrave.remove();
        this.plugin.graveManager.remove(foundGrave);
    }

    @EventHandler
    public void onPistonMove(BlockPistonExtendEvent pe) {
        Block block = pe.getBlock();

        for (Grave grave : this.plugin.graveManager.getGraves().values()) {
            if (block.getLocation().distance(grave.getLocation()) > pistonDistance) continue;
            Player player = Bukkit.getPlayer(grave.getUuid());
            if (player == null) return;
            PlayerMessage.sendMessage(player, "Grave-Piston-Disabled-Message");
            pe.setCancelled(true);
        }
    }

}
