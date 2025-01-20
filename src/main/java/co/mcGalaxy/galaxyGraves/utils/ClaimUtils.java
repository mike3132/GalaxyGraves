package co.mcGalaxy.galaxyGraves.utils;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ClaimUtils {


    public void playerClaim(Block block, Player player) {
        Location blockLocation = block.getLocation();
        Location blockCenter = blockLocation.add(0.5, 0, 0.5);
        World world = blockLocation.getWorld();
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName(player.getDisplayName());
        skullMeta.setOwningPlayer(player);
        skull.setItemMeta(skullMeta);

        ArmorStand armorStand = world.spawn(blockCenter, ArmorStand.class);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setHelmet(skull);

        world.spawnParticle(Particle.DUST, blockLocation, 1, new Particle.DustOptions(Color.ORANGE, 5));

        new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                if (time > 30) {
                    armorStand.remove();
                    this.cancel();
                    return;
                }
                if (time > 10) {
                    Location moveTo = armorStand.getLocation().add(0, 0.2, 0);
                    moveTo.setYaw(moveTo.getYaw() + 10F);
                    armorStand.teleport(moveTo);
                }
                armorStand.getWorld().spawnParticle(Particle.CLOUD, armorStand.getLocation().clone().add(0, 0.5, 0), 3,0 ,0,0,0);
                time ++;
            }
        }.runTaskTimer(GalaxyGraves.getInstance(), 0, 1L);





    }



}
