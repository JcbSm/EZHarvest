package github.jcbsm.ezharvest.croptypes;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Harvestable {

    boolean isHarvestable(@NotNull Player player, @NotNull Block block);

    default void harvest(@NotNull Player player, @NotNull Block block) {
        drops();
        replant(block);
    };

    void drops();
    void replant(Block block);
}
