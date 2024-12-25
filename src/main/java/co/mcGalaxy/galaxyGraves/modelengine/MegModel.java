package co.mcGalaxy.galaxyGraves.modelengine;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;

public class MegModel {


    public void createMegModel(Location location) {
        Pig pig = location.getWorld().spawn(location, Pig.class);

        pig.setAI(false);
        pig.setInvisible(true);
        pig.setSilent(true);
        pig.setInvulnerable(true);

        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(pig);

        // Make this a configuration option for the type of gravestone it makes
        ActiveModel activeModel = ModelEngineAPI.createActiveModel("devstone");

        modeledEntity.addModel(activeModel, true);
    }

    // Model engine developers need to fix this remove method
    public void removeMegModel(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 10, 10, 10)) {
            if (!(entity instanceof Pig)) continue;

            ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);

            // Make this a configuration option for the type of gravestone it makes
            modeledEntity.removeModel("devstone");
            entity.remove();
        }
    }


}
