package co.mcGalaxy.galaxyGraves.commands;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.ConsoleMessage;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.objects.Grave;
import co.mcGalaxy.galaxyGraves.objects.Model;
import co.mcGalaxy.galaxyGraves.objects.Npc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GravesCommand implements CommandExecutor {

    public GravesCommand() {
        GalaxyGraves.getInstance().getCommand("Graves").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //TODO: Make permissions and have permission checks for all commands.
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
            Grave grave = new Grave(player, player.getUniqueId());
            grave.create();
            GalaxyGraves.getInstance().graveManager.add(grave);
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&eSpawning shit");
            return false;
        }

        //TODO: Figure out a better way to write this and not have nested for loops #UGLY
        if (args[0].equalsIgnoreCase("Remove")) {
            Grave foundGrave = null;
            for (Grave graves : GalaxyGraves.getInstance().graveManager.getGraves().values()) {
                if (player.getLocation().distance(graves.getLocation()) < 10) {
                    foundGrave = graves;
                    break;
                }
            }
            if (foundGrave != null) {
                GalaxyGraves.getInstance().graveManager.remove(foundGrave);
                foundGrave.remove();
                PlayerMessage.sendPlayerMessageWithoutConfig(player, "&dRemoving shit");
                return false;
            }
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&dNo graves nearby to remove");
            return false;
        }

        if (args[0].equalsIgnoreCase("List")) {
            player.sendMessage(GalaxyGraves.getInstance().graveManager.getGraves().toString());
            return false;
        }


        return false;
    }
}
