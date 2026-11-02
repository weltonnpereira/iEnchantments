package voxel.wp.ienchantments.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ExtractionRune implements Listener {

    private int percentageRune = 100;
    public void GiveExtractionRune(Player player) {

        if(player.getInventory().firstEmpty() != -1) {
            ItemStack runa = new ItemStack(Material.NAME_TAG);
            ItemMeta runaMeta = runa.getItemMeta();
            runaMeta.setDisplayName(ChatColor.GOLD + "Runa de Extração");
            List<String> runaLore = new ArrayList<>();
            runaLore.add("");
            runaLore.add(ChatColor.GRAY + "Use em um item para");
            runaLore.add(ChatColor.GRAY + "remover seus " + ChatColor.WHITE + "encantamentos");
            runaLore.add("");
            runaLore.add(ChatColor.GREEN + "Chance de sucesso: " + percentageRune + "%");
            runaMeta.setLore(runaLore);

            runaMeta.addEnchant(Enchantment.DURABILITY, 1, true);

            runaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            runa.setItemMeta(runaMeta);
            player.getInventory().addItem(runa);
        } else {
            player.sendMessage(ChatColor.RED + "Não há espaço no inventário.");
        }
    }

}
