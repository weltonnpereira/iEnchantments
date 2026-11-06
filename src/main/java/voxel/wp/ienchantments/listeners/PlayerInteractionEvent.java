package voxel.wp.ienchantments.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.manager.EnchantmentManager;
import voxel.wp.ienchantments.manager.FlaskBookManager;
import voxel.wp.ienchantments.manager.PlManager;
import voxel.wp.ienchantments.manager.RuneManager;

import static voxel.wp.ienchantments.gui.MainGUI.*;

public class PlayerInteractionEvent implements Listener {

    private final PlManager plManager;

    public PlayerInteractionEvent(PlManager plManager) {
        this.plManager = plManager;
    }

    /**
     * Bloqueia o uso da Runa de Extração para renomear entidades
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();
        RuneManager runeMgr = plManager.getRuneManager();

        if (runeMgr.isExtractionRune(itemInHand)) {
            event.setCancelled(true);
            player.updateInventory();
            player.sendMessage(ChatColor.RED + "You cannot use the " + runeMgr.extractionRuneName +
                    ChatColor.RED + " to name entities.");
        }
    }

    /**
     * Gerencia o clique no ar/bloco para consumir Frascos de XP e Livros de Encantamento Customizados
     */
    @EventHandler
    public void onPlayerInteractItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        FlaskBookManager flaskMgr = plManager.getFlaskBookManager();

        // CONSUMO DE FRASCOS DE EXPERIÊNCIA
        if (flaskMgr.customXpBottle(totalFlaskType, item) ||
                flaskMgr.customXpBottle(advancedFlaskType, item) ||
                flaskMgr.customXpBottle(normalFlaskType, item) ||
                flaskMgr.customXpBottle(simpleFlaskType, item)) {

            int xpAmount = flaskMgr.getStoredExperience(item);

            if (xpAmount > 0) {
                event.setCancelled(true);
                plManager.consumeItemInHand(player, item);

                player.giveExp(xpAmount);
                player.sendMessage(ChatColor.GREEN + "You consumed a " + meta.getDisplayName() + ChatColor.GREEN + " with " + ChatColor.YELLOW + flaskMgr.formatExp(xpAmount) + " XP");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f); // Nota: ajuste para player.getLocation() conforme seu import
            }
            return;
        }

        // USO DE LIVROS DE ENCANTAMENTO CUSTOMIZADOS
        if (item.getType() == Material.BOOK && meta.hasLore()) {
            String bookTypeStr = flaskMgr.getBookTypeFromLore(meta.getLore());
            if (bookTypeStr == null) return;

            event.setCancelled(true);

            EnchantmentManager.BookType bookType = plManager.getEnchantmentManager().resolveBookType(bookTypeStr);
            if (bookType == null) return;

            if (plManager.hasInventorySpaceOrStack(player, item)) {
                plManager.consumeItemInHand(player, item);
                plManager.getEnchantmentManager().createEnchantedBook(player, bookType);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            } else {
                player.sendMessage(ChatColor.RED + "Your inventory is full. Clear space before using your book.");
            }
        }
    }
}