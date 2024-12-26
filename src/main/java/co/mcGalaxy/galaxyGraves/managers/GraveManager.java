package co.mcGalaxy.galaxyGraves.managers;

import co.mcGalaxy.galaxyGraves.utils.ModelUtils;
import co.mcGalaxy.galaxyGraves.utils.NpcUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GraveManager {

    private final Table<Location, NpcUtils, ModelUtils> graves = HashBasedTable.create();

    public GraveManager() {
    }

    public void registerGrave(NpcUtils npcUtils, ModelUtils modelUtils) {
        npcUtils.spawn(npcUtils.getLocation());
        modelUtils.spawn(modelUtils.getLocation());
        this.graves.put(npcUtils.getLocation(), npcUtils, modelUtils);
    }

    public void unRegisterGrave(NpcUtils npcUtils, ModelUtils modelUtils) {
        if (this.graves.isEmpty()) {
            Bukkit.broadcastMessage("Graves map is empty");
            return;
        }
        npcUtils.remove(npcUtils.getLocation());
        modelUtils.remove(modelUtils.getLocation());
        this.graves.remove(npcUtils.getLocation(), npcUtils);
    }


    public Table<Location, NpcUtils, ModelUtils> getGraves() {
        return graves;
    }
}
