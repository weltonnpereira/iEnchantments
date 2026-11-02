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

public class NormalEnchantsGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void NormalEnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_PURPLE + "Encantamentos - Livro Normal");

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

        ItemStack p3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p3meta = (EnchantmentStorageMeta) p3.getItemMeta();
        p3meta.addStoredEnchant(protection, level3, true);
        p3.setItemMeta(p3meta);
        i.setItem(10, p3);

        ItemStack p4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p4meta = (EnchantmentStorageMeta) p4.getItemMeta();
        p4meta.addStoredEnchant(protection, level4, true);
        p4.setItemMeta(p4meta);
        i.setItem(11, p4);

        ItemStack pFire3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta pFire3Meta = (EnchantmentStorageMeta) pFire3.getItemMeta();
        pFire3Meta.addStoredEnchant(protectionFire, level3, true);
        pFire3.setItemMeta(pFire3Meta);
        i.setItem(12, pFire3);

        ItemStack featherFalling3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta featherFalling3Meta = (EnchantmentStorageMeta) featherFalling3.getItemMeta();
        featherFalling3Meta.addStoredEnchant(featherFalling, level3, true);
        featherFalling3.setItemMeta(featherFalling3Meta);
        i.setItem(13, featherFalling3);

        ItemStack deathStrider2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta deathStrider2Meta = (EnchantmentStorageMeta) deathStrider2.getItemMeta();
        deathStrider2Meta.addStoredEnchant(deathStrider, level2, true);
        deathStrider2.setItemMeta(deathStrider2Meta);
        i.setItem(14, deathStrider2);

        ItemStack s3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta s3Meta = (EnchantmentStorageMeta) s3.getItemMeta();
        s3Meta.addStoredEnchant(sharpness, level3, true);
        s3.setItemMeta(s3Meta);
        i.setItem(15, s3);

        ItemStack s4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta s4Meta = (EnchantmentStorageMeta) s4.getItemMeta();
        s4Meta.addStoredEnchant(sharpness, level4, true);
        s4.setItemMeta(s4Meta);
        i.setItem(16, s4);

        ItemStack looting2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta looting2Meta = (EnchantmentStorageMeta) looting2.getItemMeta();
        looting2Meta.addStoredEnchant(looting, level2, true);
        looting2.setItemMeta(looting2Meta);
        i.setItem(19, looting2);

        ItemStack efficiency3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta efficiency3Meta = (EnchantmentStorageMeta) efficiency3.getItemMeta();
        efficiency3Meta.addStoredEnchant(efficiency, level3, true);
        efficiency3.setItemMeta(efficiency3Meta);
        i.setItem(20, efficiency3);

        ItemStack efficiency4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta efficiency4Meta = (EnchantmentStorageMeta) efficiency4.getItemMeta();
        efficiency4Meta.addStoredEnchant(efficiency, level4, true);
        efficiency4.setItemMeta(efficiency4Meta);
        i.setItem(21, efficiency4);

        ItemStack unbreaking3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta unbreaking3Meta = (EnchantmentStorageMeta) unbreaking3.getItemMeta();
        unbreaking3Meta.addStoredEnchant(unbreaking, level3, true);
        unbreaking3.setItemMeta(unbreaking3Meta);
        i.setItem(22, unbreaking3);

        ItemStack unbreaking4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta unbreaking4Meta = (EnchantmentStorageMeta) unbreaking4.getItemMeta();
        unbreaking4Meta.addStoredEnchant(unbreaking, level4, true);
        unbreaking4.setItemMeta(unbreaking4Meta);
        i.setItem(23, unbreaking4);

        ItemStack fortune2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta fortune2Meta = (EnchantmentStorageMeta) fortune2.getItemMeta();
        fortune2Meta.addStoredEnchant(fortune, level2, true);
        fortune2.setItemMeta(fortune2Meta);
        i.setItem(24, fortune2);

        ItemStack power3 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta power3Meta = (EnchantmentStorageMeta) power3.getItemMeta();
        power3Meta.addStoredEnchant(power, level3, true);
        power3.setItemMeta(power3Meta);
        i.setItem(25, power3);

        ItemStack power4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta power4Meta = (EnchantmentStorageMeta) power4.getItemMeta();
        power4Meta.addStoredEnchant(power, level4, true);
        power4.setItemMeta(power4Meta);
        i.setItem(28, power4);

        ItemStack fireAspect1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta fireAspect1Meta = (EnchantmentStorageMeta) fireAspect1.getItemMeta();
        fireAspect1Meta.addStoredEnchant(fireAspect, level1, true);
        fireAspect1.setItemMeta(fireAspect1Meta);
        i.setItem(29, fireAspect1);

        ItemStack punch1 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta punch1Meta = (EnchantmentStorageMeta) punch1.getItemMeta();
        punch1Meta.addStoredEnchant(punch, level1, true);
        punch1.setItemMeta(punch1Meta);
        i.setItem(30, punch1);

        ItemStack respiration2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta respiration2Meta = (EnchantmentStorageMeta) respiration2.getItemMeta();
        respiration2Meta.addStoredEnchant(respiration, level2, true);
        respiration2.setItemMeta(respiration2Meta);
        i.setItem(31, respiration2);

        // Flecha Voltar
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Voltar");
        arrowBack.setItemMeta(arrowBackMeta);
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

}
