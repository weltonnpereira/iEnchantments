package voxel.wp.ienchantments.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.holder.RecyclerHolder;
import voxel.wp.ienchantments.manager.PlManager;

import java.util.Arrays;
import java.util.List;

public class RecyclerGUI implements Listener {

    private final PlManager plManager;

    public RecyclerGUI(PlManager plManager) {
        this.plManager = plManager;
    }

    public static final String GUI_TITLE = ChatColor.DARK_GREEN + "Recycler";

    public static final String addAllToRecycler = "Add to Recycler";
    public static final String cancelRecycler = "Cancel";
    public static final String acceptRecycler = "Accept";

    public void openRecyclerInventory(Player player) {
        Inventory i = plManager.getPlugin().getServer().createInventory(new RecyclerHolder(), 54, GUI_TITLE);

        short black = 15;
        ItemStack blackPane = createItem(Material.BLACK_STAINED_GLASS_PANE, black, "", null);
        i.setItem(45, blackPane);
        i.setItem(47, blackPane);
        i.setItem(51, blackPane);
        i.setItem(53, blackPane);

        // Botão para Adicionar Livros
        short purple = 10;
        ItemStack putAllBooks = createItem(
                Material.PURPLE_STAINED_GLASS_PANE,
                purple,
                ChatColor.AQUA + addAllToRecycler,
                Arrays.asList(
                        ChatColor.GRAY + "Click to add all",
                        ChatColor.GRAY + "your books to the recycler"
                )
        );
        ItemMeta bookMeta = putAllBooks.getItemMeta();
        if (bookMeta != null) {
            bookMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            putAllBooks.setItemMeta(bookMeta);
        }
        i.setItem(46, putAllBooks);
        i.setItem(52, putAllBooks);

        // Botão Negar / Cancelar (Vidro Vermelho)
        short red = 14;
        ItemStack deny = createItem(
                Material.RED_STAINED_GLASS_PANE,
                red,
                ChatColor.RED + cancelRecycler,
                Arrays.asList(ChatColor.GRAY + "Click to cancel recycling!")
        );
        i.setItem(48, deny);

        // Frasco de Experiência Reciclada (Mostra o valor total)
        int totalValue = 0;
        ItemStack recyclerBottle = createItem(
                Material.GLASS_BOTTLE,
                (short) 0,
                ChatColor.YELLOW + "Recycled Experience",
                Arrays.asList(
                        ChatColor.WHITE + "Total value: " + ChatColor.GREEN + totalValue,
                        "",
                        ChatColor.GRAY + "All your books will be",
                        ChatColor.GRAY + "transformed into experience with " + totalValue + " XP"
                )
        );
        i.setItem(49, recyclerBottle);

        short green = 5;
        ItemStack accept = createItem(
                Material.GREEN_STAINED_GLASS_PANE,
                green,
                ChatColor.GREEN + acceptRecycler,
                Arrays.asList(
                        ChatColor.GRAY + "Click to recycle all",
                        ChatColor.GRAY + "selected items!"
                )
        );
        i.setItem(50, accept);

        player.openInventory(i);
    }

    /**
     * Método auxiliar para criar itens customizados com cor (durabilidade) de forma limpa.
     */
    private ItemStack createItem(Material material, short data, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, 1, data);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (displayName != null) {
                meta.setDisplayName(displayName);
            }
            if (lore != null) {
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

}
