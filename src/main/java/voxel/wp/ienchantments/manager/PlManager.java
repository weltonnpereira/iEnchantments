package voxel.wp.ienchantments.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import voxel.wp.ienchantments.Main;
import voxel.wp.ienchantments.gui.MainGUI;
import voxel.wp.ienchantments.gui.RecyclerGUI;
import voxel.wp.ienchantments.utils.VerifyEnchantments;
import voxel.wp.ienchantments.utils.VerifyItems;

public class PlManager {

    private final Main plugin;

    private final FlaskBookManager flaskBookManager;

    private final MainGUI mainGUI;

    private final RecyclerGUI recyclerGUI;

    private final EnchantmentManager enchantmentManager;

    private final RuneManager runeManager;

    private final RecyclerManager recyclerManager;

    private final VerifyEnchantments verifyEnchantments;

    private final VerifyItems verifyItems;

    public PlManager(Main plugin) {
        this.plugin = plugin;
        this.flaskBookManager = new FlaskBookManager(this);
        this.mainGUI = new MainGUI(this);
        this.recyclerGUI = new RecyclerGUI(this);
        this.enchantmentManager = new EnchantmentManager(this);
        this.runeManager = new RuneManager(this);
        this.recyclerManager = new RecyclerManager(this);
        this.verifyEnchantments = new VerifyEnchantments();
        this.verifyItems = new VerifyItems();
    }

    public Main getPlugin() {
        return plugin;
    }

    public FlaskBookManager getFlaskBookManager() {
        return flaskBookManager;
    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public RecyclerGUI getRecyclerGUI() {
        return recyclerGUI;
    }

    public EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }

    public RuneManager getRuneManager() {
        return runeManager;
    }

    public RecyclerManager getRecyclerManager() {
        return recyclerManager;
    }

    public VerifyEnchantments getVerifyEnchantments() {
        return verifyEnchantments;
    }

    public VerifyItems getVerifyItems() {
        return verifyItems;
    }

    // Auxiliaries

    public void consumeItemInHand(Player player, ItemStack item) {
        int amountInHand = item.getAmount();
        if (amountInHand == 1) {
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        } else {
            item.setAmount(amountInHand - 1);
        }
    }

    public boolean hasInventorySpaceOrStack(Player player, ItemStack item) {
        // Se houver espaço livre ou se for menor que 64 (pode empilhar)
        return player.getInventory().firstEmpty() != -1 || item.getAmount() < 64;
    }

    public String formatExp(int xpAmount) {
        return String.format("%,d", xpAmount);
    }

}
