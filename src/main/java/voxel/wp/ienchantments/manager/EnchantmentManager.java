package voxel.wp.ienchantments.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import voxel.wp.ienchantments.Main;
import voxel.wp.ienchantments.gui.MainGUI;
import voxel.wp.ienchantments.utils.VerifyEnchantments;
import voxel.wp.ienchantments.utils.VerifyItems;

import java.util.*;

import static voxel.wp.ienchantments.gui.MainGUI.*;

public class EnchantmentManager {

    private final PlManager plManager;
    private final Random random = new Random();

    // Target Categories
    private static final String ARMOR = "Armors";
    private static final String ARMS = "Arms";
    private static final String TOOLS = "Tools";
    private static final String HELMETS = "Helmets";
    private static final String BOOTS = "Boots";
    private static final String SWORDS = "Swords";
    private static final String BOW = "Bow";
    private static final String PICKAXE = "Pickaxe";
    private static final String AXE = "Axe";

    public enum BookType {
        SIMPLE(ChatColor.YELLOW + eBookSimpleType, 15, 76),   // 15% to 90%
        NORMAL(ChatColor.YELLOW + eBookNormalType, 10, 70),   // 10% to 80%
        ADVANCED(ChatColor.YELLOW + eBookAdvancedType, 5, 56); // 5% to 60%

        private final String displayName;
        private final int minPercentage;
        private final int percentageBound;

        BookType(String displayName, int minPercentage, int percentageBound) {
            this.displayName = displayName;
            this.minPercentage = minPercentage;
            this.percentageBound = percentageBound;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int generatePercentage(Random random) {
            return random.nextInt(percentageBound) + minPercentage;
        }
    }

    public static class EnchantmentData {
        private final Enchantment enchantment;
        private final int simpleLevel;   // -1 = N/A | 0 = Random (1-2)
        private final int normalLevel;   // -1 = N/A | 0 = Random (3-4)
        private final int advancedLevel; // -1 = N/A
        private final String target;

        public EnchantmentData(Enchantment enchantment, int simpleLevel, int normalLevel, int advancedLevel, String target) {
            this.enchantment = enchantment;
            this.simpleLevel = simpleLevel;
            this.normalLevel = normalLevel;
            this.advancedLevel = advancedLevel;
            this.target = target;
        }

        public boolean isAvailableIn(BookType type) {
            switch (type) {
                case SIMPLE: return simpleLevel != -1;
                case NORMAL: return normalLevel != -1;
                case ADVANCED: return advancedLevel != -1;
                default: return false;
            }
        }

        public int getLevel(BookType type, Random random) {
            switch (type) {
                case SIMPLE:
                    return (simpleLevel == 0) ? random.nextInt(2) + 1 : simpleLevel;
                case NORMAL:
                    return (normalLevel == 0) ? random.nextInt(2) + 3 : normalLevel;
                case ADVANCED:
                    return advancedLevel;
                default:
                    return 1;
            }
        }

        public String matchesCategory(int level) {
            if (advancedLevel != -1 && level == advancedLevel) return eBookAdvancedType;
            if (normalLevel != -1 && ((normalLevel == 0 && (level == 3 || level == 4)) || level == normalLevel)) return eBookNormalType;
            if (simpleLevel != -1 && ((simpleLevel == 0 && (level == 1 || level == 2)) || level == simpleLevel)) return eBookSimpleType;
            return null;
        }
    }

