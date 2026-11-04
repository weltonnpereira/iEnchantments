package voxel.wp.ienchantments.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.gui.AdvancedEnchantsGUI;
import voxel.wp.ienchantments.gui.MainGUI;
import voxel.wp.ienchantments.gui.SimpleEnchantsGUI;
import voxel.wp.ienchantments.gui.NormalEnchantsGUI;

import java.util.ArrayList;
import java.util.List;

import static voxel.wp.ienchantments.gui.MainGUI.*;

public class FlaskBookManager {

    private final PlManager plManager;

    public FlaskBookManager(PlManager plManager) {
        this.plManager = plManager;
    }

    public void handleTotalXpBottleWithdraw(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Your inventory is full. Clear space before acquiring your flask.");
            return;
        }

        int xpAmount = getStoredExperience(item);
        ItemStack xpBottle = createCustomXpBottle(MainGUI.totalFlaskType, xpAmount);
        ItemMeta meta = xpBottle.getItemMeta();

        if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Experience Flask")) {
            player.setLevel(0);
            player.setExp(0);
            player.setTotalExperience(0);

            player.getInventory().addItem(xpBottle);
            player.closeInventory();
            player.updateInventory();
        }
    }

    public void handleBottlePurchase(Player player, ItemStack item, String flaskType, int cost) {
        if (!isValidStackFlask(player, item, flaskType) && player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Your inventory is full. Clear space before acquiring your flask.");
            return;
        }

        int xp = player.getTotalExperience();
        if (xp < cost) {
            player.sendMessage(ChatColor.RED + "You do not have enough XP!");
            return;
        }

        player.setTotalExperience(xp - cost);
        player.setLevel(0);

        ItemStack xpBottle = createCustomXpBottle(flaskType, cost);
        player.getInventory().addItem(xpBottle);

        updateMinecartItem(player);
        updateXpTotal(player);
        player.updateInventory();

        player.sendMessage(ChatColor.GREEN + "You purchased a " + ChatColor.YELLOW + flaskType + " " + ChatColor.GREEN + "for " + ChatColor.YELLOW + formatExp(cost) + " XP");
    }

    public void handleBookPurchase(Player player, ItemStack item, String eBookType, int cost) {
        if (!isValidStackBook(player, item, eBookType) && player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Your inventory is full. Clear space before acquiring your book.");
            return;
        }

        int xp = player.getTotalExperience();
        if (xp < cost) {
            player.sendMessage(ChatColor.RED + "You do not have enough XP!");
            return;
        }

        player.setTotalExperience(xp - cost);
        player.setLevel(0);

        ItemStack customBook = createCustomBook(eBookType);
        player.getInventory().addItem(customBook);

        updateMinecartItem(player);
        updateXpTotal(player);
        player.updateInventory();

        player.sendMessage(ChatColor.GREEN + "You purchased an " + ChatColor.YELLOW + "Enchanted Book" + ChatColor.GREEN + " of type " + ChatColor.YELLOW + eBookType);
    }

    public void updateMinecartItem(Player player) {
        int xp = player.getTotalExperience();
        String formattedXp = formatExp(xp);
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (inventory != null) {
            ItemStack myxp = inventory.getItem(13);
            if (myxp != null && myxp.getType() == Material.MINECART) {
                ItemMeta myxpmeta = myxp.getItemMeta();
                myxpmeta.setDisplayName(ChatColor.YELLOW + "Your Experience");

                List<String> xplore = new ArrayList<>();
                xplore.add(ChatColor.WHITE + "Current XP: " + ChatColor.GRAY + formattedXp);
                myxpmeta.setLore(xplore);

                myxp.setItemMeta(myxpmeta);
            }
        }
    }

    public void updateXpTotal(Player player) {
        int xp = player.getTotalExperience();
        String formattedXp = formatExp(xp);
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (inventory != null) {
            ItemStack myxp = inventory.getItem(29);
            if (myxp != null) {
                if (xp > 0) {
                    ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);
                    ItemMeta meta = xpBottle.getItemMeta();
                    meta.setDisplayName(ChatColor.YELLOW + "Experience Flask");
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GREEN + formattedXp + " XP");
                    lore.add("");
                    lore.add(ChatColor.GRAY + "You will receive an experience ");
                    lore.add(ChatColor.GRAY + "flask with " + formattedXp + " XP");
                    meta.setLore(lore);
                    xpBottle.setItemMeta(meta);
                    inventory.setItem(29, xpBottle);
                } else {
                    ItemStack gBottle = new ItemStack(Material.GLASS_BOTTLE);
                    ItemMeta gBottleMeta = gBottle.getItemMeta();
                    gBottleMeta.setDisplayName(ChatColor.YELLOW + "Experience Flask");
                    List<String> gBottleLore = new ArrayList<>();
                    gBottleLore.add(ChatColor.WHITE + "Cost: " + ChatColor.GREEN + xp + " XP");
                    gBottleLore.add("");
                    gBottleLore.add(ChatColor.RED + "You do not have XP");
                    gBottleLore.add(ChatColor.RED + "to be collected!");
                    gBottleMeta.setLore(gBottleLore);
                    gBottle.setItemMeta(gBottleMeta);
                    inventory.setItem(29, gBottle);
                }
            }
        }
    }

    public boolean customXpBottle(String nameBottle, ItemStack item) {
        if (item.getType() == Material.EXP_BOTTLE && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + nameBottle);
        }
        return false;
    }

    public String formatExp(int xpAmount) {
        return String.format("%,d", xpAmount);
    }

    public int getStoredExperience(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String line : lore) {
                if (line.startsWith(ChatColor.WHITE + "Cost: " + ChatColor.GREEN)) {
                    try {
                        String xpString = line.substring((ChatColor.WHITE + "Cost: " + ChatColor.GREEN).length());
                        xpString = xpString.replace(" XP", "").replaceAll("[.,]", "");
                        return Integer.parseInt(xpString);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    public ItemStack createCustomXpBottle(String nameXpBottle, int xpAmount) {
        ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE, 1);
        ItemMeta meta = xpBottle.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + nameXpBottle);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GREEN + formatExp(xpAmount) + " XP");
        meta.setLore(lore);
        xpBottle.setItemMeta(meta);
        return xpBottle;
    }

    public ItemStack createCustomBook(String eBookType) {
        ItemStack customBook = new ItemStack(Material.BOOK);
        ItemMeta customBookMeta = customBook.getItemMeta();
        customBookMeta.setDisplayName(ChatColor.YELLOW + "Enchanted Book");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Type: " + ChatColor.WHITE + eBookType);
        customBookMeta.setLore(lore);
        customBook.setItemMeta(customBookMeta);
        return customBook;
    }

    public String getBookTypeFromLore(List<String> lore) {
        String typeLinePrefix = ChatColor.GRAY + "Type: " + ChatColor.WHITE;
        for (String line : lore) {
            if (line.startsWith(typeLinePrefix)) {
                return line.replace(typeLinePrefix, "");
            }
        }
        return null;
    }

    public int getBookCost(String eBookType) {
        switch (eBookType) {
            case eBookAdvancedType:
                return plManager.getMainGUI().getEBookAdvancedCost();
            case eBookNormalType:
                return plManager.getMainGUI().getEBookNormalCost();
            case eBookSimpleType:
                return plManager.getMainGUI().getEBookSimpleCost();
        }
        return 0;
    }

    public void openBookGui(Player player, String eBookType) {
        if (eBookType.equals(eBookAdvancedType)) {
            new AdvancedEnchantsGUI(plManager).AdvancedEnchantInventory(player);
        } else if (eBookType.equals(eBookSimpleType)) {
            new SimpleEnchantsGUI(plManager).SimpleEnchantInventory(player);
        } else if (eBookType.equals(eBookNormalType)) {
            new NormalEnchantsGUI(plManager).NormalEnchantInventory(player);
        }
    }

    private boolean isValidStackFlask(Player player, ItemStack item, String eFlaskType) {
        ItemStack[] inventoryContents = player.getInventory().getContents();

        ItemMeta meta = item.getItemMeta();

        if (!meta.hasDisplayName()) {
            return false;
        }

        String expectedName = ChatColor.YELLOW + eFlaskType;

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
}