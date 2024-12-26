package co.mcGalaxy.galaxyGraves.managers;

import co.mcGalaxy.galaxyGraves.objects.Grave;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Location;

import java.util.UUID;

public class GraveManager {

    private final Table<UUID, Location, Grave> graves = HashBasedTable.create();

    public GraveManager() {
    }

    public void add(Grave grave) {
        this.graves.put(grave.getUuid(), grave.getLocation(), grave.getGrave());
    }

    public void remove(Grave grave) {
        this.graves.remove(grave.getUuid(), grave.getLocation());
    }

    public Table<UUID, Location, Grave> getGraves() {
        return graves;
    }
}
