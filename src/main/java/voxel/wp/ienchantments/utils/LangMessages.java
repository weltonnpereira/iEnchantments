package voxel.wp.ienchantments.utils;

import voxel.wp.ienchantments.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LangMessages {

    private final Main plugin;
    private FileConfiguration messagesConfig;

    public LangMessages(Main plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    public void loadMessages() {
        String lang = plugin.getConfig().getString("lang", "en");
        String fileName = "lang/" + lang + ".yml";

        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(file);

        InputStream defaultStream = plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultStream, StandardCharsets.UTF_8)
            );
            messagesConfig.setDefaults(defaultConfig);
        }
    }

    public String getMessage(String path) {
        String prefix = messagesConfig.getString("prefix", "");
        String message = messagesConfig.getString("messages." + path, "Message missing: " + path);

        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

    public String getRawMessage(String path) {
        String message = messagesConfig.getString("messages." + path, "Message missing: " + path);

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getLogMessage(String path) {
        return messagesConfig.getString("logs." + path, "Log message missing: " + path);
    }

}
