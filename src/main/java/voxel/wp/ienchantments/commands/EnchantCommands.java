package voxel.wp.ienchantments.commands;

import voxel.wp.ienchantments.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import voxel.wp.ienchantments.manager.PlManager;

public class EnchantCommands implements Listener, CommandExecutor {

    private final PlManager plManager;

    public String cmd1 = "enchant";
    public String cmd2 = "recycle";

    public String cmd3 = "rune";

    public String cmd4 = "ienchant";

    public String cmd5 = "reload";

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    public EnchantCommands(PlManager plManager) {
        this.plManager = plManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase(cmd1)) {
                if(player.hasPermission("enchant.use")) {
                    plManager.getMainGUI().openEnchantInventory(player);
                    return true;
                }
            }

            if(cmd.getName().equals(cmd2)) {
                if(player.hasPermission("recycle.use")) {
                    plManager.getRecyclerGUI().openRecyclerInventory(player);
                    return true;
                }
            }

            if(cmd.getName().equals(cmd3)) {
                if(player.isOp() || player.hasPermission("rune.admin")) {
                    plManager.getRuneManager().giveExtractionRune(player);
                    return true;
                }
            }

            if(cmd.getName().equalsIgnoreCase(cmd4)) {
                if(args.length >= 1 && args[0].equalsIgnoreCase(cmd5)) {
                    if(player.isOp() || player.hasPermission("ienchant.admin")) {
                        Main plugin = plManager.getPlugin();
                        plugin.reloadConfig();
                        pluginManager.disablePlugin(plugin);
                        pluginManager.enablePlugin(plugin);
                        player.sendMessage(ChatColor.DARK_PURPLE + "iEnchantments " + ChatColor.GREEN + "was reloaded.");
                        return true;
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
        }
        return false;
    }


}
