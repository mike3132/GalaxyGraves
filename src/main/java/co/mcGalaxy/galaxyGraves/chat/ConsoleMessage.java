package co.mcGalaxy.galaxyGraves.chat;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.utils.HexUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConsoleMessage {

    public static void sendMessageWithoutConfig(CommandSender sender, String key) {
        String message = HexUtils.colorify("#ba63f7[<g:#0099ff:#EEEEEE>Galaxy Graves#ba63f7] #121212> &r" + key);
        sender.sendMessage(message);
    }

    public static void sendMessage(CommandSender sender, String key) {
        File messagesConfig = new File(GalaxyGraves.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        sender.sendMessage(HexUtils.colorify(message));
    }
}
