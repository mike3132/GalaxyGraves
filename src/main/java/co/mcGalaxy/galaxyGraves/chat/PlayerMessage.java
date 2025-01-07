package co.mcGalaxy.galaxyGraves.chat;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.utils.HexUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerMessage {

    public static void sendPlayerMessageWithoutConfig(Player player, String key) {
        String message = HexUtils.colorify("#ba63f7[<g:#0099ff:#EEEEEE>Galaxy Graves#ba63f7] #121212> &r" + key);
        player.sendMessage(message);
    }

    public static void sendMessage(Player player, String key) {
        File messagesConfig = new File(GalaxyGraves.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(HexUtils.colorify(message));
    }

    public static void senLocationMessage(Player player, String key, String location) {
        File messagesConfig = new File(GalaxyGraves.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key) + location;
        player.sendMessage(HexUtils.colorify(message));
    }
}
