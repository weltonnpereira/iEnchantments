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

public class SimpleEnchantsGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void SimpleEnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_PURPLE + "Encantamentos - Livro Simples");

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
        ItemStack p1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p1meta = (EnchantmentStorageMeta) p1.getItemMeta();
        p1meta.addStoredEnchant(protection, level1, true);
        p1.setItemMeta(p1meta);
        i.setItem(10, p1);

        ItemStack p2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p2meta = (EnchantmentStorageMeta) p2.getItemMeta();
        p2meta.addStoredEnchant(protection, level2, true);
        p2.setItemMeta(p2meta);
        i.setItem(11, p2);

        ItemStack pFire1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta pFire1Meta = (EnchantmentStorageMeta) pFire1.getItemMeta();
        pFire1Meta.addStoredEnchant(protectionFire, level1, true);
        pFire1.setItemMeta(pFire1Meta);
        i.setItem(12, pFire1);

        ItemStack pFire2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta pFire2Meta = (EnchantmentStorageMeta) pFire2.getItemMeta();
        pFire2Meta.addStoredEnchant(protectionFire, level2, true);
        pFire2.setItemMeta(pFire2Meta);
        i.setItem(13, pFire2);

        ItemStack respiration1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta respiration1Meta = (EnchantmentStorageMeta) respiration1.getItemMeta();
        respiration1Meta.addStoredEnchant(respiration, level1, true);
        respiration1.setItemMeta(respiration1Meta);
        i.setItem(14, respiration1);

        ItemStack featherFalling1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta featherFalling1Meta = (EnchantmentStorageMeta) featherFalling1.getItemMeta();
        featherFalling1Meta.addStoredEnchant(featherFalling, level1, true);
        featherFalling1.setItemMeta(featherFalling1Meta);
        i.setItem(15, featherFalling1);

        ItemStack featherFalling2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta featherFalling2Meta = (EnchantmentStorageMeta) featherFalling2.getItemMeta();
        featherFalling2Meta.addStoredEnchant(featherFalling, level2, true);
        featherFalling2.setItemMeta(featherFalling2Meta);
        i.setItem(16, featherFalling2);

        ItemStack deathStrider1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta deathStrider1Meta = (EnchantmentStorageMeta) deathStrider1.getItemMeta();
        deathStrider1Meta.addStoredEnchant(deathStrider, level1, true);
        deathStrider1.setItemMeta(deathStrider1Meta);
        i.setItem(19, deathStrider1);

        ItemStack s1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta s1Meta = (EnchantmentStorageMeta) s1.getItemMeta();
        s1Meta.addStoredEnchant(sharpness, level1, true);
        s1.setItemMeta(s1Meta);
        i.setItem(20, s1);

        ItemStack s2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta s2Meta = (EnchantmentStorageMeta) s2.getItemMeta();
        s2Meta.addStoredEnchant(sharpness, level2, true);
        s2.setItemMeta(s2Meta);
        i.setItem(21, s2);

        ItemStack looting1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta looting1Meta = (EnchantmentStorageMeta) looting1.getItemMeta();
        looting1Meta.addStoredEnchant(looting, level1, true);
        looting1.setItemMeta(looting1Meta);
        i.setItem(22, looting1);

        ItemStack efficiency1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta efficiency1Meta = (EnchantmentStorageMeta) efficiency1.getItemMeta();
        efficiency1Meta.addStoredEnchant(efficiency, level1, true);
        efficiency1.setItemMeta(efficiency1Meta);
        i.setItem(23, efficiency1);

        ItemStack efficiency2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta efficiency2Meta = (EnchantmentStorageMeta) efficiency2.getItemMeta();
        efficiency2Meta.addStoredEnchant(efficiency, level2, true);
        efficiency2.setItemMeta(efficiency2Meta);
        i.setItem(24, efficiency2);

        ItemStack unbreaking1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta unbreaking1Meta = (EnchantmentStorageMeta) unbreaking1.getItemMeta();
        unbreaking1Meta.addStoredEnchant(unbreaking, level1, true);
        unbreaking1.setItemMeta(unbreaking1Meta);
        i.setItem(25, unbreaking1);

        ItemStack unbreaking2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta unbreaking2Meta = (EnchantmentStorageMeta) unbreaking2.getItemMeta();
        unbreaking2Meta.addStoredEnchant(unbreaking, level2, true);
        unbreaking2.setItemMeta(unbreaking2Meta);
        i.setItem(28, unbreaking2);

        ItemStack fortune1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta fortune1Meta = (EnchantmentStorageMeta) fortune1.getItemMeta();
        fortune1Meta.addStoredEnchant(fortune, level1, true);
        fortune1.setItemMeta(fortune1Meta);
        i.setItem(29, fortune1);

        ItemStack power1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta power1Meta = (EnchantmentStorageMeta) power1.getItemMeta();
        power1Meta.addStoredEnchant(power, level1, true);
        power1.setItemMeta(power1Meta);
        i.setItem(30, power1);

        ItemStack power2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta power2Meta = (EnchantmentStorageMeta) power2.getItemMeta();
        power2Meta.addStoredEnchant(power, level2, true);
        power2.setItemMeta(power2Meta);
        i.setItem(31, power2);

        // Flecha Voltar
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Voltar");
        arrowBack.setItemMeta(arrowBackMeta);
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

}
