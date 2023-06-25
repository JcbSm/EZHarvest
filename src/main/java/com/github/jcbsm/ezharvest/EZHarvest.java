package com.github.jcbsm.ezharvest;

import com.github.jcbsm.ezharvest.listeners.PlayerInteractEventListener;
import com.github.jcbsm.ezharvest.util.CropManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class EZHarvest extends JavaPlugin {

    private final CropManager cropManager = new CropManager();

    @Override
    public void onEnable() {

        // Logger
        getLogger().setLevel(Level.ALL);

        cropManager.init();

        // Register listener
        getLogger().info("Registering PlayerInteractEventListener...");
        getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(), this);

        getLogger().info("Done.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EZHarvest getPlugin() {
        return getPlugin(EZHarvest.class);
    }

    public CropManager getCropManager() {
        return cropManager;
    }

}
