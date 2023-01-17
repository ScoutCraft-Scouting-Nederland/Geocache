package nl.scoutcraft.geocache.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * It converts a location to a string and vice versa
 */
public class LocationUtils {

    public static Location fromString(String string) {
        String[] p = string.split(",");
        return new Location(Bukkit.getWorld(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]));
    }

    public static String toString(Location location) {
        return location.getWorld().getName()+ "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }
}
