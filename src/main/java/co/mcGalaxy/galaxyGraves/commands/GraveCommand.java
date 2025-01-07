package co.mcGalaxy.galaxyGraves.commands;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.ConsoleMessage;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.grave.Grave;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GraveCommand implements CommandExecutor {

    private final GalaxyGraves plugin;

    public GraveCommand(GalaxyGraves plugin) {
        this.plugin = plugin;
        plugin.getCommand("Graves").setExecutor(this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            ConsoleMessage.sendMessage(sender, "Player-Only");
            return false;
        }

        if (args.length == 0) {
            if (!player.hasPermission("GalaxyGraves.Command.Grave")) {
                PlayerMessage.sendMessage(player, "No-Permission");
                return false;
            }
            Grave foundGrave = null;
            for (Grave graves : plugin.graveManager.getGraves().values()) {
                if (graves.getUuid() == player.getUniqueId()) {
                    foundGrave = graves;
                    break;
                }
            }
            if (foundGrave == null) {
                PlayerMessage.sendMessage(player, "No-Active-Grave");
                return false;
            }
            String worldName = foundGrave.getLocation().getWorld().getName();
            int x = foundGrave.getLocation().getBlockX();
            int y = foundGrave.getLocation().getBlockY();
            int z = foundGrave.getLocation().getBlockZ();
            String message = worldName + " " + x + " " + y + " " + z;

            PlayerMessage.senLocationMessage(player, "Grave-Active", message);
            return false;
        }

        if (!player.hasPermission("GalaxyGraves.Command")) {
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&bGalaxy Graves written by &5Mike3132");
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&bYou don't have permission for any sub commands");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                if (!player.hasPermission("GalaxyGraves.Command.Reload")) {
                    PlayerMessage.sendMessage(player, "No-Permission");
                    return false;
                }
                ConfigManager.reloadAll();
                plugin.reloadConfig();
                PlayerMessage.sendPlayerMessageWithoutConfig(player, "&2Config reloaded");
                break;
            case "admin":
                if (!player.hasPermission("GalaxyGraves.Command.Admin")) {
                    PlayerMessage.sendMessage(player, "No-Permission");
                    return false;
                }
                if (args.length == 1) {
                    PlayerMessage.sendMessage(player, "Target-Player-Not-Supplied");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    PlayerMessage.sendMessage(player, "Target-Player-Not-Found");
                    return false;
                }
                if (args.length == 2) {
                    PlayerMessage.sendMessage(player, "Not-Enough-Args");
                    return false;
                }
                if (args[2].equalsIgnoreCase("ListActive")) {
                    Grave foundGrave = null;
                    for (Grave graves : plugin.graveManager.getGraves().values()) {
                        foundGrave = graves;
                        break;
                    }
                    if (foundGrave == null) {
                        PlayerMessage.sendPlayerMessageWithoutConfig(player, "&4ERROR: &cThis player doesn't have active graves");
                        return false;
                    }
                    if (foundGrave.getUuid() == target.getUniqueId()) {
                        PlayerMessage.sendPlayerMessageWithoutConfig(player, foundGrave.getLocation().toString());
                    }
                    return false;
                }

                if (args[2].equalsIgnoreCase("ForceRemove")) {
                    Grave foundGrave = null;
                    for (Grave graves : plugin.graveManager.getGraves().values()) {
                        foundGrave = graves;
                        break;
                    }
                    if (foundGrave == null) {
                        PlayerMessage.sendPlayerMessageWithoutConfig(player, "&4ERROR: &cThis player doesn't have active graves");
                        return false;
                    }
                    if (foundGrave.getUuid() == target.getUniqueId()) {
                        foundGrave.remove();
                        plugin.graveManager.remove(foundGrave);
                        PlayerMessage.sendPlayerMessageWithoutConfig(player, "&2Successfully ForceRemoved &b" + target.getName() + "'s &3Grave");
                    }
                }

                break;
            //TODO: Remove these development commands
            case "spawn":
                Grave grave = new Grave(player, player.getUniqueId());
                grave.create();
                plugin.graveManager.add(grave);
                plugin.graveManager.saveGrave(player.getUniqueId(), grave);
                PlayerMessage.sendPlayerMessageWithoutConfig(player, "&2Grave created");
                break;
            default:

                PlayerMessage.sendMessage(player, "Not-Right-Args");
                break;
        }

        return false;
    }


}
