package co.mcGalaxy.galaxyGraves.grave;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.Pig;

public class Model {

    private final Location location;
    public Pig pig;
    private final String modelId = GalaxyGraves.getInstance().getConfig().getString("Model-Engine-Grave-Name");

    public Model(Location location) {
        this.location = location;
    }

    public void spawn(Location location) {
        Pig pig = this.location.getWorld().spawn(this.location, Pig.class);
        pig.getLocation().setDirection(this.location.clone().toVector());

        pig.setAI(false);
        pig.setInvisible(true);
        pig.setSilent(true);
        pig.setInvulnerable(true);
        pig.setGravity(false);

        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(pig);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(modelId);

        modeledEntity.addModel(activeModel, true);
        this.pig = pig;
    }

    public void remove(Location location) {
        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(this.pig);

        this.pig.remove();
        modeledEntity.removeModel(modelId);
    }

    public Location getLocation() {
        return location;
    }

    public Pig getPig() {
        return pig;
    }
}


