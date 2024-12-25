package co.mcGalaxy.galaxyGraves.chat;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConsoleMessage {

    public static void sendConsoleMessageWithoutConfig(CommandSender sender, String key) {
        String message = HexUtils.colorify("&f[<g:#03fcfc:#1703fc>Galaxy Graves&f] &7> " + key);
        sender.sendMessage(message);
    }

    public static void sendMessage(CommandSender sender, String key) {
        File messagesConfig = new File(GalaxyGraves.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        sender.sendMessage(HexUtils.colorify(message));
    }
}
