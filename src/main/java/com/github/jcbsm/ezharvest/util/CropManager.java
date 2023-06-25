package com.github.jcbsm.ezharvest.util;

import com.github.jcbsm.ezharvest.EZHarvest;
import com.github.jcbsm.ezharvest.croptypes.Harvestable;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;

public class CropManager {

    private final Map<Material, Harvestable> map = new HashMap<>();

    /**
     * Initialises the manager map
     */
    public void init() {

        Logger logger = EZHarvest.getPlugin().getLogger();

        logger.info("Initialising CropManger...");

        // Get services
        ServiceLoader<Harvestable> loader = ServiceLoader.load(Harvestable.class, EZHarvest.class.getClassLoader());

        // for each
        for (Harvestable service: loader) {

            logger.info(String.format("Mapping materials for %s", service.getClass().getSimpleName()));

            // Get the annotation
            var mapping = service.getClass().getAnnotation(Crops.class);

            // If there is no annotation, error
            if (mapping == null) {
                logger.warning((String.format("Class '%s' didn't have HarvestMapping annotation.", service.getClass().getSimpleName())));
                continue;
            }

            // Put values into the map
            for (Material material : mapping.value()) {
                logger.info(String.format("\tAdding %s", material));
                map.put(material, service);
            }
        }
    }

    /**
     * Attempts to harvest a block
     * @param player Player harvesting
     * @param block Block to be harvested
     * @return If the harvest was successful.
     */
    public boolean attemptHarvest(@NotNull Player player, @NotNull Block block) {

        // Get Crop type
        var crop = map.get(block.getType());

        // Check if it is a crop, and if it's harvestable
        if (crop == null || !crop.isHarvestable(block)) {
            return false;
        }

        crop.harvest(player, block);
        return true;

    }
}
