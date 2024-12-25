package co.mcGalaxy.galaxyGraves.chat;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerMessage {

    public static void sendPlayerMessageWithoutConfig(Player player, String key) {
        String message = HexUtils.colorify("&f[<g:#03fcfc:#1703fc>Galaxy Graves&f] &7> " + key);
        player.sendMessage(message);
    }

    public static void sendMessage(Player player, String key) {
        File messagesConfig = new File(GalaxyGraves.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(HexUtils.colorify(message));
    }
}
