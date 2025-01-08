package co.mcGalaxy.galaxyGraves.utils;

import org.bukkit.Location;

public class LocationUtils {

    public String formatLocation(Location location) {
        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return worldName + " " + x + " " + y + " " + z;
    }



}
