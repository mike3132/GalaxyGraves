package co.mcGalaxy.galaxyGraves.managers;

import co.mcGalaxy.galaxyGraves.objects.Grave;
import co.mcGalaxy.galaxyGraves.objects.Model;
import co.mcGalaxy.galaxyGraves.objects.Npc;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Location;

public class GraveManager {

    private final Table<Location, Npc, Model> graves = HashBasedTable.create();

    public GraveManager() {
    }

    public void add(Grave grave) {
        this.graves.put(grave.getLocation(), grave.getNpc(), grave.getModel());
    }

    public void remove(Grave grave) {
        this.graves.remove(grave.getLocation(), grave.getNpc());
    }

    public Table<Location, Npc, Model> getGraves() {
        return graves;
    }

}
