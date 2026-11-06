package voxel.wp.ienchantments.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import voxel.wp.ienchantments.gui.*;

import voxel.wp.ienchantments.holder.EnchantGUIHolder;
import voxel.wp.ienchantments.holder.MainHolder;
import voxel.wp.ienchantments.holder.RecyclerHolder;
import voxel.wp.ienchantments.manager.EnchantmentManager;
import voxel.wp.ienchantments.manager.PlManager;
import voxel.wp.ienchantments.manager.RecyclerManager;
import voxel.wp.ienchantments.manager.RuneManager;

import java.util.List;

import static voxel.wp.ienchantments.gui.MainGUI.*;

public class InventoryEvent implements Listener {

    private PlManager plManager;

    public InventoryEvent(PlManager plManager) {
        this.plManager = plManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        Inventory open = player.getOpenInventory().getTopInventory();

        if (open == null) return;

        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        // Apply book enchantments via cursor
        if (cursorItem != null && cursorItem.getType() == Material.ENCHANTED_BOOK && cursorItem.hasItemMeta()) {
            ItemMeta cursorMeta = cursorItem.getItemMeta();
            if (cursorMeta.hasDisplayName() && cursorMeta.hasLore()) {
                String displayName = cursorMeta.getDisplayName();
                if (displayName.equals(ChatColor.YELLOW + eBookSimpleType) ||
                        displayName.equals(ChatColor.YELLOW + eBookNormalType) ||
                        displayName.equals(ChatColor.YELLOW + eBookAdvancedType)) {

                    if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                        event.setCancelled(true);
                        boolean success = plManager.getEnchantmentManager().applyBookToItem(player, clickedItem, cursorItem);
                        // Se é compativel consumimos o livro
                        if (success) {
                            if (cursorItem.getAmount() == 1) {
                                event.setCursor(new ItemStack(Material.AIR));
                            } else {
                                cursorItem.setAmount(cursorItem.getAmount() - 1);
                            }
                        }
                        return;
                    }
                }
            }
        }

        RuneManager runeMgr = plManager.getRuneManager();

        // Rune extraction
        if (clickedItem != null && clickedItem.getType() != Material.AIR && cursorItem != null && runeMgr.isExtractionRune(cursorItem)) {
            if (!runeMgr.hasEnchantments(clickedItem)) return;

            if (!runeMgr.canExtractEnchantments(clickedItem)) {
                player.sendMessage(ChatColor.RED + "You cannot extract from this item.");
                player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0f, 1.0f);
                return;
            }

            int numEnchantments = clickedItem.getEnchantments().size();
            int emptySlots = runeMgr.countEmptySlots(player.getInventory());