    // Tabela única com todas as regras de encantamento
    private final List<EnchantmentData> registry = Arrays.asList(
            new EnchantmentData(Enchantment.PROTECTION_ENVIRONMENTAL, 0, 0, 5, ARMOR),
            new EnchantmentData(Enchantment.PROTECTION_FIRE,          0, 3, 4, ARMOR),
            new EnchantmentData(Enchantment.DURABILITY,                0, 0, 5, ARMOR + "/" + ARMS + "/" + BOW + "/" + TOOLS),
            new EnchantmentData(Enchantment.DAMAGE_ALL,                0, 0, 5, ARMS),
            new EnchantmentData(Enchantment.OXYGEN,                    1, 2, 3, HELMETS),
            new EnchantmentData(Enchantment.PROTECTION_FALL,          0, 3, 4, BOOTS),
            new EnchantmentData(Enchantment.DEPTH_STRIDER,             1, 2, 3, BOOTS),
            new EnchantmentData(Enchantment.LOOT_BONUS_MOBS,           1, 2, 3, SWORDS),
            new EnchantmentData(Enchantment.DIG_SPEED,                 0, 0, 5, TOOLS),
            new EnchantmentData(Enchantment.LOOT_BONUS_BLOCKS,         1, 2, 3, PICKAXE + "/" + AXE),
            new EnchantmentData(Enchantment.ARROW_DAMAGE,              0, 0, 5, BOW),
            new EnchantmentData(Enchantment.FIRE_ASPECT,              -1, 1, 2, ARMS),
            new EnchantmentData(Enchantment.ARROW_KNOCKBACK,          -1, 1, 2, BOW),
            new EnchantmentData(Enchantment.ARROW_INFINITE,           -1, -1, 1, BOW),
            new EnchantmentData(Enchantment.ARROW_FIRE,               -1, -1, 1, BOW),
            new EnchantmentData(Enchantment.SILK_TOUCH,                -1, -1, 1, PICKAXE),
            new EnchantmentData(Enchantment.WATER_WORKER,              -1, -1, 1, HELMETS)
    );

    public EnchantmentManager(PlManager plManager) {
        this.plManager = plManager;
    }

    public EnchantmentManager.BookType resolveBookType(String typeStr) {
        if (typeStr.equalsIgnoreCase(eBookSimpleType)) return BookType.SIMPLE;
        if (typeStr.equalsIgnoreCase(eBookNormalType)) return BookType.NORMAL;
        if (typeStr.equalsIgnoreCase(eBookAdvancedType)) return BookType.ADVANCED;
        return null;
    }

    public String getCategory(Enchantment enchantment, int level) {
        for (EnchantmentData data : registry) {
            if (data.enchantment.equals(enchantment)) {
                String category = data.matchesCategory(level);
                if (category != null) return category;
            }
        }
        return null;
    }

    /**
     * Geração centralizada dos Livros Encantados com lore e chance de sucesso.
     */
    public void createEnchantedBook(Player player, BookType bookType) {
        List<EnchantmentData> available = new ArrayList<>();
        for (EnchantmentData data : registry) {
            if (data.isAvailableIn(bookType)) {
                available.add(data);
            }
        }

        if (available.isEmpty()) return;

        EnchantmentData chosenData = available.get(random.nextInt(available.size()));
        int level = chosenData.getLevel(bookType, random);
        int percentage = bookType.generatePercentage(random);

        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

        if (meta == null) return;

        meta.setDisplayName(bookType.getDisplayName());
        meta.addStoredEnchant(chosenData.enchantment, level, true);

        List<String> lore = new ArrayList<>();
        lore.add("");
        String colorPercentage = (percentage <= 25) ? ChatColor.RED.toString() : ChatColor.GREEN.toString();
        lore.add(colorPercentage + "Success Chance: " + percentage + "%");
        lore.add("Enchant: " + chosenData.target);

        meta.setLore(lore);
        enchantedBook.setItemMeta(meta);

        player.getInventory().addItem(enchantedBook);
    }

    /**
     * Identifica e retorna o BookType com base no display name do ItemStack.
     */
    public String getEnchantedBookTypeFromItem(ItemStack item) {
        if (item == null || item.getType() != Material.ENCHANTED_BOOK || !item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return null;

        String displayName = meta.getDisplayName();
        if (displayName.equals(ChatColor.YELLOW + eBookAdvancedType)) return eBookAdvancedType;
        if (displayName.equals(ChatColor.YELLOW + eBookNormalType)) return eBookNormalType;
        if (displayName.equals(ChatColor.YELLOW + eBookSimpleType)) return eBookSimpleType;

        return null;
    }

    /**
     * Aplica o livro do cursor sobre o item clicado no inventário (com teste de porcentagem de sucesso).
     */
    public boolean applyBookToItem(Player player, ItemStack targetItem, ItemStack bookItem) {
        if (!isValidTargetItem(targetItem, bookItem)) {
            player.sendMessage(ChatColor.RED + "This enchantment cannot be applied to this item.");
            player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0f, 1.0f);
            return false;
        }

        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) bookItem.getItemMeta();
        if (bookMeta == null || !bookMeta.hasStoredEnchants()) return false;

