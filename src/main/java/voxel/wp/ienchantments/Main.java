package voxel.wp.ienchantments;

import voxel.wp.ienchantments.commands.EnchantCommands;
import voxel.wp.ienchantments.listeners.InventoryEvent;
import voxel.wp.ienchantments.listeners.PlayerInteractionEvent;
import voxel.wp.ienchantments.manager.PlManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PlManager plManager;

    @Override
    public void onEnable() {
        getLogger().info("iEnchantments is on!");

        this.plManager = new PlManager(this);

        EnchantCommands commands = new EnchantCommands(plManager);
        getCommand(commands.cmd1).setExecutor(commands);
        getCommand(commands.cmd2).setExecutor(commands);
        getCommand(commands.cmd3).setExecutor(commands);
        getCommand(commands.cmd4).setExecutor(commands);
        getCommand(commands.cmd5).setExecutor(commands);

        getServer().getPluginManager().registerEvents(new InventoryEvent(plManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionEvent(plManager), this);

        loadConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("iEnchantments is off!");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
