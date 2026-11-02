package voxel.wp.ienchantments.events;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentManager {

    private Map<Enchantment, Map<Integer, String>> enchantmentLevels = new HashMap<>();

    void initializeEnchantmentLevels() {
        Map<Integer, String> oxygenLevels = new HashMap<>();
        oxygenLevels.put(3, "advanced");
        oxygenLevels.put(2, "normal");
        oxygenLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.OXYGEN, oxygenLevels);

        Map<Integer, String> flameLevels = new HashMap<>();
        flameLevels.put(1, "advanced");
        enchantmentLevels.put(Enchantment.ARROW_FIRE, flameLevels);

        Map<Integer, String> infinityLevels = new HashMap<>();
        infinityLevels.put(1, "advanced");
        enchantmentLevels.put(Enchantment.ARROW_INFINITE, infinityLevels);

        Map<Integer, String> silkTouchLevels = new HashMap<>();
        silkTouchLevels.put(1, "advanced");
        enchantmentLevels.put(Enchantment.SILK_TOUCH, silkTouchLevels);

        Map<Integer, String> punchLevels = new HashMap<>();
        punchLevels.put(2, "advanced");
        punchLevels.put(1, "normal");
        enchantmentLevels.put(Enchantment.ARROW_KNOCKBACK, punchLevels);

        Map<Integer, String> powerLevels = new HashMap<>();
        powerLevels.put(5, "advanced");
        powerLevels.put(4, "normal");
        powerLevels.put(3, "normal");
        powerLevels.put(2, "simple");
        powerLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.ARROW_DAMAGE, powerLevels);

        Map<Integer, String> aquaInfinityLevels = new HashMap<>();
        aquaInfinityLevels.put(1, "advanced");
        enchantmentLevels.put(Enchantment.WATER_WORKER, aquaInfinityLevels);

        Map<Integer, String> depthStriderLevels = new HashMap<>();
        depthStriderLevels.put(3, "advanced");
        depthStriderLevels.put(2, "normal");
        depthStriderLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.DEPTH_STRIDER, depthStriderLevels);

        Map<Integer, String> featherFallingLevels = new HashMap<>();
        featherFallingLevels.put(4, "advanced");
        featherFallingLevels.put(3, "normal");
        featherFallingLevels.put(2, "simple");
        featherFallingLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.PROTECTION_FALL, featherFallingLevels);

        Map<Integer, String> fireAspectLevels = new HashMap<>();
        fireAspectLevels.put(2, "advanced");
        fireAspectLevels.put(1, "normal");
        enchantmentLevels.put(Enchantment.FIRE_ASPECT, fireAspectLevels);

        Map<Integer, String> lootingLevels = new HashMap<>();
        lootingLevels.put(3, "advanced");
        lootingLevels.put(2, "normal");
        lootingLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.LOOT_BONUS_MOBS, lootingLevels);

        Map<Integer, String> fortuneLevels = new HashMap<>();
        fortuneLevels.put(3, "advanced");
        fortuneLevels.put(2, "normal");
        fortuneLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.LOOT_BONUS_BLOCKS, fortuneLevels);

        Map<Integer, String> protectionLevels = new HashMap<>();
        protectionLevels.put(5, "advanced");
        protectionLevels.put(4, "normal");
        protectionLevels.put(3, "normal");
        protectionLevels.put(2, "simple");
        protectionLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.PROTECTION_ENVIRONMENTAL, protectionLevels);

        Map<Integer, String> unbreakingLevels = new HashMap<>();
        unbreakingLevels.put(5, "advanced");
        unbreakingLevels.put(4, "normal");
        unbreakingLevels.put(3, "normal");
        unbreakingLevels.put(2, "simple");
        unbreakingLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.DURABILITY, unbreakingLevels);

        Map<Integer, String> sharpenessLevels = new HashMap<>();
        sharpenessLevels.put(5, "advanced");
        sharpenessLevels.put(4, "normal");
        sharpenessLevels.put(3, "normal");
        sharpenessLevels.put(2, "simple");
        sharpenessLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.DAMAGE_ALL, sharpenessLevels);

        Map<Integer, String> efficiencyLevels = new HashMap<>();
        efficiencyLevels.put(5, "advanced");
        efficiencyLevels.put(4, "normal");
        efficiencyLevels.put(3, "normal");
        efficiencyLevels.put(2, "simple");
        efficiencyLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.DIG_SPEED, efficiencyLevels);

        Map<Integer, String> protectionFireLevels = new HashMap<>();
        protectionFireLevels.put(4, "advanced");
        protectionFireLevels.put(3, "normal");
        protectionFireLevels.put(2, "simple");
        protectionFireLevels.put(1, "simple");
        enchantmentLevels.put(Enchantment.PROTECTION_FIRE, protectionFireLevels);
    }

    public String getCategory(Enchantment enchantment, int level) {
        Map<Integer, String> levelMap = enchantmentLevels.get(enchantment);

        if(levelMap != null && levelMap.containsKey(level)) {
            return levelMap.get(level);
        }

        return null;
    }

}
