package voxel.wp.ienchantments.utils;

import org.bukkit.Material;

public class VerifyItems {
    // Funções auxiliares para verificar tipos de itens
    public boolean isArmor(Material itemType) {
        if (itemType == null) return false;
        String name = itemType.name();
        return name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") ||
                name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");
    }

    public boolean isSword(Material itemType) {
        return itemType != null && itemType.name().endsWith("_SWORD");
    }

    public boolean isPickaxe(Material itemType) {
        return itemType != null && itemType.name().endsWith("_PICKAXE");
    }

    public boolean isAxe(Material itemType) {
        return itemType != null && itemType.name().endsWith("_AXE");
    }

    public boolean isHelmet(Material itemType) {
        return itemType != null && itemType.name().endsWith("_HELMET");
    }

    public boolean isBow(Material itemType) {
        return itemType == Material.BOW || itemType == Material.CROSSBOW;
    }

    public boolean isTool(Material itemType) {
        if (itemType == null) return false;
        String name = itemType.name();
        return name.endsWith("_PICKAXE") || name.endsWith("_SHOVEL") ||
                name.endsWith("_AXE") || name.endsWith("_HOE") ||
                itemType == Material.SHEARS || itemType == Material.FLINT_AND_STEEL;
    }

    public boolean isHoe(Material itemType) {
        return itemType != null && itemType.name().endsWith("_HOE");
    }

    public boolean isBoots(Material itemType) {
        return itemType != null && itemType.name().endsWith("_BOOTS");
    }
}