package voxel.wp.ienchantments.events;

import voxel.wp.ienchantments.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import voxel.wp.ienchantments.listeners.RecyclerGUI;
import voxel.wp.ienchantments.listeners.SimpleEnchantsGUI;
import voxel.wp.ienchantments.listeners.NormalEnchantsGUI;
import voxel.wp.ienchantments.listeners.AdvancedEnchantsGUI;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class InventoryEvent implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    private String totalFraskType = "Frasco de Experiência";
    private String advancedFraskType = "Frasco de Experiência Avançado";
    private String normalFraskType = "Frasco de Experiência Normal";
    private String simpleFraskType = "Frasco de Experiência Simples";
    private String eBookSimpleType = "Simples";
    private String eBookNormalType = "Normal";
    private String eBookAdvancedType = "Avançado";

    private int eBookAdvancedCost = plugin.getConfig().getInt("BookCost.eBookAdvancedCost");
    private int eBookNormalCost = plugin.getConfig().getInt("BookCost.eBookNormalCost");
    private int eBookSimpleCost = plugin.getConfig().getInt("BookCost.eBookSimpleCost");


    private int xpAdvancedCost = plugin.getConfig().getInt("FraskCost.advancedFraskCost");

    private int xpNormalCost = plugin.getConfig().getInt("FraskCost.normalFraskCost");

    private int xpSimpleCost = plugin.getConfig().getInt("FraskCost.simpleFraskCost");

    @EventHandler
    public void InventoryClicked(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory open = player.getOpenInventory().getTopInventory();
        Inventory openLower = player.getOpenInventory().getBottomInventory();
        ItemStack item = event.getCurrentItem();


        if (item == null || open == null) {
            return;
        }

        if (player instanceof Player) {

            if (open.equals(player.getInventory())) {
                return;
            }

            if (open.getName().equals(ChatColor.DARK_PURPLE + "Feiticeiro")) {
                event.setCancelled(true);

                if(event.isRightClick() || event.getClickedInventory().equals(openLower)) {
                    event.setCancelled(true);
                } else {
                    // Verificar se o player clicou em um custom frasco xp total
                    if (customXpBottle(totalFraskType, item)) {
                        if(player.getInventory().firstEmpty() != -1) {
                            // Obter a quantidade de xp armazenada no frasco
                            int xpAmount = getStoredExperience(item);

                            //if (xpAmount > 0) {
                                // Remover frasco de xp
                                event.setCurrentItem(null);

                                // Criar um frasco de xp custom
                                ItemStack xpBottle = createCustomXpBottle(totalFraskType, xpAmount);
                                ItemMeta meta = xpBottle.getItemMeta();

                                if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Frasco de Experiência")) {
                                    // Remover xp do player
                                    player.setLevel(0);
                                    player.setExp(0);
                                    player.setTotalExperience(0);

                                    // Add frasco de xp custom no inv do player
                                    player.getInventory().addItem(xpBottle);

                                    // Atualizar o inv do player
                                    player.closeInventory();
                                    player.updateInventory();
                                }
                           // }
                        } else {
                            player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu frasco.");
                        }
                    }

                    // Sistema dos Frasco Custom
                    if (item.getType() == Material.EXP_BOTTLE && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();

                        // Frasco de Experiência Avançado
                        if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + advancedFraskType)) {
                            if(isValidStackFrask(player, item, advancedFraskType) || player.getInventory().firstEmpty() != -1) {
                                // Xp Avançado
                                int xp = player.getTotalExperience();

                                if (xp >= xpAdvancedCost) {
                                    player.setTotalExperience(xp - xpAdvancedCost);
                                    player.setLevel(0);

                                    ItemStack advancedXpBottle = createCustomXpBottle(advancedFraskType, xpAdvancedCost);
                                    player.getInventory().addItem(advancedXpBottle);

                                    //Atualizar item do minecart no inventario do jogador
                                    updateMinecartItem(player);
                                    updateXpTotal(player);
                                    player.updateInventory();

                                    player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW + "Frasco Avançado " + ChatColor.GREEN + "por " + ChatColor.YELLOW + formatExp(xpAdvancedCost) + "XP");
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu frasco.");
                            }
                        // Frasco de Experiência Normal
                        } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + normalFraskType)) {
                            if (isValidStackFrask(player, item, normalFraskType) || player.getInventory().firstEmpty() != -1) {
                                // Xp Normal
                                int xp = player.getTotalExperience();
                                if (xp >= xpNormalCost) {
                                    // Remover a quant de xp correto
                                    player.setTotalExperience(xp - xpNormalCost);
                                    player.setLevel(0);
                                    // Criar um frasco de xp avançado para o jogador
                                    ItemStack normalXpBottle = createCustomXpBottle(normalFraskType, xpNormalCost);
                                    player.getInventory().addItem(normalXpBottle);

                                    //Atualizar item do minecart no inventario do jogador
                                    updateMinecartItem(player);
                                    updateXpTotal(player);
                                    player.updateInventory();

                                    player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW + "Frasco Normal " + ChatColor.GREEN + "por " + ChatColor.YELLOW + formatExp(xpNormalCost) + "XP");
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu frasco.");
                            }
                        // Frasco de Experiência Simples
                        } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + simpleFraskType)) {
                            if (isValidStackFrask(player, item, simpleFraskType) || player.getInventory().firstEmpty() != -1) {
                                // Xp Simples
                                int xp = player.getTotalExperience();
                                if (xp >= xpSimpleCost) {
                                    // Remover a quant de xp correto
                                    player.setTotalExperience(xp - xpSimpleCost);
                                    player.setLevel(0);
                                    // Criar um frasco de xp avançado para o jogador
                                    ItemStack simpleXpBottle = createCustomXpBottle(simpleFraskType, xpSimpleCost);
                                    player.getInventory().addItem(simpleXpBottle);

                                    //Atualizar item do minecart no inventario do jogador
                                    updateMinecartItem(player);
                                    updateXpTotal(player);
                                    player.updateInventory();

                                    player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW + "Frasco Simples " + ChatColor.GREEN + "por " + ChatColor.YELLOW + formatExp(xpSimpleCost) + "XP");
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu frasco.");
                            }
                        }
                    }

                    // Sistema livro de Encantamentos Avançado quando Clicado com Botão Esquerdo
                    if (item.getType() == Material.BOOK && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();

                        if (meta.hasLore()) {
                            List<String> lore = meta.getLore();

                            for (String loreLine : lore) {
                                if(loreLine.equals(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookAdvancedType)) {
                                    int xp = player.getTotalExperience();

                                    if (isValidStackBook(player, item, eBookAdvancedType) || player.getInventory().firstEmpty() != -1) {
                                        if (xp >= eBookAdvancedCost) {
                                            // Remover o xp do player
                                            player.setTotalExperience(xp - eBookAdvancedCost);
                                            player.setLevel(0);

                                            // Criar um Livro de Encantamentos do Tipo Avançado
                                            ItemStack eBookAdvanced = createCustomBook(eBookAdvancedType);

                                            // Add livro ao inv
                                            player.getInventory().addItem(eBookAdvanced);

                                            updateMinecartItem(player);
                                            updateXpTotal(player);
                                            player.updateInventory();

                                            player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW +
                                                    "Livro Encantado" + ChatColor.GREEN + " do tipo " +
                                                    ChatColor.YELLOW + eBookAdvancedType);
                                        } else {
                                            event.setCancelled(true);
                                            player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu livro.");
                                    }
                                } else if(loreLine.equals(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookNormalType)) {
                                    int xp = player.getTotalExperience();

                                    if (isValidStackBook(player, item, eBookNormalType) || player.getInventory().firstEmpty() != -1) {
                                        if (xp >= eBookNormalCost) {
                                            // Remover o xp do player
                                            player.setTotalExperience(xp - eBookNormalCost);
                                            player.setLevel(0);

                                            // Criar um Livro de Encantamentos do Tipo Avançado
                                            ItemStack eBookNormal = createCustomBook(eBookNormalType);

                                            // Add livro ao inv
                                            player.getInventory().addItem(eBookNormal);

                                            updateMinecartItem(player);
                                            updateXpTotal(player);
                                            player.updateInventory();

                                            player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW +
                                                    "Livro Encantado" + ChatColor.GREEN + " do tipo " +
                                                    ChatColor.YELLOW + eBookNormalType);
                                        } else {
                                            event.setCancelled(true);
                                            player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu livro.");
                                    }
                                } else if(loreLine.equals(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookSimpleType)) {

                                    int xp = player.getTotalExperience();

                                    if (isValidStackBook(player, item, eBookSimpleType) || player.getInventory().firstEmpty() != -1) {
                                        if (xp >= eBookSimpleCost) {
                                            // Remover o xp do player
                                            player.setTotalExperience(xp - eBookSimpleCost);
                                            player.setLevel(0);

                                            // Criar um Livro de Encantamentos do Tipo Avançado
                                            ItemStack eBookSimple = createCustomBook(eBookSimpleType);

                                            // Add livro ao inv
                                            player.getInventory().addItem(eBookSimple);

                                            updateMinecartItem(player);
                                            updateXpTotal(player);
                                            player.updateInventory();

                                            player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW +
                                                    "Livro Encantado" + ChatColor.GREEN + " do tipo " +
                                                    ChatColor.YELLOW + eBookSimpleType);
                                        } else {
                                            event.setCancelled(true);
                                            player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adquirir seu livro.");
                                    }
                                }

                                break;
                            }
                        }
                    }

                    // Sistema para abrir o GUI do reciclador
                    if(item.getType() == Material.HOPPER && event.isLeftClick()) {
                        ItemMeta meta = item.getItemMeta();
                        if(meta.hasDisplayName()) {
                            if(meta.getDisplayName().equals(ChatColor.DARK_GREEN + "Reciclador")) {
                                RecyclerGUI recyclerGUI = new RecyclerGUI();
                                recyclerGUI.RecyclerInventory(player);
                            }
                        }
                    }
                }
                // Verificação para ver se está clicando com botão direito no bottom inv com GUI aberto
                if (event.getClickedInventory().equals(openLower)) {
                    event.setCancelled(true);
                } else {
                    // Sistema livro de Encantamentos quando Clicado com Botão Direito
                    if (item.getType() == Material.BOOK && event.isRightClick()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta.hasLore()) {
                            List<String> lore = meta.getLore();
                            // Verificar se a lore contem as linhas especificas
                            if (lore.contains(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookAdvancedType)) {
                                // Abrir um novo GUI
                                AdvancedEnchantsGUI advancedEnchantsGUI = new AdvancedEnchantsGUI();
                                advancedEnchantsGUI.AdvancedEnchantInventory(player);
                            } else if (lore.contains(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookSimpleType)) {
                                // Abrir um novo GUI
                                SimpleEnchantsGUI simpleEnchantsGUI = new SimpleEnchantsGUI();
                                simpleEnchantsGUI.SimpleEnchantInventory(player);
                            } else if(lore.contains(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookNormalType)) {
                                // Abrir um novo GUI
                                NormalEnchantsGUI normalEnchantsGUI = new NormalEnchantsGUI();
                                normalEnchantsGUI.NormalEnchantInventory(player);
                            }
                         }
                    }
                }
            }
        }
    }

    private boolean isValidStackFrask(Player player, ItemStack item, String eFraskType) {
        ItemStack[] inventoryContents = player.getInventory().getContents();

        ItemMeta meta = item.getItemMeta();

        if (!meta.hasDisplayName()) {
            return false;
        }

        String expectedName = ChatColor.YELLOW + eFraskType;

        ItemStack existingFrask = null;

        for (ItemStack stack : inventoryContents) {
            if(stack != null && stack.getType() == Material.EXP_BOTTLE) {
                ItemMeta stackMeta = stack.getItemMeta();
                if (stackMeta != null && stackMeta.hasDisplayName() &&
                        stackMeta.getDisplayName().contains(expectedName)) {
                    if (stack.getAmount() < 64) {
                        existingFrask = stack;
                        break;
                    }
                }
            }
        }

        return existingFrask != null;
    }

    private boolean isValidStackBook(Player player, ItemStack item, String eBookType) {
        ItemStack[] inventoryContents = player.getInventory().getContents();

        ItemMeta meta = item.getItemMeta();

        if (!meta.hasLore()) {
            return false; // Não tem lore
        }

        String expectedLore = ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookType;

        ItemStack existingBook = null;

        for (ItemStack stack : inventoryContents) {
            if(stack != null && stack.getType() == Material.BOOK) {
                ItemMeta stackMeta = stack.getItemMeta();
                if (stackMeta != null && stackMeta.hasLore() &&
                        stackMeta.getLore().contains(expectedLore)) {
                    if (stack.getAmount() < 64) {
                        existingBook = stack;
                        break;
                    }
                }
            }
        }

        return existingBook != null;
    }

    private void updateMinecartItem(Player player) {
        int xp = player.getTotalExperience();
        String formattedXp = String.format("%,d", xp);
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if(inventory != null) {
            ItemStack myxp = inventory.getItem(13);

            if(myxp != null && myxp.getType() == Material.MINECART) {
                ItemMeta myxpmeta = myxp.getItemMeta();
                myxpmeta.setDisplayName(ChatColor.YELLOW + "Sua experiência");

                List<String> xplore = new ArrayList<>();
                xplore.add(ChatColor.WHITE + "XP atual: " + ChatColor.GRAY + formattedXp);
                myxpmeta.setLore(xplore);

                myxp.setItemMeta(myxpmeta);
            }
        }
    }

    private void updateXpTotal(Player player) {
        int xp = player.getTotalExperience();
        String formattedXp = String.format("%,d", xp);
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if(inventory != null) {
            ItemStack myxp = inventory.getItem(29);

            if(myxp != null && myxp.getType() == Material.EXP_BOTTLE) {
                if(xp > 0) {
                    ItemMeta myxpmeta = myxp.getItemMeta();
                    myxpmeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência");

                    List<String> xplore = new ArrayList<>();
                    xplore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + formattedXp);
                    xplore.add("");
                    xplore.add(ChatColor.GRAY + "Você receberá um frasco de ");
                    xplore.add(ChatColor.GRAY + "experiência com " + formattedXp + " XP");
                    myxpmeta.setLore(xplore);

                    myxp.setItemMeta(myxpmeta);
                    inventory.setItem(29, myxp);
                } else {
                    ItemStack gBottle = new ItemStack(Material.GLASS_BOTTLE);
                    ItemMeta gBottleMeta = gBottle.getItemMeta();
                    gBottleMeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência");
                    List<String> gBottleLore = new ArrayList<>();
                    gBottleLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xp);
                    gBottleLore.add("");
                    gBottleLore.add(ChatColor.RED + "Você não possui XP");
                    gBottleLore.add(ChatColor.RED + "para ser coletado!");

                    gBottleMeta.setLore(gBottleLore);
                    gBottle.setItemMeta(gBottleMeta);
                    inventory.setItem(29, gBottle);
                }
            }
        }
    }

    @EventHandler
    public void onRightClickInBottle(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        ItemMeta frasco = item.getItemMeta();

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(customXpBottle(totalFraskType, item) || customXpBottle(advancedFraskType, item)
                    || customXpBottle(normalFraskType, item) || customXpBottle(simpleFraskType, item)) {
                int xpAmount = getStoredExperience(item);

                if(xpAmount > 0) {
                    event.setCancelled(true);

                    // Verificar a quant do frasco no slot
                    int amountInHand = item.getAmount();

                    if(amountInHand == 1) {
                        // Se houver apenas 1 remover
                        player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                        player.giveExp(xpAmount);
                        player.sendMessage(ChatColor.GREEN + "Você consumiu um " + frasco.getDisplayName() + ChatColor.GREEN + " com " + ChatColor.YELLOW + formatExp(xpAmount) + "XP");
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        // Se houver mais de um 1 apenas diminuir no slot
                        item.setAmount(amountInHand - 1);
                        player.giveExp(xpAmount);
                        player.sendMessage(ChatColor.GREEN + "Você consumiu um " + frasco.getDisplayName() + ChatColor.GREEN + " com " + ChatColor.YELLOW + formatExp(xpAmount) + "XP");
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    }

                }
            }
        }
    }

    // Verificar se um item é um frasco de xp custom total
        private boolean customXpBottle(String nameBottle,ItemStack item) {
            if(item.getType() == Material.EXP_BOTTLE && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + nameBottle)) {
                    return true;
                }
            }
            return false;
        }

        private String formatExp(int xpAmount) {
            return String.format("%,d", xpAmount);
        }

        // Obter a quantidade de xp armazenada em um frasco de xp total
        private int getStoredExperience(ItemStack item) {
            if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                for (String line : lore) {
                    if(line.startsWith(ChatColor.WHITE + "Custo: " + ChatColor.GREEN)) {
                        try {
                            // Extrair a quantidade de experiência da linha do lore
                            String xpString = line.substring((ChatColor.WHITE + "Custo: " + ChatColor.GREEN).length());

                            xpString = xpString.replace(" XP", "");

                            xpString = xpString.replaceAll("[.,]", "");

                            return Integer.parseInt(xpString);
                        } catch (NumberFormatException e) {
                            // Se houver um erro ao analisar a quantidade de experiência, retornar 0
                            return 0;
                        }
                        }
                    }
                }
            return 0;
        }

        // Criar um frasco de xp custom com a quant de xp
        private ItemStack createCustomXpBottle(String nameXpBottle, int xpAmount) {
            ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE, 1);
            ItemMeta meta = xpBottle.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + nameXpBottle);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + formatExp(xpAmount) + " XP");
            meta.setLore(lore);
            xpBottle.setItemMeta(meta);
            return xpBottle;
        }

        // Criar um livro custom com tipo do livro
        private ItemStack createCustomBook(String eBookType) {
            ItemStack customBook = new ItemStack(Material.BOOK);
            ItemMeta customBookMeta = customBook.getItemMeta();

            customBookMeta.setDisplayName(ChatColor.YELLOW + "Livro de Encantamentos");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookType);
            customBookMeta.setLore(lore);
            customBook.setItemMeta(customBookMeta);
            return customBook;
        }
    }
