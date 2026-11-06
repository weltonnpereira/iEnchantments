package voxel.wp.ienchantments.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RecyclerManager {

    private final PlManager plManager;
    private final Random random = new Random();

    public RecyclerManager(PlManager plManager) {
        this.plManager = plManager;
    }

    /**
     * Calcula o valor de XP reciclado com base no custo base do livro (15% a 60%).
     */
    public int calculateRecycleValue(String bookType) {
        int cost = plManager.getFlaskBookManager().getBookCost(bookType);
        double proportionMin = 0.15;
        double proportionMax = 0.60;

        int minValue = (int) (cost * proportionMin);
        int maxValue = (int) (cost * proportionMax);

        if (maxValue > cost) {
            maxValue = cost;
        }

        int recyclerValue = random.nextInt(maxValue - minValue + 1) + minValue;
        return Math.min(recyclerValue, cost - 1);
    }

    /**
     * Atualiza o frasco de XP central (slot 49) com o valor total acumulado.
     */
    public void updateRecyclerBottle(Player player, int totalValue) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int flaskPosition = 49;

        if (recyclerInventory == null) return;
        ItemStack recyclerBottle = recyclerInventory.getItem(flaskPosition);

        if (recyclerBottle != null) {
            ItemMeta meta = recyclerBottle.getItemMeta();
            if (meta == null) return;

            if (totalValue == 0) {
                recyclerBottle.setType(Material.GLASS_BOTTLE);
                meta.setLore(null);
            } else {
                recyclerBottle.setType(Material.EXPERIENCE_BOTTLE);
                List<String> lore = meta.getLore();
                if (lore != null) {
                    String lineTotalValue = ChatColor.WHITE + "Total value: ";
                    for (int i = 0; i < lore.size(); i++) {
                        String line = lore.get(i);
                        if (line.startsWith(lineTotalValue)) {
                            lore.set(i, lineTotalValue + ChatColor.GREEN + plManager.formatExp(totalValue) + " XP");
                            if (i + 3 < lore.size()) {
                                lore.set(i + 1, "");
                                lore.set(i + 2, ChatColor.GRAY + "All your books will be");
                                lore.set(i + 3, ChatColor.GRAY + "transformed into experience with " + plManager.formatExp(totalValue) + " XP");
                            }
                            break;
                        }
                    }
                    meta.setLore(lore);
                }
            }
            recyclerBottle.setItemMeta(meta);
            recyclerInventory.setItem(flaskPosition, recyclerBottle);
        }
    }

    /**
     * Extrai o valor total de XP armazenado no lore do frasco no slot 49.
     */
    public int getTotalValueFromRecyclerBottle(Player player) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int flaskPosition = 49;

        if (recyclerInventory != null) {
            ItemStack recyclerBottle = recyclerInventory.getItem(flaskPosition);
            if (recyclerBottle != null && recyclerBottle.getType() == Material.EXPERIENCE_BOTTLE) {
                ItemMeta meta = recyclerBottle.getItemMeta();
                if (meta == null || !meta.hasLore()) return 0;

                List<String> lore = meta.getLore();
                String lineTotalValue = ChatColor.WHITE + "Total value: " + ChatColor.GREEN;

                for (String line : lore) {
                    if (line.startsWith(lineTotalValue)) {
                        String valueStr = line.substring(lineTotalValue.length())
                                .replace(" XP", "")
                                .replaceAll("[.,]", "")
                                .trim();
                        try {
                            return Integer.parseInt(valueStr);
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Gerencia o clique no botão "Add to Recycler" (move todos os livros do inventário do player para o Reciclador).
     */
    public void addAllBooksToRecycler(Player player) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        Inventory playerInv = player.getInventory();
        int accumulatedValue = getTotalValueFromRecyclerBottle(player);

        EnchantmentManager enchantMgr = plManager.getEnchantmentManager();

        for (int i = 0; i < playerInv.getSize(); i++) {
            ItemStack item = playerInv.getItem(i);

            String bookType = enchantMgr.getEnchantedBookTypeFromItem(item);

            if (bookType != null) {
                int emptySlot = recyclerInventory.firstEmpty();
                if (emptySlot == -1 || emptySlot >= 45) {
                    player.sendMessage(ChatColor.RED + "The recycler inventory is full.");
                    break;
                }

                recyclerInventory.setItem(emptySlot, item);
                playerInv.setItem(i, null);

                accumulatedValue += calculateRecycleValue(bookType);
            }
        }
        updateRecyclerBottle(player, accumulatedValue);
    }

    /**
     * Devolve os livros ao fechar o inventário ou cancelar.
     */
    public void returnBooksToPlayer(Player player, Inventory recyclerInventory) {
        Inventory playerInv = player.getInventory();

        for (int slot = 0; slot < 45; slot++) {
            ItemStack item = recyclerInventory.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {

                HashMap<Integer, ItemStack> leftover = playerInv.addItem(item);

                if (!leftover.isEmpty()) {
                    for (Map.Entry<Integer, ItemStack> entry : leftover.entrySet()) {
                        ItemStack overflowItem = entry.getValue();
                        if (overflowItem != null && overflowItem.getType() != Material.AIR) {
                            player.getWorld().dropItem(player.getLocation(), overflowItem);
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove permanentemente os livros reciclados ao clicar em "Accept".
     */
    public void clearRecyclerBooks(Inventory recyclerInventory) {
        for (int slot = 0; slot < 45; slot++) {
            ItemStack item = recyclerInventory.getItem(slot);
            if (item != null && item.getType() == Material.ENCHANTED_BOOK) {
                recyclerInventory.setItem(slot, null);
            }
        }
    }

    public boolean isInventoryFull(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return false;
            }
        }
        return true;
    }
}