            if (emptySlots < numEnchantments) {
                player.sendMessage(ChatColor.RED + "You do not have enough space for the extracted books.");
                player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0f, 1.0f);
                return;
            }

            event.setCancelled(true);

            // Consome a runa via cursor
            int amountInCursor = cursorItem.getAmount();
            if (amountInCursor == 1) {
                event.setCursor(new ItemStack(Material.AIR));
            } else {
                cursorItem.setAmount(amountInCursor - 1);
            }

            // Realiza a extração
            runeMgr.extractEnchantments(player, clickedItem);
            runeMgr.removeEnchantments(clickedItem);

            player.sendMessage(ChatColor.GREEN + "Enchantments successfully extracted!");
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 1.0f);
            return;
        }

        // Guis de visualização
        if (inventory.getHolder() instanceof EnchantGUIHolder) {
            event.setCancelled(true);

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;
            ItemMeta meta = clickedItem.getItemMeta();

            // Voltar ao MainGui
            if (clickedItem.getType() == Material.ARROW && meta.hasDisplayName()) {
                if (meta.getDisplayName().equals(ChatColor.YELLOW + "Back")) {
                    plManager.getMainGUI().openEnchantInventory(player);
                }
            }
            return;
        }

        if (inventory.getHolder() instanceof RecyclerHolder) {
            RecyclerManager recyclerMgr = plManager.getRecyclerManager();
            Inventory topInventory = player.getOpenInventory().getTopInventory();
            Inventory clickedInventory = event.getClickedInventory();

            // Player clicou no inventário do reciclador
            if (clickedInventory != null && clickedInventory.equals(topInventory)) {
                if (clickedItem != null && clickedItem.getType() == Material.ENCHANTED_BOOK) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Click the cancel button or exit the inventory to recover your books!");
                    return;
                }

                event.setCancelled(true);
                if (clickedItem == null || !clickedItem.hasItemMeta()) return;

                ItemMeta meta = clickedItem.getItemMeta();
                String rawDisplayName = meta.hasDisplayName() ? meta.getDisplayName() : "";
                String displayName = ChatColor.stripColor(rawDisplayName);

                switch (displayName) {
                    // Add all
                    case RecyclerGUI.addAllToRecycler:
                        recyclerMgr.addAllBooksToRecycler(player);
                        return;

                    // Cancel
                    case RecyclerGUI.cancelRecycler:
                        recyclerMgr.returnBooksToPlayer(player, open);
                        recyclerMgr.updateRecyclerBottle(player, 0);
                        recyclerMgr.clearRecyclerBooks(open);
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        return;

                    // Accept
                    case RecyclerGUI.acceptRecycler:
                        int totalValue = recyclerMgr.getTotalValueFromRecyclerBottle(player);
                        if (totalValue > 0) {
                            player.giveExp(totalValue);
                            recyclerMgr.clearRecyclerBooks(open);
                            player.closeInventory();
                            player.sendMessage(ChatColor.GREEN + "You gained " + ChatColor.YELLOW + plManager.formatExp(totalValue) + " XP " + ChatColor.GREEN + "by recycling your enchanted books!");
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        }
                        return;
                }
                return;
            }

            // Player clicou no proprio inventário
            if (clickedInventory != null && clickedInventory.equals(player.getOpenInventory().getBottomInventory())) {
                event.setCancelled(true);
                if (clickedItem == null || !clickedItem.hasItemMeta()) return;
                if (clickedItem.getType() == Material.ENCHANTED_BOOK) {
                    String bookType = plManager.getEnchantmentManager().getEnchantedBookTypeFromItem(clickedItem);

                    if (bookType != null) {
                        int firstEmptySlot = topInventory.firstEmpty();

                        if (firstEmptySlot == -1 || firstEmptySlot >= 45) {
                            player.sendMessage(ChatColor.RED + "The recycler inventory is full.");
                            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0f, 1.0f);
                            return;
                        }

                        ItemStack bookToRecycle = clickedItem.clone();
                        bookToRecycle.setAmount(1);

                        if (clickedItem.getAmount() > 1) {
                            clickedItem.setAmount(clickedItem.getAmount() - 1);
                        } else {
                            player.getInventory().setItem(event.getSlot(), null);
                        }

                        topInventory.setItem(firstEmptySlot, bookToRecycle);

                        int currentTotal = recyclerMgr.getTotalValueFromRecyclerBottle(player);
                        currentTotal += recyclerMgr.calculateRecycleValue(bookType);
                        recyclerMgr.updateRecyclerBottle(player, currentTotal);

                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.0f);
                    }
                }
            }
            return;
        }

        // Sorcerer GUI
        if (!(inventory.getHolder() instanceof MainHolder)) return;

        event.setCancelled(true);

        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        ItemMeta meta = clickedItem.getItemMeta();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && clickedInventory.equals(player.getOpenInventory().getBottomInventory())) {
            return;
        }

        // Sistema do Frasco XP Total
        if (plManager.getFlaskBookManager().customXpBottle(totalFlaskType, clickedItem)) {
            plManager.getFlaskBookManager().handleTotalXpBottleWithdraw(player, clickedItem);
            return;
        }

        // Sistema de Compra de Frascos de Experiência (Simples, Normal, Avançado)
        if (clickedItem.getType() == Material.EXPERIENCE_BOTTLE && meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();

            if (displayName.equals(ChatColor.YELLOW + advancedFlaskType)) {
                plManager.getFlaskBookManager().handleBottlePurchase(player, clickedItem, advancedFlaskType, plManager.getMainGUI().getXpAdvancedCost());
            } else if (displayName.equals(ChatColor.YELLOW + normalFlaskType)) {
                plManager.getFlaskBookManager().handleBottlePurchase(player, clickedItem, normalFlaskType, plManager.getMainGUI().getXpNormalCost());
            } else if (displayName.equals(ChatColor.YELLOW + simpleFlaskType)) {
                plManager.getFlaskBookManager().handleBottlePurchase(player, clickedItem, simpleFlaskType, plManager.getMainGUI().getXpSimpleCost());
            }
            return;
        }

        // Compra com Esquerdo / Abertura de GUI com Direito
        if (clickedItem.getType() == Material.BOOK && meta.hasLore()) {
            List<String> lore = meta.getLore();

            String bookType = plManager.getFlaskBookManager().getBookTypeFromLore(lore);
            if (bookType == null) return;

            if (event.isLeftClick()) {
                int cost = plManager.getFlaskBookManager().getBookCost(bookType);
                plManager.getFlaskBookManager().handleBookPurchase(player, clickedItem, bookType, cost);
            } else if (event.isRightClick()) {
                plManager.getFlaskBookManager().openBookGui(player, bookType);
            }
            return;
        }

        // Sistema para abrir o GUI do Reciclador
        if (clickedItem.getType() == Material.HOPPER && event.isLeftClick()) {
            if (meta.hasDisplayName() && meta.getDisplayName().equals(RecyclerGUI.GUI_TITLE)) {
                plManager.getRecyclerGUI().openRecyclerInventory(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof RecyclerHolder) {
            RecyclerManager recyclerMgr = plManager.getRecyclerManager();
            recyclerMgr.returnBooksToPlayer(player, inventory);
            recyclerMgr.clearRecyclerBooks(inventory);
        }
    }
}
