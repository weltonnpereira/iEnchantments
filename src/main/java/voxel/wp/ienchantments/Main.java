package voxel.wp.ienchantments;

import voxel.wp.ienchantments.commands.EnchantCommands;
import network.insane.iencantamentos.events.*;
import org.bukkit.plugin.java.JavaPlugin;
import voxel.wp.ienchantments.events.*;

public class Main extends JavaPlugin {

    private EnchantmentManager enchantmentManager;

    @Override
    public void onEnable() {
        getLogger().info("iEncantamentos foi ligado!");

        EnchantCommands commands = new EnchantCommands();
        getCommand(commands.cmd1).setExecutor(commands);
        getCommand(commands.cmd2).setExecutor(commands);
        getCommand(commands.cmd3).setExecutor(commands);
        getCommand(commands.cmd4).setExecutor(commands);
        getCommand(commands.cmd5).setExecutor(commands);

        enchantmentManager = new EnchantmentManager();
        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new SimpleInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new NormalInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new AdvancedInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new RecyclerInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new RuneExtractionEvent(enchantmentManager), this);
        loadConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("iEncantamentos foi desligado!");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}
