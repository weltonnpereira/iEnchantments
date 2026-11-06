package voxel.wp.ienchantments.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.holder.MainHolder;
import voxel.wp.ienchantments.manager.PlManager;

import java.util.Arrays;
import java.util.List;

public class MainGUI {

    private final PlManager plManager;

    public MainGUI(PlManager plManager) {
        this.plManager = plManager;
    }

    public static final String GUI_TITLE = ChatColor.DARK_PURPLE + "Sorcerer";

    public static final String totalFlaskType = "Experience Flask";
    public static final String advancedFlaskType = "Advanced Experience Flask";
    public static final String normalFlaskType = "Normal Experience Flask";
    public static final String simpleFlaskType = "Simple Experience Flask";

    public static final String eBookSimpleType = "Simple";
    public static final String eBookNormalType = "Normal";
    public static final String eBookAdvancedType = "Advanced";

    public int getEBookAdvancedCost() {
        return plManager.getPlugin().getConfig().getInt("Book.Cost.Advanced", 3500);
    }

    public int getEBookNormalCost() {
        return plManager.getPlugin().getConfig().getInt("Book.Cost.Normal", 2000);
    }

    public int getEBookSimpleCost() {
        return plManager.getPlugin().getConfig().getInt("Book.Cost.Simple", 1500);
    }

    public int getXpAdvancedCost() {
        return plManager.getPlugin().getConfig().getInt("Flask.Cost.Advanced", 5000);
    }

    public int getXpNormalCost() {
        return plManager.getPlugin().getConfig().getInt("Flask.Cost.Normal", 3000);
    }

    public int getXpSimpleCost() {
        return plManager.getPlugin().getConfig().getInt("Flask.Cost.Simple", 1500);
    }

    public void openEnchantInventory(Player player) {
        Inventory inv = Bukkit.createInventory(new MainHolder(), 45, GUI_TITLE);
        int totalXp = player.getTotalExperience();

        inv.setItem(13, createItem(
                Material.MINECART,
                ChatColor.YELLOW + "Your XP",
                Arrays.asList(ChatColor.WHITE + "Actual XP: " + ChatColor.GRAY + plManager.formatExp(totalXp))
        ));

        setupExpBottleItem(inv, 19, simpleFlaskType, "Flask.Cost.Simple");
        setupExpBottleItem(inv, 20, normalFlaskType, "Flask.Cost.Normal");
        setupExpBottleItem(inv, 28, normalFlaskType, "Flask.Cost.Advanced");

        setupTotalXpBottleItem(inv, 29, totalFlaskType, totalXp);

        setupBookItem(inv, 24, eBookSimpleType, "Book.Cost.Simple");
        setupBookItem(inv, 25, eBookNormalType, "Book.Cost.Normal");
        setupBookItem(inv, 33, eBookAdvancedType, "Book.Cost.Advanced");

        setupRecycleItem(inv, 40);

        player.openInventory(inv);
    }

    private void setupExpBottleItem(Inventory inv, int slot, String name, String configCost) {
        int cost = plManager.getPlugin().getConfig().getInt(configCost);
        String formattedCost = plManager.formatExp(cost);

        ItemStack item = createItem(
                Material.EXPERIENCE_BOTTLE,
                ChatColor.YELLOW + name,
                Arrays.asList(
                        ChatColor.WHITE + "Cost: " + ChatColor.GREEN + formattedCost + " XP",
                        "",
                        ChatColor.GRAY + "You receive a exp flask ",
                        ChatColor.GRAY + "with " + formattedCost + " XP"
                )
        );
        inv.setItem(slot, item);
    }

    private void setupTotalXpBottleItem(Inventory inv, int slot, String name, int playerXp) {
        String formattedXp = plManager.formatExp(playerXp);

        if (playerXp > 0) {
            ItemStack item = createItem(
                    Material.EXPERIENCE_BOTTLE,
                    ChatColor.YELLOW + name,
                    Arrays.asList(
                            ChatColor.WHITE + "Cost: " + ChatColor.GREEN + formattedXp + " XP",
                            "",
                            ChatColor.GRAY + "You receive a exp flask ",
                            ChatColor.GRAY + "with " + formattedXp + " XP"
                    )
            );
            inv.setItem(slot, item);
        } else {
            ItemStack item = createItem(
                    Material.GLASS_BOTTLE,
                    ChatColor.YELLOW + name,
                    Arrays.asList(
                            ChatColor.WHITE + "Custo: " + ChatColor.GREEN + formattedXp + " XP",
                            "",
                            ChatColor.RED + "Você não possui XP",
                            ChatColor.RED + "para ser coletado!"
                    )
            );
            inv.setItem(slot, item);
        }
    }

    private void setupBookItem(Inventory inv, int slot, String configType, String configKey) {
        int cost = plManager.getPlugin().getConfig().getInt(configKey);
        String formattedCost = plManager.formatExp(cost);

        ItemStack item = createItem(
                Material.BOOK,
                ChatColor.YELLOW + "Enchantment Book",
                Arrays.asList(
                        ChatColor.GRAY + "Type: " + ChatColor.WHITE + configType,
                        "",
                        ChatColor.GRAY + "Right-click " + ChatColor.GRAY + "to view ",
                        ChatColor.GRAY + "the " + configType.toLowerCase() + " enchantments",
                        "",
                        ChatColor.WHITE + "Cost: " + ChatColor.GREEN + formattedCost + " XP"
                )
        );
        inv.setItem(slot, item);
    }

    private void setupRecycleItem(Inventory inv, int slot) {
        ItemStack item = createItem(
                Material.HOPPER,
                RecyclerGUI.GUI_TITLE,
                Arrays.asList(
                        ChatColor.GRAY + "Click to recycle",
                        ChatColor.GRAY + "your enchanted books"
                )
        );

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        inv.setItem(slot, item);
    }

    /**
     * Utilitário genérico para criação rápida de ItemStack com Lore.
     */
    private ItemStack createItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
