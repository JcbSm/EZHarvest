package com.github.jcbsm.ezharvest.croptypes;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A Harvestable is a crop which can be harvested using a hoe.
 */
public interface Harvestable {

    /**
     * Whether a block is harvestable
     * @param block The block to check
     * @return Boolean
     */
    boolean isHarvestable(@NotNull Block block);

    /**
     * Harvests the target block
     * @param player Player who harvested
     * @param block Block to harvest
     */
    default void harvest(@NotNull Player player, @NotNull Block block) {
        drops(block);
        replant(block);
    };

    /**
     * Handles drops
     * @param block Block to get drops from
     */
    void drops(@NotNull Block block);

    /**
     * Handles the "replanting" of the crop
     * @param block Block to replant
     */
    void replant(@NotNull Block block);
}
