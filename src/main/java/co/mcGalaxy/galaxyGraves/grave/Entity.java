package co.mcGalaxy.galaxyGraves.grave;

import org.bukkit.Location;
import org.bukkit.entity.Interaction;


public class Entity {

    private final Location location;
    public Interaction interaction;

    public Entity(Location location) {
        this.location = location;
    }

    public void spawn(Location location) {
        this.interaction = this.location.getWorld().spawn(this.location, Interaction.class, entity -> {
            entity.setInteractionHeight(1);
            entity.setInteractionWidth(1);
            entity.setResponsive(true);
        });
    }

    public void remove(Location location) {
        this.interaction.remove();
    }


    public Location getLocation() {
        return location;
    }

    public Interaction getInteraction() {
        return interaction;
    }
}
