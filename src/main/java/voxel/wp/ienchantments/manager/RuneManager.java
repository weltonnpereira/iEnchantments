package voxel.wp.ienchantments.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static voxel.wp.ienchantments.gui.MainGUI.*;

public class RuneManager {

    private final PlManager plManager;
    public final String extractionRuneName = ChatColor.GOLD + "Extraction Rune";

    public RuneManager(PlManager plManager) {
        this.plManager = plManager;
    }

    /**
     * Entrega a Runa de Extração ao jogador.
     */
    public void giveExtractionRune(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Your inventory is full.");
            return;
        }

        ItemStack rune = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = rune.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(extractionRuneName);
            meta.setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Use on an item to",
                    ChatColor.GRAY + "remove its " + ChatColor.WHITE + "enchantments",
                    "",
                    ChatColor.GREEN + "Success chance: 100%"
            ));

            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rune.setItemMeta(meta);
        }

        player.getInventory().addItem(rune);
    }

    /**
     * Verifica se o item fornecido é a Runa de Extração.
     */
    public boolean isExtractionRune(ItemStack item) {
        if (item != null && item.getType() == Material.NAME_TAG && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasDisplayName() && meta.getDisplayName().equals(extractionRuneName);
        }
        return false;
    }

    /**
     * Verifica se o item pode ter seus encantamentos extraídos (checa itens bloqueados na config).
     */
    public boolean canExtractEnchantments(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        String itemName = meta.getDisplayName();
        if (itemName != null) {
            List<String> blockedKitColors = plManager.getPlugin().getConfig().getStringList("BlockedItemColors");

            for (String blockedColor : blockedKitColors) {
                try {
                    ChatColor color = ChatColor.valueOf(blockedColor);
                    if (itemName.contains(color.toString())) {
                        return false;
                    }
                } catch (IllegalArgumentException ignored) {}
            }
        }
        return true;
    }

    /**
     // Conta quantos slots vazios o jogador possui no inventário principal.
     */
    public int countEmptySlots(Inventory inventory) {
        int emptySlots = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                emptySlots++;
            }
        }
        return emptySlots;
    }

    // Verifica se o item possui encantamentos.
    public boolean hasEnchantments(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasEnchants();
    }

    /**
     * Remove todos os encantamentos de um item.
     */
    public void removeEnchantments(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            item.getItemMeta().getEnchants().keySet().forEach(item::removeEnchantment);
        }
    }

    /**
     * Cria e adiciona os livros encantados extraídos ao inventário do jogador.
     */
    public void extractEnchantments(Player player, ItemStack clickedItem) {
        Random random = new Random();
        for (Map.Entry<Enchantment, Integer> entry : clickedItem.getEnchantments().entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            String category = plManager.getEnchantmentManager().getCategory(enchantment, level);

            if (category != null) {
                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

                int percentage;
                if (category.equals(eBookAdvancedType)) {
                    bookMeta.setDisplayName(ChatColor.YELLOW + eBookAdvancedType);
                    percentage = random.nextInt(31) + 50; // 50% to 80%
                } else if (category.equals(eBookNormalType)) {
                    bookMeta.setDisplayName(ChatColor.YELLOW + eBookNormalType);
                    percentage = random.nextInt(41) + 50; // 50% to 90%
                } else {
                    bookMeta.setDisplayName(ChatColor.YELLOW + eBookSimpleType);
                    percentage = random.nextInt(51) + 50; // 50% to 100%
                }

                setSuccessChanceLore(bookMeta, percentage);
                bookMeta.addStoredEnchant(enchantment, level, true);
                enchantedBook.setItemMeta(bookMeta);

                player.getInventory().addItem(enchantedBook);
            }
        }
    }

    private void setSuccessChanceLore(EnchantmentStorageMeta bookMeta, int percentage) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (percentage <= 50) {
            lore.add(ChatColor.RED + "Success chance: " + percentage + "%");
        } else {
            lore.add(ChatColor.GREEN + "Success chance: " + percentage + "%");
        }
        lore.add("Extracted Enchantment");
        bookMeta.setLore(lore);
    }
}