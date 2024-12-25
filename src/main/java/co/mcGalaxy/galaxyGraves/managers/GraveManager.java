package co.mcGalaxy.galaxyGraves.managers;

import co.mcGalaxy.galaxyGraves.utils.Grave;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class GraveManager {

    private final Map<Location, Grave> graves = new HashMap<>();

    public GraveManager() {
    }

    public void registerGrave(Grave grave) {
        this.graves.put(grave.getLocation(), grave);
        grave.spawn(grave.getLocation());
        Bukkit.broadcastMessage("Putting grave at " + grave.getLocation());
    }

    public void unRegisterGrave(Grave grave) {
        if (this.graves.isEmpty()) {
            Bukkit.broadcastMessage("Graves map is empty");
            return;
        }
        this.graves.remove(grave.getLocation());
        grave.remove(grave.getLocation());
    }


    public Map<Location, Grave> getGraves() {
        return graves;
    }



}