        int successChance = extractSuccessChance(bookMeta.getLore());
        int roll = random.nextInt(100) + 1; // 1 a 100

        if (roll <= successChance) {
            // Sucesso ao aplicar
            for (Map.Entry<Enchantment, Integer> entry : bookMeta.getStoredEnchants().entrySet()) {
                targetItem.addUnsafeEnchantment(entry.getKey(), entry.getValue());
            }
            player.sendMessage(ChatColor.GREEN + "Enchantment successfully applied!");
            player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 1.0f, 1.0f);
        } else {
            // Falha na aplicação
            player.sendMessage(ChatColor.RED + "The enchantment failed to apply!");
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
        }

        return true;
    }

    private int extractSuccessChance(List<String> lore) {
        if (lore == null) return 100;
        for (String line : lore) {
            if (line.contains("Success Chance:")) {
                String clean = ChatColor.stripColor(line).replaceAll("[^0-9]", "");
                try {
                    return Integer.parseInt(clean);
                } catch (NumberFormatException ignored) {}
            }
        }
        return 100;
    }

    /**
     * Valida se o item de destino aceita os encantamentos presentes no livro.
     */
    public boolean isValidTargetItem(ItemStack item, ItemStack enchantedBook) {
        if (item == null || enchantedBook == null || !(enchantedBook.getItemMeta() instanceof EnchantmentStorageMeta)) {
            return false;
        }

        Material itemType = item.getType();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
        Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

        VerifyEnchantments verifyEnchantments = plManager.getVerifyEnchantments();
        VerifyItems verifyItems = plManager.getVerifyItems();

        if (verifyItems.isArmor(itemType) && (verifyEnchantments.hasProtection(enchantments) || verifyEnchantments.hasFireProtection(enchantments)
                || verifyEnchantments.hasUnbreaking(enchantments))) {
            return true;
        }

        if (verifyItems.isHelmet(itemType) && (verifyEnchantments.hasRespiration(enchantments) || verifyEnchantments.hasAquaInfinity(enchantments))) {
            return true;
        }

        if (verifyItems.isBoots(itemType) && (verifyEnchantments.hasDepthStrider(enchantments) || verifyEnchantments.hasFeatherFalling(enchantments))) {
            return true;
        }

        if (verifyItems.isSword(itemType) && (verifyEnchantments.hasSharpness(enchantments) || (verifyEnchantments.hasLooting(enchantments))
                || verifyEnchantments.hasUnbreaking(enchantments) || verifyEnchantments.hasFireAspect(enchantments))) {
            return true;
        }

        if (verifyItems.isAxe(itemType) && (verifyEnchantments.hasSharpness(enchantments) ||
                verifyEnchantments.hasUnbreaking(enchantments) || verifyEnchantments.hasFireAspect(enchantments) ||
                verifyEnchantments.hasFortune(enchantments))) {
            return true;
        }

        if (verifyItems.isBow(itemType) && (verifyEnchantments.hasFlame(enchantments) || verifyEnchantments.hasPower(enchantments)
                || verifyEnchantments.hasInfinity(enchantments) || verifyEnchantments.hasPunch(enchantments) || verifyEnchantments.hasUnbreaking(enchantments))) {
            return true;
        }

        if (verifyItems.isTool(itemType) && (verifyEnchantments.hasEfficiency(enchantments) || verifyEnchantments.hasUnbreaking(enchantments))) {
            return true;
        }

        if (verifyItems.isHoe(itemType) && (verifyEnchantments.hasUnbreaking(enchantments))) {
            return true;
        }

        if (verifyItems.isPickaxe(itemType) && (verifyEnchantments.hasSilkTouch(enchantments) || verifyEnchantments.hasFortune(enchantments))) {
            return true;
        }

        return false;
    }
}