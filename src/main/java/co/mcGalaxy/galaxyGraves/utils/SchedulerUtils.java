package co.mcGalaxy.galaxyGraves.utils;

import co.mcGalaxy.galaxyGraves.GalaxyGraves;
import co.mcGalaxy.galaxyGraves.chat.PlayerMessage;
import co.mcGalaxy.galaxyGraves.grave.Grave;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SchedulerUtils {

    private final BukkitTask task;
    private final int graveLifetime = GalaxyGraves.getInstance().getConfig().getInt("Grave-Lifetime");
    private final boolean sendMessage = GalaxyGraves.getInstance().getConfig().getBoolean("Player-Grave-Expire-Message");

    public SchedulerUtils(GalaxyGraves plugin) {
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, this::graveTask, 0, 20);
    }

    private void graveTask() {

        for (Grave graves : GalaxyGraves.getInstance().graveManager.getGraves().values()) {
            handleGrave(graves);
            break;
        }
    }

    public void disable() {
        this.task.cancel();
    }

    private void handleGrave(Grave grave) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (!currentTime.isAfter(grave.getLocalDateTime())) return;

        if (ChronoUnit.MINUTES.between(grave.getLocalDateTime(), currentTime) >= graveLifetime) {

            if (sendMessage) {
                Player player = Bukkit.getPlayer(grave.getUuid());
                if (player == null) return;
                PlayerMessage.sendMessage(player, "Grave-Expire-Message");
            }

            grave = null;
            for (Grave graves : GalaxyGraves.getInstance().graveManager.getGraves().values()) {
                grave = graves;
            }
            if (grave == null) return;
            grave.remove();
            GalaxyGraves.getInstance().graveManager.remove(grave);
        }
    }

}
