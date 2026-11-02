package voxel.wp.ienchantments.events;

import voxel.wp.ienchantments.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import voxel.wp.ienchantments.listeners.EnchantGUI;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class AdvancedInventoryEvent implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void AdvancedInventoryClicked(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory open = player.getOpenInventory().getTopInventory();
        ItemStack item = event.getCurrentItem();

        if(item == null || open == null) {
            return;
        }

        if(player instanceof Player) {

            if(open.equals(player.getInventory())) {
                return;
            }

            if(open.getName().equals(ChatColor.DARK_PURPLE + "Encantamentos - Livro Avançado")) {
                event.setCancelled(true);

                if(event.isRightClick()) {
                    event.setCancelled(true);
                } else {
                    // Verificar se o player clicou na flecha
                    if(item.getType() == Material.ARROW && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Voltar")) {
                            EnchantGUI enchantGUI = new EnchantGUI();
                            enchantGUI.EnchantInventory(player);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRightClickInAdvancedBook(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        String typeBook = "Avançado";

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(customBook(item, typeBook)) {
                // Verifica se o player tem espaço no inv para o livro
                int amountInHand = item.getAmount();
                if(amountInHand == 1) {
                    // Se houver apenas 1 remover
                    player.getInventory().setItemInHand(new ItemStack(Material.AIR));

                    createEnchantedBook(player);
                } else if(player.getInventory().firstEmpty() != -1) {
                    // Se houver mais de um apenas diminuir no slot
                    item.setAmount(amountInHand - 1);

                    createEnchantedBook(player);
                } else {
                    player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu livro.");
                }
            }
        }
    }

    public void createEnchantedBook(Player player) {
        String armor = "Armaduras";
        String arms = "Armas";
        String tools = "Ferramentas";
        String elms = "Elmos";
        String boots = "Botas";
        String swords = "Espadas";
        String bow = "Arcos";
        String pickaxe = "Picaretas";
        String axe = "Machados";

        // Levels
        int level1 = 1;
        int level2 = 2;
        int level3 = 3;
        int level4 = 4;
        int level5 = 5;

        // Lista de encantamentos possíveis
        List<Enchantment> possibleEnchantments = new ArrayList<>();
        possibleEnchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        possibleEnchantments.add(Enchantment.PROTECTION_FIRE);
        possibleEnchantments.add(Enchantment.DURABILITY);
        possibleEnchantments.add(Enchantment.DAMAGE_ALL);
        possibleEnchantments.add(Enchantment.OXYGEN);
        possibleEnchantments.add(Enchantment.PROTECTION_FALL);
        possibleEnchantments.add(Enchantment.DEPTH_STRIDER);
        possibleEnchantments.add(Enchantment.LOOT_BONUS_MOBS);
        possibleEnchantments.add(Enchantment.DIG_SPEED);
        possibleEnchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
        possibleEnchantments.add(Enchantment.ARROW_DAMAGE);
        possibleEnchantments.add(Enchantment.ARROW_INFINITE);
        possibleEnchantments.add(Enchantment.FIRE_ASPECT);
        possibleEnchantments.add(Enchantment.ARROW_FIRE);
        possibleEnchantments.add(Enchantment.SILK_TOUCH);
        possibleEnchantments.add(Enchantment.ARROW_KNOCKBACK);
        possibleEnchantments.add(Enchantment.WATER_WORKER);

        // Escolher aleatoriamente um encantamento da lista ou nivel se precisar
        Random random = new Random();
        int randomLevel4between5= random.nextInt(2) + 4;
        Enchantment chosenEnchantment = possibleEnchantments.get(random.nextInt(possibleEnchantments.size()));

        // Criar um livro encantado com o encantamento escolhido!
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantedBookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

        enchantedBookMeta.setDisplayName(ChatColor.YELLOW + "Livro Avançado");

        // Gerar uma porcentagem aleatória entre 5 e 60
        int porcentagem = random.nextInt(56) + 5;

        // Verificar os encantamentos
        if(chosenEnchantment == Enchantment.PROTECTION_ENVIRONMENTAL) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level5, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + armor);
            enchantedBookMeta.setLore(lore);
        } else if(chosenEnchantment == Enchantment.PROTECTION_FIRE) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level4, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + armor);
            enchantedBookMeta.setLore(lore);
        }  else if (chosenEnchantment == Enchantment.DURABILITY) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level5, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + armor + "/" + arms + "/" + bow + "/" + tools);
            enchantedBookMeta.setLore(lore);
        }else if(chosenEnchantment == Enchantment.DAMAGE_ALL) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level5, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + arms);
            enchantedBookMeta.setLore(lore);
        } else if(chosenEnchantment == Enchantment.OXYGEN) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level3, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + elms);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.PROTECTION_FALL) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level4, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + boots);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.DEPTH_STRIDER) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level3, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + boots);
            enchantedBookMeta.setLore(lore);
        } else if(chosenEnchantment == Enchantment.LOOT_BONUS_MOBS) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level3, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + swords);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.DIG_SPEED) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level5, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + tools);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.LOOT_BONUS_BLOCKS) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level3, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + pickaxe + "/" + axe);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.ARROW_DAMAGE) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level5, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + bow);
            enchantedBookMeta.setLore(lore);
        } else if(chosenEnchantment == Enchantment.ARROW_INFINITE) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level1, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + bow);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.FIRE_ASPECT) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level2, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + arms);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.ARROW_FIRE) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level1, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + bow);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.SILK_TOUCH) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level1, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + pickaxe);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.ARROW_KNOCKBACK) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level2, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + bow);
            enchantedBookMeta.setLore(lore);
        } else if (chosenEnchantment == Enchantment.WATER_WORKER) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, level1, true);
            List<String> lore = new ArrayList<>();
            lore.add("");
            if(porcentagem <= 25) {
                lore.add(ChatColor.RED + "Chance de sucesso: " + porcentagem + "%");
            } else {
                lore.add(ChatColor.GREEN + "Chance de sucesso: " + porcentagem + "%");
            }
            lore.add("Encanta: " + elms);
            enchantedBookMeta.setLore(lore);
        }

        enchantedBook.setItemMeta(enchantedBookMeta);

        // Add enchant book ao inv do player
        player.getInventory().addItem(enchantedBook);
    }

    private boolean hasEnchantment(ItemStack item, Enchantment enchantment, int level) {
        Map<Enchantment, Integer> existingEnchantments = item.getEnchantments();
        return existingEnchantments.containsKey(enchantment) && existingEnchantments.get(enchantment) >= level;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if(clickedItem != null && cursorItem != null){
            // Verificar se é um item encantado
            if(isEnchantedBook(cursorItem)) {
                if(isValidTargetItem(clickedItem, cursorItem)) {
                    EnchantmentStorageMeta cursorMeta = (EnchantmentStorageMeta) cursorItem.getItemMeta();
                    Map<Enchantment, Integer> enchantments = cursorMeta.getStoredEnchants();

                    for(Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                        Enchantment enchantment = entry.getKey();
                        int level = entry.getValue();

                        int porcentagem = extractPorcentagemFromLore(cursorItem);
                        int randomValue = new Random().nextInt(61);

                        if (canExtractEnchantments(clickedItem)) {
                            if (!hasEnchantment(clickedItem, enchantment, level)) {
                                if (randomValue <= porcentagem) {
                                    // Remover o livro encantado do inv do player
                                    event.setCursor(new ItemStack(Material.AIR));

                                    // Aplicar encantamento
                                    applyEnchantment(clickedItem, cursorItem);

                                    player.sendMessage(ChatColor.GREEN + "O item foi encantado com sucesso!");
                                    player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1.0f, 1.0f);
                                } else {
                                    event.setCursor(new ItemStack(Material.AIR));
                                    player.sendMessage(ChatColor.RED + "O encantamento falhou.");
                                    player.playSound(player.getLocation(), Sound.BLAZE_HIT, 1.0f, 1.0f);
                                }

                            } else {
                                player.sendMessage(ChatColor.RED + "Esse item já possui um encantamento igual ou é maior que o seu!");
                                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Você não pode encantar esse item.");
                            player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
                        }
                    }

                    event.setCancelled(true);
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

    // Função para extrair a porcen da lore
    private int extractPorcentagemFromLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta.hasLore()) {
            for (String lore : meta.getLore()) {
                if(lore.contains(ChatColor.GREEN + "Chance de sucesso:") ||
                        lore.contains(ChatColor.RED + "Chance de sucesso:")) {
                    String[] parts = lore.split(" ");
                    if(parts.length >= 4) {
                        try {
                            return Integer.parseInt(parts[3].replace("%", ""));
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao converter número: " + e.getMessage());
                        }
                    }
                }
            }
        }
        return 0;
    }

    private boolean isEnchantedBook(ItemStack item) {
        if(item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                return true;
            }
        }
        return false; // Não possui nome custom
    }


    private boolean isValidTargetItem(ItemStack item,ItemStack enchantedBook) {
        Material itemType = item.getType();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
        Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

        if (isArmor(itemType) && (hasProtection(enchantments) || hasFireProtection(enchantments)
                || hasUnbreaking(enchantments))) {
            return true;
        }

        if (isHelmet(itemType) && (hasRespiration(enchantments) || hasAquaInfinity(enchantments))) {
            return true;
        }

        if (isBoots(itemType) && (hasDepthStrider(enchantments) || hasFeatherFalling(enchantments))) {
            return true;
        }

        if (isSword(itemType) && (hasSharpness(enchantments) || (hasLooting(enchantments))
                || hasUnbreaking(enchantments) || hasFireAspect(enchantments))) {
            return true;
        }

        if(isAxe(itemType) && (hasSharpness(enchantments) ||
                hasUnbreaking(enchantments) || hasFireAspect(enchantments) ||
                hasFortune(enchantments))) {
            return true;
        }

        if (isBow(itemType) && (hasFlame(enchantments) || hasPower(enchantments)
                || hasInfinity(enchantments) || hasPunch(enchantments) || hasUnbreaking(enchantments))) {
            return true;
        }

        if (isTool(itemType) && (hasEfficiency(enchantments) || hasUnbreaking(enchantments))) {
            return true;
        }

        if(isHoe(itemType) && (hasUnbreaking(enchantments))) {
            return true;
        }

        if (isPickaxe(itemType) && (hasSilkTouch(enchantments) || hasFortune(enchantments))) {
            return true;
        }

        return false;
    }

    // Funções auxiliares para verificar encantamentos
    private boolean hasProtection(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL);
    }

    private boolean hasUnbreaking(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DURABILITY);
    }

    private boolean hasRespiration(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.OXYGEN);
    }

    private boolean hasFeatherFalling(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.PROTECTION_FALL);
    }

    private boolean hasAquaInfinity(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.WATER_WORKER);
    }

    private boolean hasSharpness(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DAMAGE_ALL);
    }

    private boolean hasFireAspect(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.FIRE_ASPECT);
    }

    private boolean hasFlame(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_FIRE);
    }

    private boolean hasPower(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_DAMAGE);
    }

    private boolean hasInfinity(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_INFINITE);
    }

    private boolean hasPunch(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_KNOCKBACK);
    }

    private boolean hasEfficiency(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DIG_SPEED);
    }

    private boolean hasSilkTouch(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.SILK_TOUCH);
    }

    private boolean hasFortune(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS);
    }

    private boolean hasDepthStrider(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DEPTH_STRIDER);
    }

    private boolean hasLooting(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.LOOT_BONUS_MOBS);
    }

    private boolean hasFireProtection(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.PROTECTION_FIRE);
    }

    // Funções auxiliares para verificar tipos de itens
    private boolean isArmor(Material itemType) {
        return itemType == Material.DIAMOND_HELMET || itemType == Material.DIAMOND_CHESTPLATE ||
                itemType == Material.DIAMOND_LEGGINGS || itemType == Material.DIAMOND_BOOTS ||
                itemType == Material.GOLD_HELMET || itemType == Material.GOLD_CHESTPLATE ||
                itemType == Material.GOLD_LEGGINGS || itemType == Material.GOLD_BOOTS ||
                itemType == Material.IRON_HELMET || itemType == Material.IRON_CHESTPLATE ||
                itemType == Material.IRON_LEGGINGS || itemType == Material.IRON_BOOTS ||
                itemType == Material.CHAINMAIL_HELMET || itemType == Material.CHAINMAIL_CHESTPLATE ||
                itemType == Material.CHAINMAIL_LEGGINGS || itemType == Material.CHAINMAIL_BOOTS ||
                itemType == Material.LEATHER_HELMET || itemType == Material.LEATHER_CHESTPLATE ||
                itemType == Material.LEATHER_LEGGINGS || itemType == Material.LEATHER_BOOTS;
    }

    private boolean isSword(Material itemType) {
        return itemType == Material.DIAMOND_SWORD || itemType == Material.IRON_SWORD ||
                itemType == Material.WOOD_SWORD || itemType == Material.STONE_SWORD
                || itemType == Material.GOLD_SWORD;
    }

    private boolean isPickaxe(Material itemType) {
        return itemType == Material.DIAMOND_PICKAXE || itemType == Material.IRON_PICKAXE ||
                itemType == Material.WOOD_PICKAXE || itemType == Material.STONE_PICKAXE
                || itemType == Material.GOLD_PICKAXE;
    }

    private boolean isAxe(Material itemType) {
        return itemType == Material.DIAMOND_AXE || itemType == Material.GOLD_AXE ||
                itemType == Material.IRON_AXE || itemType == Material.WOOD_AXE
                || itemType == Material.STONE_AXE;
    }

    private boolean isHelmet(Material itemType) {
        return itemType == Material.DIAMOND_HELMET || itemType == Material.GOLD_HELMET ||
                itemType == Material.IRON_HELMET || itemType == Material.LEATHER_HELMET;
    }

    private boolean isBow(Material itemType) {
        return itemType == Material.BOW;
    }

    private boolean isTool(Material itemType) {
        return itemType == Material.DIAMOND_PICKAXE || itemType == Material.IRON_PICKAXE ||
                itemType == Material.STONE_PICKAXE || itemType == Material.WOOD_PICKAXE ||
                itemType == Material.DIAMOND_SPADE || itemType == Material.IRON_SPADE ||
                itemType == Material.STONE_SPADE || itemType == Material.WOOD_SPADE ||
                itemType == Material.DIAMOND_AXE || itemType == Material.IRON_AXE ||
                itemType == Material.STONE_AXE || itemType == Material.WOOD_AXE ||
                itemType == Material.SHEARS;
    }

    private boolean isHoe(Material itemType) {
        return itemType == Material.DIAMOND_HOE || itemType == Material.IRON_HOE ||
                itemType == Material.STONE_HOE || itemType == Material.WOOD_HOE;
    }

    private boolean isBoots(Material itemType) {
        return itemType == Material.DIAMOND_BOOTS || itemType == Material.GOLD_BOOTS ||
                itemType == Material.IRON_BOOTS || itemType == Material.LEATHER_BOOTS;
    }

    private void applyEnchantment(ItemStack targetItem, ItemStack enchantedBook) {
        if(!isEnchantedBook(enchantedBook)) {
            return;
        }

        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
        Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

            // Loop através de todos os encantamentos no livro encantado
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();

                int level = entry.getValue();
                // Aplicar encantamento ao item de destino
                targetItem.addUnsafeEnchantment(enchantment, level);

            }
    }

    // Verificar se um item é um enchant book custom
    private boolean customBook(ItemStack item, String eBookType) {
        if(item != null && item.getType() == Material.BOOK && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if(meta.hasLore()) {
                return meta.getLore().contains(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookType);
            }
        }
        return false;
    }

}
