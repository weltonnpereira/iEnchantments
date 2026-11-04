package voxel.wp.ienchantments.utils;

import org.bukkit.enchantments.Enchantment;
import voxel.wp.ienchantments.manager.PlManager;

import java.util.Map;

public class VerifyEnchantments {

    // Funções auxiliares para verificar encantamentos
    public boolean hasProtection(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL);
    }

    public boolean hasUnbreaking(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DURABILITY);
    }

    public boolean hasRespiration(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.OXYGEN);
    }

    public boolean hasFeatherFalling(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.PROTECTION_FALL);
    }

    public boolean hasAquaInfinity(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.WATER_WORKER);
    }

    public boolean hasSharpness(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DAMAGE_ALL);
    }

    public boolean hasFireAspect(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.FIRE_ASPECT);
    }

    public boolean hasFlame(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_FIRE);
    }

    public boolean hasPower(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_DAMAGE);
    }

    public boolean hasInfinity(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_INFINITE);
    }

    public boolean hasPunch(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.ARROW_KNOCKBACK);
    }

    public boolean hasEfficiency(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DIG_SPEED);
    }

    public boolean hasSilkTouch(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.SILK_TOUCH);
    }

    public boolean hasFortune(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS);
    }

    public boolean hasDepthStrider(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.DEPTH_STRIDER);
    }

    public boolean hasLooting(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.LOOT_BONUS_MOBS);
    }

    public boolean hasFireProtection(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(Enchantment.PROTECTION_FIRE);
    }

}
