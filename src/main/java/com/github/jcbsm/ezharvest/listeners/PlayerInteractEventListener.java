package com.github.jcbsm.ezharvest.listeners;

import com.github.jcbsm.ezharvest.EZHarvest;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;

public class PlayerInteractEventListener implements Listener {

    private final Random random = new Random();

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        // Ignore left hand clicks
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) return;

        // Get item in main hand
        ItemStack item = player.getInventory().getItemInMainHand();
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

        // Harvest Crop
        if (EZHarvest.getPlugin().getCropManager().attemptHarvest(player, block)) {

            // Play sound
            player.getWorld().playSound(block.getLocation(), Sound.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1, 1);

            // Swing hand
            player.swingMainHand();

            // End if in creative, no durability loss.
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            // Reduce durability
            damage(item);

            // check if item is broken
            if (((Damageable) player.getInventory().getItemInMainHand().getItemMeta()).getDamage() > player.getInventory().getItemInMainHand().getType().getMaxDurability()) {
                player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
            }
        }
    }

    /**
     * Damages the tool, taking into account unbreaking
     * @param item Item to damage
     */
    private void damage(ItemStack item) {

        // Get meta
        Damageable meta = (Damageable) item.getItemMeta();

        // Adjust damage accordingly
        meta.setDamage(damageTaken(meta) ? meta.getDamage() + 1 : meta.getDamage());

        // Set value back
        item.setItemMeta(meta);


    }

    /**
     * Calculates if damage should be dealt
     * @param damageable Metadata to examine
     * @return Boolean
     */
    private boolean damageTaken(Damageable damageable) {
        return random.nextDouble() < (double) 1 / (damageable.getEnchantLevel(Enchantment.DURABILITY) + 1.0);
    }
}
