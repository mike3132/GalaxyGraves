package co.mcGalaxy.galaxyGraves.events;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityEvent implements Listener {

    private final GalaxyGraves plugin;

    public EntityEvent(GalaxyGraves plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent ete) {
        Entity entity = ete.getEntity();
        Location entityLocation = entity.getLocation();

        if (!this.plugin.graveManager.getGraves().containsColumn(entityLocation)) return;
        ete.setCancelled(true);
    }


}
