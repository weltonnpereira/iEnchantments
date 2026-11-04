package voxel.wp.ienchantments.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.manager.PlManager;

public class NormalEnchantsGUI implements Listener {

    private final PlManager plManager;

    public NormalEnchantsGUI(PlManager plManager) {
        this.plManager = plManager;
    }

    public static final String GUI_TITLE = ChatColor.DARK_PURPLE + "Enchantments - Normal Book";

    public void NormalEnchantInventory(Player player) {
        Inventory i = plManager.getPlugin().getServer().createInventory(null, 54, GUI_TITLE);

        // Normal Enchantment Items
        i.setItem(10, createEnchantedBookItem(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
        i.setItem(11, createEnchantedBookItem(Enchantment.PROTECTION_ENVIRONMENTAL, 4));
        i.setItem(12, createEnchantedBookItem(Enchantment.PROTECTION_FIRE, 3));
        i.setItem(13, createEnchantedBookItem(Enchantment.PROTECTION_FALL, 3));
        i.setItem(14, createEnchantedBookItem(Enchantment.DEPTH_STRIDER, 2));
        i.setItem(15, createEnchantedBookItem(Enchantment.DAMAGE_ALL, 3));
        i.setItem(16, createEnchantedBookItem(Enchantment.DAMAGE_ALL, 4));

        i.setItem(19, createEnchantedBookItem(Enchantment.LOOT_BONUS_MOBS, 2));
        i.setItem(20, createEnchantedBookItem(Enchantment.DIG_SPEED, 3));
        i.setItem(21, createEnchantedBookItem(Enchantment.DIG_SPEED, 4));
        i.setItem(22, createEnchantedBookItem(Enchantment.DURABILITY, 3));
        i.setItem(23, createEnchantedBookItem(Enchantment.DURABILITY, 4));
        i.setItem(24, createEnchantedBookItem(Enchantment.LOOT_BONUS_BLOCKS, 2));
        i.setItem(25, createEnchantedBookItem(Enchantment.ARROW_DAMAGE, 3));

        i.setItem(28, createEnchantedBookItem(Enchantment.ARROW_DAMAGE, 4));
        i.setItem(29, createEnchantedBookItem(Enchantment.FIRE_ASPECT, 1));
        i.setItem(30, createEnchantedBookItem(Enchantment.ARROW_KNOCKBACK, 1));
        i.setItem(31, createEnchantedBookItem(Enchantment.OXYGEN, 2));

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
