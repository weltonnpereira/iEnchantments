package voxel.wp.ienchantments.listeners;

import voxel.wp.ienchantments.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class AdvancedEnchantsGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void AdvancedEnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_PURPLE + "Encantamentos - Livro Avançado");

        // Levels
        int level1 = 1;
        int level2 = 2;
        int level3 = 3;
        int level4 = 4;
        int level5 = 5;

        // Protection
        Enchantment protection = Enchantment.PROTECTION_ENVIRONMENTAL;
        // Fire Protection
        Enchantment protectionFire = Enchantment.PROTECTION_FIRE;
        // Unbreaking
        Enchantment unbreaking = Enchantment.DURABILITY;
        // Sharpness
        Enchantment sharpness = Enchantment.DAMAGE_ALL;
        // Respiration
        Enchantment respiration = Enchantment.OXYGEN;
        // Feather Falling
        Enchantment featherFalling = Enchantment.PROTECTION_FALL;
        // Death Strider
        Enchantment deathStrider = Enchantment.DEPTH_STRIDER;
        // Looting
        Enchantment looting = Enchantment.LOOT_BONUS_MOBS;
        // Effiency
        Enchantment efficiency = Enchantment.DIG_SPEED;
        // Fortune
        Enchantment fortune = Enchantment.LOOT_BONUS_BLOCKS;
        // Power
        Enchantment power = Enchantment.ARROW_DAMAGE;
        // Infinity
        Enchantment infinity = Enchantment.ARROW_INFINITE;
        // Fire Aspect
        Enchantment fireAspect = Enchantment.FIRE_ASPECT;
        // Flame
        Enchantment flame = Enchantment.ARROW_FIRE;
        // Silk Touch
        Enchantment silkTouch = Enchantment.SILK_TOUCH;
        // Punch
        Enchantment punch = Enchantment.ARROW_KNOCKBACK;
        // Aqua Infinity
        Enchantment aquaInfinity = Enchantment.WATER_WORKER;

        // Livros Encantados
        ItemStack p5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p5meta = (EnchantmentStorageMeta) p5.getItemMeta();
        p5meta.addStoredEnchant(protection, level5, true);
        p5.setItemMeta(p5meta);
        i.setItem(10, p5);

        ItemStack pFire4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta pFire4Meta = (EnchantmentStorageMeta) pFire4.getItemMeta();
        pFire4Meta.addStoredEnchant(protectionFire, level4, true);
        pFire4.setItemMeta(pFire4Meta);
        i.setItem(11, pFire4);

        ItemStack unbreaking5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta unbreaking5Meta = (EnchantmentStorageMeta) unbreaking5.getItemMeta();
        unbreaking5Meta.addStoredEnchant(unbreaking, level5, true);
        unbreaking5.setItemMeta(unbreaking5Meta);
        i.setItem(12, unbreaking5);

        ItemStack s5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta s5Meta = (EnchantmentStorageMeta) s5.getItemMeta();
        s5Meta.addStoredEnchant(sharpness, level5, true);
        s5.setItemMeta(s5Meta);
        i.setItem(13, s5);

        ItemStack respiration3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta respiration3Meta = (EnchantmentStorageMeta) respiration3.getItemMeta();
        respiration3Meta.addStoredEnchant(respiration, level3, true);
        respiration3.setItemMeta(respiration3Meta);
        i.setItem(14, respiration3);

        ItemStack featherFalling4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta featherFalling4Meta = (EnchantmentStorageMeta) featherFalling4.getItemMeta();
        featherFalling4Meta.addStoredEnchant(featherFalling, level4, true);
        featherFalling4.setItemMeta(featherFalling4Meta);
        i.setItem(15, featherFalling4);

        ItemStack deathStrider3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta deathStrider3Meta = (EnchantmentStorageMeta) deathStrider3.getItemMeta();
        deathStrider3Meta.addStoredEnchant(deathStrider, level3, true);
        deathStrider3.setItemMeta(deathStrider3Meta);
        i.setItem(16, deathStrider3);

        ItemStack looting3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta looting3Meta = (EnchantmentStorageMeta) looting3.getItemMeta();
        looting3Meta.addStoredEnchant(looting, level3, true);
        looting3.setItemMeta(looting3Meta);
        i.setItem(19, looting3);

        ItemStack efficiency5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta efficiency5Meta = (EnchantmentStorageMeta) efficiency5.getItemMeta();
        efficiency5Meta.addStoredEnchant(efficiency, level5, true);
        efficiency5.setItemMeta(efficiency5Meta);
        i.setItem(20, efficiency5);

        ItemStack fortune3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta fortune3Meta = (EnchantmentStorageMeta) fortune3.getItemMeta();
        fortune3Meta.addStoredEnchant(fortune, level3, true);
        fortune3.setItemMeta(fortune3Meta);
        i.setItem(21, fortune3);

        ItemStack power5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta power5Meta = (EnchantmentStorageMeta) power5.getItemMeta();
        power5Meta.addStoredEnchant(power, level5, true);
        power5.setItemMeta(power5Meta);
        i.setItem(22, power5);

        ItemStack infinity1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta infinity1Meta = (EnchantmentStorageMeta) infinity1.getItemMeta();
        infinity1Meta.addStoredEnchant(infinity, level1, true);
        infinity1.setItemMeta(infinity1Meta);
        i.setItem(23, infinity1);

        ItemStack fireAspect2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta fireAspect2Meta = (EnchantmentStorageMeta) fireAspect2.getItemMeta();
        fireAspect2Meta.addStoredEnchant(fireAspect, level2, true);
        fireAspect2.setItemMeta(fireAspect2Meta);
        i.setItem(24, fireAspect2);

        ItemStack flame1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta flame1Meta = (EnchantmentStorageMeta) flame1.getItemMeta();
        flame1Meta.addStoredEnchant(flame, level1, true);
        flame1.setItemMeta(flame1Meta);
        i.setItem(25, flame1);

        ItemStack silkTouch1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta silkTouch1Meta = (EnchantmentStorageMeta) silkTouch1.getItemMeta();
        silkTouch1Meta.addStoredEnchant(silkTouch, level1, true);
        silkTouch1.setItemMeta(silkTouch1Meta);
        i.setItem(28, silkTouch1);

        ItemStack punch2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta punch2Meta = (EnchantmentStorageMeta) punch2.getItemMeta();
        punch2Meta.addStoredEnchant(punch, level2, true);
        punch2.setItemMeta(punch2Meta);
        i.setItem(29, punch2);

        ItemStack aquaInfinity1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta aquaInfinity1Meta = (EnchantmentStorageMeta) aquaInfinity1.getItemMeta();
        aquaInfinity1Meta.addStoredEnchant(aquaInfinity, level1, true);
        aquaInfinity1.setItemMeta(aquaInfinity1Meta);
        i.setItem(30, aquaInfinity1);

        // Flecha Voltar
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Voltar");
        arrowBack.setItemMeta(arrowBackMeta);
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

}
