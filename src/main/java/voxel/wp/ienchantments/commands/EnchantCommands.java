package voxel.wp.ienchantments.commands;

import voxel.wp.ienchantments.Main;
import voxel.wp.ienchantments.listeners.EnchantGUI;
import voxel.wp.ienchantments.listeners.ExtractionRune;
import voxel.wp.ienchantments.listeners.RecyclerGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EnchantCommands implements Listener, CommandExecutor {

    public String cmd1 = "encantar";
    public String cmd2 = "reciclar";

    public String cmd3 = "runa";

    public String cmd4 = "iencantar";

    public String cmd5 = "reload";

    private Plugin plugin = Main.getPlugin(Main.class);
    private PluginManager pluginManager = Bukkit.getPluginManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase(cmd1)) {
                if(player.hasPermission("encantar.use")) {
                    EnchantGUI i = new EnchantGUI();

                    i.EnchantInventory(player);
                    return true;
                }
            }

            if(cmd.getName().equals(cmd2)) {
                if(player.hasPermission("reciclar.use")) {
                    RecyclerGUI i = new RecyclerGUI();

                    i.RecyclerInventory(player);
                    return true;
                }
            }

            if(cmd.getName().equals(cmd3)) {
                if(player.isOp() || player.hasPermission("rune.admin")) {
                    ExtractionRune extractionRune = new ExtractionRune();

                    extractionRune.GiveExtractionRune(player);
                    return true;
                }
            }

            if(cmd.getName().equalsIgnoreCase(cmd4)) {
                if(args.length >= 1 && args[0].equalsIgnoreCase(cmd5)) {
                    if(player.isOp() || player.hasPermission("iencantar.admin")) {
                        plugin.reloadConfig();
                        pluginManager.disablePlugin(plugin);
                        pluginManager.enablePlugin(plugin);
                        player.sendMessage(ChatColor.DARK_PURPLE + "iEncantamentos " + ChatColor.GREEN + "foi recarregado.");
                        return true;
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Somente jogadores podem executar este comando!");
        }
        return false;
    }


}
