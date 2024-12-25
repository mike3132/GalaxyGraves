package co.mcGalaxy.galaxyGraves.commands;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.ConsoleMessage;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.utils.Grave;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GravesCommand implements CommandExecutor {

    public GravesCommand() {
        GalaxyGraves.getInstance().getCommand("Graves").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            if (args.length == 0) {
                ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                return false;
            }
            if (!args[0].equalsIgnoreCase("Reload")) {
                ConsoleMessage.sendConsoleMessageWithoutConfig(sender, "&bGalaxy Graves written by &5Mike3132");
                return false;
            }
            ConsoleMessage.sendConsoleMessageWithoutConfig(sender, "g:<00ff2a:24b33b>Config Files Reloaded");
            ConfigManager.reloadAll();
            GalaxyGraves.getInstance().reloadConfig();
            return false;
        }


        PlayerMessage.sendPlayerMessageWithoutConfig(player, "<g:#00ff2a:#24b33b>Player Command Ran");
        if (args.length == 0) {
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "Default /graves command");
            return false;
        }

        if (args[0].equalsIgnoreCase("Spawn")) {
            Grave grave = new Grave(player, player.getLocation(), player.getName());
            GalaxyGraves.getInstance().graveManager.registerGrave(grave);
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&eSpawning shit");
            return false;
        }
        if (args[0].equalsIgnoreCase("Remove")) {
            Grave foundGrave = null;
            for (Grave graves : GalaxyGraves.getInstance().graveManager.getGraves().values()) {
                if (player.getLocation().distance(graves.getLocation()) < 10) {
                    foundGrave = graves;
                } else {
                    Bukkit.broadcastMessage("Can't find grave // Your not close enough");
                }
            }
            GalaxyGraves.getInstance().graveManager.unRegisterGrave(foundGrave);

            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&dRemoving shit");
            return false;
        }


        return false;
    }
}
