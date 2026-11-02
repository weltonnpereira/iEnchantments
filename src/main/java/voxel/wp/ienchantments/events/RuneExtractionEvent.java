package voxel.wp.ienchantments.events;

import voxel.wp.ienchantments.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class RuneExtractionEvent implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    private EnchantmentManager enchantmentManager;

    public RuneExtractionEvent(EnchantmentManager enchantmentManager) {
        this.enchantmentManager = enchantmentManager;
        enchantmentManager.initializeEnchantmentLevels();
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();

        if (itemInHand != null && itemInHand.getType() == Material.NAME_TAG) {
            ItemMeta itemMeta = itemInHand.getItemMeta();

            if (itemMeta != null && itemMeta.hasDisplayName()) {
                String displayName = itemMeta.getDisplayName();
                if (displayName.equals(ChatColor.GOLD + "Runa de Extração")) {
                    event.setCancelled(true);
                    player.updateInventory();
                    player.sendMessage(ChatColor.RED + "Você não pode usar a " + ChatColor.GOLD + "Runa de Extração" +
                            ChatColor.RED + " para nomear entidades.");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if (clickedItem != null && cursorItem != null) {
            if (cursorItem.getType() == Material.NAME_TAG) {
                ItemMeta meta = cursorItem.getItemMeta();

                if (meta != null && meta.hasDisplayName()) {
                    if (meta.getDisplayName().equals(ChatColor.GOLD + "Runa de Extração")) {
                        int amountInCursor = cursorItem.getAmount();
                        if (amountInCursor == 1) {
                            if (hasEnchantments(clickedItem)) {
                                if(canExtractEnchantments(clickedItem)) {
                                    int numEnchantments = clickedItem.getEnchantments().size();

                                    int emptySlot = countEmptySlots(player.getInventory());

                                    if (emptySlot >= numEnchantments) {
                                        event.setCursor(new ItemStack(Material.AIR));

                                        createEnchantedBook(player, clickedItem);

                                        removeEnchantments(clickedItem);

                                        player.getInventory().remove(cursorItem);

                                        player.sendMessage(ChatColor.GREEN + "Os encantamentos foram extraídos com sucesso!");
                                        player.playSound(player.getLocation(), Sound.WITHER_IDLE, 1.0f, 1.0f);

                                        event.setCancelled(true);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Você não tem espaço para os livros extraídos.");
                                        player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
                                        event.setCancelled(true);
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Você não pode extrair esse item.");
                                    player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
                                    event.setCancelled(true);
                                }
                            }
                        } else {
                            if (hasEnchantments(clickedItem)) {
                                if(canExtractEnchantments(clickedItem)) {
                                    int numEnchantments = clickedItem.getEnchantments().size();

                                    int emptySlot = countEmptySlots(player.getInventory());

                                    if (emptySlot >= numEnchantments) {
                                        cursorItem.setAmount(amountInCursor - 1);

                                        createEnchantedBook(player, clickedItem);

                                        removeEnchantments(clickedItem);

                                        player.sendMessage(ChatColor.GREEN + "Os encantamentos foram extraídos com sucesso!");
                                        player.playSound(player.getLocation(), Sound.WITHER_IDLE, 1.0f, 1.0f);

                                        event.setCancelled(true);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Você não tem espaço para os livros extraídos.");
                                        player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
                                        event.setCancelled(true);
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Você não pode extrair esse item.");
                                    player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canExtractEnchantments(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        String itemName = meta.getDisplayName();
        if(itemName != null) {
            List<String> blockedKitColors = plugin.getConfig().getStringList("BlockedItemColors");

            for (String blockedColor : blockedKitColors) {
                ChatColor color = ChatColor.valueOf(blockedColor);
                if(itemName.contains(color.toString())) {
                    return false;
                }
            }
        }

        return true;
    }

    private int countEmptySlots(Inventory inventory) {
        int emptySlots = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                emptySlots++;
            }
        }

        return emptySlots;
    }

    public void createEnchantedBook(Player player, ItemStack itemToExtractFrom) {
        // Loop através dos encantamentos presentes no item de armadura
        for (Map.Entry<Enchantment, Integer> entry : itemToExtractFrom.getEnchantments().entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            // Verificar se o encantamento está na lista de encantamentos a serem processados
            String category = enchantmentManager.getCategory(enchantment, level);

            if (category != null) {
                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

                if (category.equals("advanced")) {
                    bookMeta.setDisplayName(ChatColor.YELLOW + "Livro Avançado");
                    Random random = new Random();
                    int porcentagem = random.nextInt(31) + 50; // 50% a 80%
                    setSuccessChanceLore(bookMeta, porcentagem);
                } else if (category.equals("normal")) {
                    bookMeta.setDisplayName(ChatColor.YELLOW + "Livro Normal");
                    // Defina a porcentagem com base na categoria
                    Random random = new Random();
                    int porcentagem = random.nextInt(41) + 50; // 50% a 90%
                    setSuccessChanceLore(bookMeta, porcentagem);
                } else if (category.equals("simple")) {
                    bookMeta.setDisplayName(ChatColor.YELLOW + "Livro Simples");
                    // Defina a porcentagem com base na categoria
                    Random random = new Random();
                    int porcentagem = random.nextInt(51) + 50; // 50% a 100%
                    setSuccessChanceLore(bookMeta, porcentagem);
                }

                bookMeta.addStoredEnchant(enchantment, level, true);

                enchantedBook.setItemMeta(bookMeta);

                player.getInventory().addItem(enchantedBook);
            }
        }
    }

    private void setSuccessChanceLore(EnchantmentStorageMeta bookMeta, int porcentagem) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (porcentagem <= 50) {
            lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
        } else {
            lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
        }
        lore.add("Encantamento Extraído");
        bookMeta.setLore(lore);
    }

    private boolean hasEnchantments(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasEnchants();
    }

    private void removeEnchantments(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            item.getItemMeta().getEnchants().keySet().forEach(item::removeEnchantment);
        }
    }

}
