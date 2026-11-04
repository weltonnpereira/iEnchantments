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

public class SimpleEnchantsGUI implements Listener {

    private final PlManager plManager;

    public SimpleEnchantsGUI(PlManager plManager) {
        this.plManager = plManager;
    }

    public static final String GUI_TITLE = ChatColor.DARK_PURPLE + "Enchantments - Simple Book";

    public void SimpleEnchantInventory(Player player) {
        Inventory i = plManager.getPlugin().getServer().createInventory(null, 54, GUI_TITLE);

        int level1 = 1;
        int level2 = 2;

        i.setItem(10, createEnchantedBookItem(Enchantment.PROTECTION_ENVIRONMENTAL, level1));
        i.setItem(11, createEnchantedBookItem(Enchantment.PROTECTION_ENVIRONMENTAL, level2));
        i.setItem(12, createEnchantedBookItem(Enchantment.PROTECTION_FIRE, level1));
        i.setItem(13, createEnchantedBookItem(Enchantment.PROTECTION_FIRE, level2));
        i.setItem(14, createEnchantedBookItem(Enchantment.OXYGEN, level1));
        i.setItem(15, createEnchantedBookItem(Enchantment.PROTECTION_FALL, level1));
        i.setItem(16, createEnchantedBookItem(Enchantment.PROTECTION_FALL, level2));

        i.setItem(19, createEnchantedBookItem(Enchantment.DEPTH_STRIDER, level1));
        i.setItem(20, createEnchantedBookItem(Enchantment.DAMAGE_ALL, level1));
        i.setItem(21, createEnchantedBookItem(Enchantment.DAMAGE_ALL, level2));
        i.setItem(22, createEnchantedBookItem(Enchantment.LOOT_BONUS_MOBS, level1));
        i.setItem(23, createEnchantedBookItem(Enchantment.DIG_SPEED, level1));
        i.setItem(24, createEnchantedBookItem(Enchantment.DIG_SPEED, level2));
        i.setItem(25, createEnchantedBookItem(Enchantment.DURABILITY, level1));

        i.setItem(28, createEnchantedBookItem(Enchantment.DURABILITY, level2));
        i.setItem(29, createEnchantedBookItem(Enchantment.LOOT_BONUS_BLOCKS, level1));
        i.setItem(30, createEnchantedBookItem(Enchantment.ARROW_DAMAGE, level1));
        i.setItem(31, createEnchantedBookItem(Enchantment.ARROW_DAMAGE, level2));

        // Botão Voltar (Arrow)
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
