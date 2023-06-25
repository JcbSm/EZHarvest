package github.jcbsm.ezharvest.util;

import github.jcbsm.ezharvest.EzHarvest;
import github.jcbsm.ezharvest.croptypes.Harvestable;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class CropManager {

    private final Map<Material, Harvestable> mapper = new HashMap<>();

    public void init() {

        System.out.println("Getting services...");
        var services = ServiceLoader.load(Harvestable.class);

        System.out.println(services);

        for (var service: services) {
            System.out.println(service);
        }
    }

}
