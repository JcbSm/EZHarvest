package com.github.jcbsm.ezharvest;

import com.github.jcbsm.ezharvest.listeners.PlayerInteractEventListener;
import com.github.jcbsm.ezharvest.util.CropManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EzHarvest extends JavaPlugin {

    private CropManager cropManager = new CropManager();

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("Initialising EZHarvest Crop Manager...");
        cropManager.init();
        cropManager.debug();

        // Register listener
        getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EzHarvest getPlugin() {
        return getPlugin(EzHarvest.class);
    }

    public CropManager getCropManager() {
        return cropManager;
    }
}
