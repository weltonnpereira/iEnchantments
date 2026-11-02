package voxel.wp.ienchantments.events;

import voxel.wp.ienchantments.Main;
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
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

public class RecyclerInventoryEvent implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    int bookAdvancedCost = plugin.getConfig().getInt("CustoLivro.eBookAdvancedCost");
    int bookNormalCost = plugin.getConfig().getInt("CustoLivro.eBookNormalCost");
    int bookSimpleCost = plugin.getConfig().getInt("CustoLivro.eBookSimpleCost");

    private int totalValue = 0;

    @EventHandler
    public void RecyclerInventoryClicked(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory open = player.getOpenInventory().getTopInventory();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        Inventory playerInventory = player.getInventory();

        if (item == null || open == null) {
            return;
        }

        if (player instanceof Player) {

            if (open.equals(player.getInventory())) {
                return;
            }

            if (open.getName().equals(ChatColor.DARK_GREEN + "Reciclador")) {
                event.setCancelled(true);

                if (event.isRightClick()) {
                    event.setCancelled(true);
                } else {
                    if (item != null && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if(meta != null && meta.hasDisplayName()
                                && meta.getDisplayName().equals(ChatColor.AQUA + "Adicionar ao Reciclador")) {
                            Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
                            for (int i = 0; i < playerInventory.getSize(); i++) {
                                ItemStack book = playerInventory.getItem(i);
                                if (book != null && book.getType() == Material.ENCHANTED_BOOK && book.hasItemMeta()) {
                                    ItemMeta bookMeta = book.getItemMeta();
                                    if(bookMeta != null) {
                                        String bookDisplayName = bookMeta.getDisplayName();
                                        if (bookDisplayName != null) {
                                            int emptySlot = recyclerInventory.firstEmpty();
                                            if (bookDisplayName.equals(ChatColor.YELLOW + "Livro Avançado")) {
                                                if (emptySlot == -1) {
                                                    player.sendMessage(ChatColor.RED + "O inventário do reciclador está cheio.");
                                                    break;
                                                }

                                                recyclerInventory.setItem(emptySlot, book);

                                                playerInventory.setItem(i, null);

                                                recycleBooks(bookAdvancedCost);
                                                updateRecyclerAllBottle(player, totalValue);
                                            } else if (bookDisplayName.equals(ChatColor.YELLOW + "Livro Normal")) {
                                                if (emptySlot == -1) {
                                                    player.sendMessage(ChatColor.RED + "O inventário do reciclador está cheio.");
                                                    break;
                                                }

                                                recyclerInventory.setItem(emptySlot, book);

                                                playerInventory.setItem(i, null);

                                                recycleBooks(bookNormalCost);
                                                updateRecyclerAllBottle(player, totalValue);
                                            } else if (bookDisplayName.equals(ChatColor.YELLOW + "Livro Simples")) {
                                                if (emptySlot == -1) {
                                                    player.sendMessage(ChatColor.RED + "O inventário do reciclador está cheio.");
                                                    break;
                                                }

                                                recyclerInventory.setItem(emptySlot, book);

                                                playerInventory.setItem(i, null);

                                                recycleBooks(bookSimpleCost);
                                                updateRecyclerAllBottle(player, totalValue);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Verificar se o item clicado é "Negar"
                    if (item != null && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null && meta.hasDisplayName() &&
                                meta.getDisplayName().equals(ChatColor.RED + "Negar")) {
                            Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
                            if (isInventoryFull(playerInventory) && isInventoryFull(recyclerInventory)) {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. " +
                                        "Saia do inventário para recuperar seus livros!");
                                event.setCancelled(true);

                                if(item.getType() == Material.STAINED_GLASS_PANE) {
                                    return;
                                }

                            } else {
                                event.setCancelled(true);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);

                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
                            }
                        }
                    }

                    // Verificar se o item clicado é "Aceitar"
                    if(item != null && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null && meta.hasDisplayName() &&
                                meta.getDisplayName().equals(ChatColor.GREEN + "Aceitar")) {
                            int totalValue = getTotalValueFromRecyclerBottle(player);

                            if (totalValue > 0) {
                                // Dar xp ao jogador
                                player.giveExp(totalValue);

                                // Remover os livros encantados do inventário do jogador
                                removeRecycledBooks(player);

                                // Reseta o frasco
                                resetRecyclerBottle(player);

                                // Fechar o inventário do reciclador
                                player.closeInventory();

                                // Reinicializar o valor do frasco de vidro para zero

                                // Envie uma mensagem ao jogador informando que eles ganharam XP
                                player.sendMessage(ChatColor.GREEN + "Você ganhou "
                                        + ChatColor.YELLOW + formatExp(totalValue) + " XP "
                                        + ChatColor.GREEN + "reciclando seus livros encantados!");
                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                            }
                        }
                    }
                }

                // Verificar se o item clicado é um "Livro Encantado"
                if (item.getType() == Material.ENCHANTED_BOOK && item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                        // Verificar se o inv do reciclador tem espaço
                        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
                        int firstEmptySlot = recyclerInventory.firstEmpty();


                        // Verificar se o inventário do jogador está cheio
                        if (event.getClickedInventory().equals(recyclerInventory)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "Clique no botão negar ou " +
                                    "saia do inventario para recuperar seus livros!");
                            return;
                        }

                        // Verificar se o inventário do reciclador está cheio
                        if(firstEmptySlot == -1) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "O inventário do reciclador está cheio.");
                        } else {
                            // Update Frask
                            recycleBooks(bookAdvancedCost);

                            updateRecyclerBottle(player, totalValue);

                            // Remova o item do inv do player
                            event.setCurrentItem(null);

                            // Add o item no inv do reciclador
                            recyclerInventory.setItem(firstEmptySlot, item);
                        }
                    } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Normal")) {
                        // Verificar se o inv do reciclador tem espaço
                        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
                        int firstEmptySlot = recyclerInventory.firstEmpty();


                        // Verificar se o inventário do jogador está cheio
                        if (event.getClickedInventory().equals(recyclerInventory)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "Clique no botão negar ou " +
                                    "saia do inventario para recuperar seus livros!");
                            return;
                        }

                        // Verificar se o inventário do reciclador está cheio
                        if(firstEmptySlot == -1) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "O inventário do reciclador está cheio.");
                        } else {
                            // Update Frask
                            recycleBooks(bookNormalCost);

                            updateRecyclerBottle(player, totalValue);

                            // Remova o item do inv do player
                            event.setCurrentItem(null);

                            // Add o item no inv do reciclador
                            recyclerInventory.setItem(firstEmptySlot, item);
                        }
                    } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Simples")) {
                        // Verificar se o inv do reciclador tem espaço
                        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
                        int firstEmptySlot = recyclerInventory.firstEmpty();


                        // Verificar se o inventário do jogador está cheio
                        if (event.getClickedInventory().equals(recyclerInventory)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "Clique no botão negar ou " +
                                    "saia do inventario para recuperar seus livros!");
                            return;
                        }

                        // Verificar se o inventário do reciclador está cheio
                        if(firstEmptySlot == -1) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "O inventário do reciclador está cheio.");
                        } else {
                            // Update Frask
                            recycleBooks(bookSimpleCost);

                            updateRecyclerBottle(player, totalValue);

                            // Remova o item do inv do player
                            event.setCurrentItem(null);

                            // Add o item no inv do reciclador
                            recyclerInventory.setItem(firstEmptySlot, item);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void RecyclerInventoryClosed(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        Inventory playerInventory = player.getInventory();

        // Verifique se o inventário fechado é o inventário do reciclador
        if(inventory.getName().equals(ChatColor.DARK_GREEN + "Reciclador")) {
            // Verifique se há algum item no inventário do reciclador
            for (ItemStack item : inventory.getContents()) {
                if(item != null && item.getType() != Material.AIR) {
                    // Verifique se o item é um "Livro Avançado"
                    if(item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                            // Verifique se o inventário do jogador está cheio
                            if (isInventoryFull(playerInventory)) {
                                // Inventario cheio, vamos dropar o item no chão
                                player.getWorld().dropItem(player.getLocation(), item);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);
                            } else {
                                // Mova o "Livro Avançado" para o inventário do jogador
                                playerInventory.addItem(item);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);
                            }
                        } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Normal")) {
                            // Verifique se o inventário do jogador está cheio
                            if (isInventoryFull(playerInventory)) {
                                // Inventario cheio, vamos dropar o item no chão
                                player.getWorld().dropItem(player.getLocation(), item);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);
                            } else {
                                // Mova o "Livro Normal" para o inventário do jogador
                                playerInventory.addItem(item);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);
                            }
                        } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Simples")) {
                            // Verifique se o inventário do jogador está cheio
                            if (isInventoryFull(playerInventory)) {
                                // Inventario cheio, vamos dropar o item no chão
                                player.getWorld().dropItem(player.getLocation(), item);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);
                            } else {
                                // Mova o "Livro Simples" para o inventário do jogador
                                playerInventory.addItem(item);

                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);

                                // Remova o item do inventário
                                inventory.remove(item);
                            }
                        }
                    }
                }
            }
        }
    }

    private void resetRecyclerBottle(Player player) {
        totalValue = 0;
        updateRecyclerAllBottle(player, 0); // Atualize o frasco para mostrar um valor de 0
    }

    private void recycleBooks(int bookCost) {
        // Calcular o valor de XP com base no livro e adicionar ao valor total

        double proportionMin = 0.15; // 0.15 = 15% do valor base

        double proportionMax = 0.6; // 0.6 = 60% do valor base

        // Calcular valor min e max
        int minValue = (int) (bookCost * proportionMin);
        int maxValue = (int) (bookCost * proportionMax);

        if(maxValue > bookCost) {
            maxValue = bookCost;
        }

        // Gerar um valor aleatório entre valorMinimo e valorMaximo (inclusive)
        Random random = new Random();
        int recyclerValue = random.nextInt(maxValue - minValue + 1) + minValue;

        // Garantir que recyclerValue seja menor que bookCost
        recyclerValue = Math.min(recyclerValue, bookCost - 1);

        // Atualizar o lore do frasco de vidro com o novo valor total de XP
        totalValue += recyclerValue;

    }

    private void updateRecyclerAllBottle(Player player, int totalValue) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int fraskPosition = 49;

        if (recyclerInventory != null) {
            for (ItemStack item : recyclerInventory.getContents()) {
                if (item != null && item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null && meta.hasDisplayName()) {
                        String displayName = meta.getDisplayName();
                        if (displayName.equals(ChatColor.YELLOW + "Livro Avançado")) {
                            updateRecyclerBottle(player, totalValue);
                        } else if (displayName.equals(ChatColor.YELLOW + "Livro Normal")) {
                            updateRecyclerBottle(player, totalValue);
                        } else if (displayName.equals(ChatColor.YELLOW + "Livro Simples")) {
                            updateRecyclerBottle(player, totalValue);
                        }
                    }
                }
            }
        }

        if (recyclerInventory != null) {
            ItemStack recyclerBottle = recyclerInventory.getItem(fraskPosition);

            if (recyclerBottle != null) {
                ItemMeta recyclerBottleMeta = recyclerBottle.getItemMeta();

                // Verifique o valor total e configure o tipo de frasco com base nesse valor
                if (totalValue == 0) {
                    recyclerBottle.setType(Material.GLASS_BOTTLE);
                    recyclerBottleMeta.setLore(null);
                } else {
                    recyclerBottle.setType(Material.EXP_BOTTLE);
                    // Obtenha o lore atual do frasco de vidro
                    List<String> recyclerBottleLore = recyclerBottleMeta.getLore();

                    // Encontre a linha do lore que contém o valor total
                    String lineTotalValue = ChatColor.WHITE + "Valor total: ";

                    for (int i = 0; i < recyclerBottleLore.size(); i++) {
                        String loreLine = recyclerBottleLore.get(i);
                        if (loreLine.startsWith(lineTotalValue)) {
                            // Atualize o valor total de XP no lore
                            recyclerBottleLore.set(i, lineTotalValue + ChatColor.GREEN + formatExp(totalValue) + " XP");
                            recyclerBottleLore.set(i + 1, "");
                            recyclerBottleLore.set(i + 2, ChatColor.GRAY + "Todos os seus livros serão");
                            recyclerBottleLore.set(i + 3, ChatColor.GRAY + "transformados em experiência com " + formatExp(totalValue) + " XP");
                            break;
                        }
                    }

                    // Defina o novo lore no item do frasco de vidro
                    recyclerBottleMeta.setLore(recyclerBottleLore);
                    recyclerBottle.setItemMeta(recyclerBottleMeta);
                    recyclerInventory.setItem(fraskPosition, recyclerBottle);
                }
            }
        }
    }


    public void updateRecyclerBottle(Player player, int totalValue) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int fraskPosition = 49;

        if(recyclerInventory != null) {
            ItemStack recyclerBottle = recyclerInventory.getItem(fraskPosition);

            if(recyclerBottle != null) {
                ItemMeta recyclerBottleMeta = recyclerBottle.getItemMeta();

                // Verifique o valor total e configure o tipo de frasco com base nesse valor
                if(totalValue == 0) {
                    recyclerBottle.setType(Material.GLASS_BOTTLE);
                    recyclerBottleMeta.setLore(null);
                } else {
                    recyclerBottle.setType(Material.EXP_BOTTLE);
                    // Obtenha o lore atual do frasco de vidro
                    List<String> recyclerBottleLore = recyclerBottleMeta.getLore();

                    // Encontre a linha do lore que contém o valor total
                    String lineTotalValue = ChatColor.WHITE + "Valor total: ";

                    for(int i = 0; i < recyclerBottleLore.size(); i++) {
                        String loreLine = recyclerBottleLore.get(i);
                        if(loreLine.startsWith(lineTotalValue)) {
                            // Atualize o valor total de XP no lore
                            recyclerBottleLore.set(i, lineTotalValue + ChatColor.GREEN + formatExp(totalValue) + " XP");
                            recyclerBottleLore.set(i + 1, "");
                            recyclerBottleLore.set(i + 2, ChatColor.GRAY + "Todos os seus livros serão");
                            recyclerBottleLore.set(i + 3, ChatColor.GRAY + "transformados em experiência com " + formatExp(totalValue) + " XP");
                            break;
                        }
                    }

                    // Defina o novo lore no item do frasco de vidro
                    recyclerBottleMeta.setLore(recyclerBottleLore);
                    recyclerBottle.setItemMeta(recyclerBottleMeta);
                    recyclerInventory.setItem(fraskPosition, recyclerBottle);
                }
            }
        }
    }

    private String formatExp(int xpAmount) {
        return String.format("%,d", xpAmount);
    }

    private int getTotalValueFromRecyclerBottle(Player player) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int fraskPosition = 49;

        if(recyclerInventory != null) {
            ItemStack recyclerBottle = recyclerInventory.getItem(fraskPosition);

            if(recyclerBottle != null && recyclerBottle.getType() == Material.EXP_BOTTLE) {
                ItemMeta recyclerBottleMeta = recyclerBottle.getItemMeta();
                List<String> recyclerBottleLore = recyclerBottleMeta.getLore();

                String lineTotalValue = ChatColor.WHITE + "Valor total: " + ChatColor.GREEN;

                // Iterar sobre as linhas do lore para encontrar o valor total
                for (String loreLine : recyclerBottleLore) {
                    if(loreLine.startsWith(lineTotalValue)) {
                        // Extrair o valor total do lore
                        String totalValueString = loreLine.substring(lineTotalValue.length());
                        totalValueString = totalValueString.replace(" XP", "");

                        totalValueString = totalValueString.replaceAll("[.,]", "");
                        try {
                            // Tentar analisar o valor total como um inteiro
                            int totalValue = Integer.parseInt(totalValueString.trim());
                            return totalValue;
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    }
                }
            }
        }

        return 0;
    }

    private void removeRecycledBooks(Player player) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();

        if(recyclerInventory != null) {
            // Iterar sobre os slots do inventário do reciclador
            for(int slot = 0; slot < recyclerInventory.getSize(); slot++) {
                ItemStack item = recyclerInventory.getItem(slot);

                // Verificar se o slot contém um item
                if(item != null && item.getType() == Material.ENCHANTED_BOOK) {
                    // Verificar se o livro reciclado é do tipo avançado
                    if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                    item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado") ||
                            item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Livro Normal") ||
                            item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Livro Simples")) {
                        // Remover o livro reciclado do inventário do jogador
                        recyclerInventory.setItem(slot, null);
                    }
                }
            }
        }
    }

    // Função para verificar se um inventário está cheio
    private boolean isInventoryFull(Inventory inventory) {
        for(ItemStack item : inventory.getContents()) {
            if(item == null || item.getType() == Material.AIR) {
                return false;
            }
        }

        return true;
    }
}
