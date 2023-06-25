package github.jcbsm.ezharvest.listeners;

import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEventListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        // Ignore left hand clicks
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        // Get item in main hand
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        Material itemType = item.getType();

        // Only listen if player is holding a hoe
        if (!(
                   itemType == Material.WOODEN_HOE
                || itemType == Material.STONE_HOE
                || itemType == Material.IRON_HOE
                || itemType == Material.GOLDEN_HOE
                || itemType == Material.DIAMOND_HOE
                || itemType == Material.NETHERITE_HOE
        )) return;

        // Get age
        Ageable ageable;

        // Check if the target is a crop.
        try {
            ageable = (Ageable) event.getClickedBlock().getState().getBlockData();
        } catch (Exception e) {
            // If it cannot cast, return
            return;
        }

        // If the crop is fully grown
        if (ageable.getAge() == 7) {

            // Harvest Crop

            // Reduce durability

        }
    }
}
