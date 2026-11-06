package voxel.wp.ienchantments.gui;

import voxel.wp.ienchantments.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.holder.AdvancedEnchantHolder;
import voxel.wp.ienchantments.manager.PlManager;

public class AdvancedEnchantsGUI implements Listener {

    private final PlManager plManager;

    public AdvancedEnchantsGUI(PlManager plManager) {
        this.plManager = plManager;
    }

    public static final String GUI_TITLE = ChatColor.DARK_PURPLE + "Enchantments - Advanced Book";

    public void AdvancedEnchantInventory(Player player) {
        Inventory i = plManager.getPlugin().getServer().createInventory(new AdvancedEnchantHolder(), 54, GUI_TITLE);

        // Advanced Enchantment Items
        i.setItem(10, createEnchantedBookItem(Enchantment.PROTECTION_ENVIRONMENTAL, 5));
        i.setItem(11, createEnchantedBookItem(Enchantment.PROTECTION_FIRE, 4));
        i.setItem(12, createEnchantedBookItem(Enchantment.DURABILITY, 5));
        i.setItem(13, createEnchantedBookItem(Enchantment.DAMAGE_ALL, 5));
        i.setItem(14, createEnchantedBookItem(Enchantment.OXYGEN, 3));
        i.setItem(15, createEnchantedBookItem(Enchantment.PROTECTION_FALL, 4));
        i.setItem(16, createEnchantedBookItem(Enchantment.DEPTH_STRIDER, 3));

        i.setItem(19, createEnchantedBookItem(Enchantment.LOOT_BONUS_MOBS, 3));
        i.setItem(20, createEnchantedBookItem(Enchantment.DIG_SPEED, 5));
        i.setItem(21, createEnchantedBookItem(Enchantment.LOOT_BONUS_BLOCKS, 3));
        i.setItem(22, createEnchantedBookItem(Enchantment.ARROW_DAMAGE, 5));
        i.setItem(23, createEnchantedBookItem(Enchantment.ARROW_INFINITE, 1));
        i.setItem(24, createEnchantedBookItem(Enchantment.FIRE_ASPECT, 2));
        i.setItem(25, createEnchantedBookItem(Enchantment.ARROW_FIRE, 1));

        i.setItem(28, createEnchantedBookItem(Enchantment.SILK_TOUCH, 1));
        i.setItem(29, createEnchantedBookItem(Enchantment.ARROW_KNOCKBACK, 2));
        i.setItem(30, createEnchantedBookItem(Enchantment.WATER_WORKER, 1));

        // Back Button (Arrow)
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        if (arrowBackMeta != null) {
            arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Back");
            arrowBack.setItemMeta(arrowBackMeta);
        }
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

    private ItemStack createEnchantedBookItem(Enchantment enchantment, int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        if (meta != null) {
            meta.addStoredEnchant(enchantment, level, true);
            book.setItemMeta(meta);
        }
        return book;
    }

}
