package co.mcGalaxy.galaxyGraves.commands;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.ConsoleMessage;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.configs.ConfigManager;
import co.mcGalaxy.galaxyGraves.grave.Grave;
import co.mcGalaxy.galaxyGraves.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GraveCommand implements CommandExecutor {

    private final GalaxyGraves plugin;
    private final LocationUtils locationUtils = new LocationUtils();

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
            PlayerMessage.sendLocationMessage(player, "Grave-Active", locationUtils.formatLocation(foundGrave.getLocation()));
            return false;
        }

        if (!player.hasPermission("GalaxyGraves.Command")) {
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&bGalaxy Graves written by &5Mike3132");
            PlayerMessage.sendPlayerMessageWithoutConfig(player, "&bYou don't have permission for any sub commands");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                if (!player.hasPermission("GalaxyGraves.Command.Admin.Reload")) {
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
                    if (!player.hasPermission("GalaxyGraves.Admin.ListActive")) {
                        PlayerMessage.sendMessage(player, "No-Permission");
                        return false;
                    }

                    Grave foundGrave = null;
                    for (Grave graves : plugin.graveManager.getGraves().values()) {
                        foundGrave = graves;
                        break;
                    }
                    if (foundGrave == null) {
                        PlayerMessage.sendMessage(player, "Admin-Target-No-Graves");
                        return false;
                    }
                    if (foundGrave.getUuid() == target.getUniqueId()) {
                        PlayerMessage.sendLocationMessage(player, "Grave-Active", locationUtils.formatLocation(foundGrave.getLocation()));
                        return false;
                    }
                    return false;
                }

                if (args[2].equalsIgnoreCase("ForceRemove")) {
                    if (!player.hasPermission("GalaxyGraves.Admin.ForceRemove")) {
                        PlayerMessage.sendMessage(player, "No-Permission");
                        return false;
                    }
                    Grave foundGrave = null;
                    for (Grave graves : plugin.graveManager.getGraves().values()) {
                        foundGrave = graves;
                        break;
                    }
                    if (foundGrave == null) {
                        PlayerMessage.sendMessage(player, "Admin-Target-No-Graves");
                        return false;
                    }
                    if (foundGrave.getUuid() == target.getUniqueId()) {
                        foundGrave.remove();
                        plugin.graveManager.remove(foundGrave);
                        PlayerMessage.sendMessageWithTarget(player, "Admin-Force-Remove-Grave", target.getName());
                    }
                    return false;
                }

                if (args[2].equalsIgnoreCase("Recover")) {
                    if (!player.hasPermission("GalaxyGraves.Admin.Recover")) {
                        PlayerMessage.sendMessage(player, "No-Permission");
                        return false;
                    }
                    if (args.length < 4) {
                        PlayerMessage.sendPlayerMessageWithoutConfig(player, "You need to pick an grave index");
                        return false;
                    }
                    int index;
                    try {
                        index = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        PlayerMessage.sendMessage(player, "Admin-Recover-String-In-Index");
                        return false;
                    }

                    plugin.graveManager.loadBackups(player, target.getUniqueId(), index);
                    return false;
                }

                break;
            default:
                PlayerMessage.sendMessage(player, "Not-Right-Args");
                break;
        }

        return false;
    }


}
