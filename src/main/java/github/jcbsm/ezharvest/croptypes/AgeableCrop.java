package github.jcbsm.ezharvest.croptypes;

import github.jcbsm.ezharvest.util.Crops;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Crops({
        Material.WHEAT,
        Material.BEETROOT,
        Material.CARROT,
        Material.POTATO,
        Material.COCOA_BEANS,
        Material.SWEET_BERRIES,
        Material.NETHER_WART
})
public class AgeableCrop implements Harvestable {
    @Override
    public boolean isHarvestable(@NotNull Player player, @NotNull Block block) {
        try {
            Ageable ageable = (Ageable) block;
            return ageable.getAge() == ageable.getMaximumAge();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Block %s does not have age", block));
        }
    }

    @Override
    public void drops() {

    }

    @Override
    public void replant(@NotNull Block block) {
        try {
            Ageable ageable = (Ageable) block;
            ageable.setAge(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Block %s does not have age", block));
        }
    }
}
