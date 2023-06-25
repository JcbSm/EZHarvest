package com.github.jcbsm.ezharvest.croptypes;

import com.github.jcbsm.ezharvest.util.Crops;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.jetbrains.annotations.NotNull;

/**
 * Ageable Crops are crops which age themselves, growing from a seed.
 */
@Crops({
        Material.WHEAT,
        Material.BEETROOTS,
        Material.CARROTS,
        Material.POTATOES,
        Material.COCOA,
        Material.NETHER_WART
})
public class AgeableCrop implements Harvestable {
    @Override
    public boolean isHarvestable(@NotNull Block block) {
        try {
            // Check if the block data can be cast as ageable
            Ageable ageable = (Ageable) block.getBlockData();

            // If so, return if it's at maximum age or not.
            return ageable.getAge() == ageable.getMaximumAge();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Block %s does not have age", block));
        }
    }

    @Override
    public void drops(@NotNull Block block) {

        // Get block data
        Material seed = getSeed(block.getType());
        Location location = block.getLocation();
        World world = block.getWorld();

        // Remove 1 seed from the drop pool & drop them naturally.
        block.getDrops().stream()
                .peek(item -> item.setAmount(item.getAmount() - (item.getType() == seed ? 1 : 0)))
                .filter(item -> item.getAmount() != 0)
                .forEach(item -> world.dropItemNaturally(location, item));
    }

    @Override
    public void replant(@NotNull Block block) {
        try {
            // Get block data
            Ageable ageable = (Ageable) block.getBlockData();
            // Set age to 0
            ageable.setAge(0);
            // Update block
            block.setBlockData(ageable);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Block %s does not have age", block));
        }
    }

    /**
     * Gets the seed for a given crop
     * @param material Crop to get seed for
     * @return The seed material
     */
    private Material getSeed(Material material) {
        return switch (material) {
            case WHEAT -> Material.WHEAT_SEEDS;
            case BEETROOTS -> Material.BEETROOT_SEEDS;
            case CARROTS -> Material.CARROT;
            case POTATOES -> Material.POTATO;
            case COCOA -> Material.COCOA_BEANS;
            case NETHER_WART -> Material.NETHER_WART;
            default -> Material.AIR;
        };
    }
}
