package nl.scoutcraft.geocache.utils;

import nl.scoutcraft.geocache.Geocache;
import nl.scoutcraft.geocache.locale.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * It's a list of caches that can be added to, removed from, and used to get a random cache for a
 * player
 */
public class CacheList {

    private static final Random R = new Random();

    private final Geocache plugin;
    private final List<String> cacheStrings;
    private final List<Location> caches;

    public CacheList(Geocache plugin) {
        this.plugin = plugin;
        this.cacheStrings = plugin.getConfig().getStringList("caches");
        this.caches = this.cacheStrings.stream().map(LocationUtils::fromString).collect(Collectors.toList());
    }

    public List<String> getCacheStrings() {
        return this.cacheStrings;
    }

    public List<Location> getCaches() {
        return this.caches;
    }

    public boolean isCache(String cache) {
        return this.cacheStrings.contains(cache);
    }

    public boolean isCache(Location cache) {
        return this.caches.contains(cache);
    }

    /**
     * If the cache is not already in the list, add it to the list and set it as the next cache for all
     * players
     * 
     * @param cache The location of the cache
     * @return A boolean
     */
    public boolean addCache(Location cache) {
        if (this.isCache(cache))
            return false;

        this.cacheStrings.add(LocationUtils.toString(cache));
        this.caches.add(cache);
        this.plugin.getConfig().set("caches", this.cacheStrings);
        this.plugin.saveConfig();

        PlayerData.forEach(data -> {
            if (data.getCache() == null) this.setCache(data, cache);
        });

        return true;
    }

    /**
     * If the cache is in the list, remove it from the list, remove it from the config, and save the
     * config
     * 
     * @param cache The location of the cache to remove
     * @return A boolean value.
     */
    public boolean removeCache(Location cache) {
        if (!this.caches.remove(cache))
            return false;

        this.cacheStrings.remove(LocationUtils.toString(cache));
        this.caches.remove(cache);
        this.plugin.getConfig().set("caches", this.cacheStrings);
        this.plugin.saveConfig();
        return true;
    }

    /**
     * Adds the current cache to the collected caches, sets the current cache to null, sets the current
     * cache hint to null, saves the data, and then calls the newCache function.
     * 
     * @param player The player who is completing the cache
     * @param data The PlayerData object for the player
     */
    public void completeCache(Player player, PlayerData data) {
        data.addCollected(data.getCache());
        data.setCache(null);
        data.setCacheHint(null);
        data.save();

        this.newCache(player, data);
    }

    /**
     * "If the player has not collected all the caches, pick a random one that they haven't collected
     * yet and set it as their current cache."
     * 
     * The first thing we do is get a list of all the caches the player has already collected. We do
     * this by calling `data.getCollected()`. This returns a `List<String>` of all the caches the
     * player has collected
     * 
     * @param player The player that is being given a new cache. This is null if the player is offline.
     * @param data The PlayerData object for the player.
     */
    public void newCache(@Nullable Player player, PlayerData data) {
        List<String> done = data.getCollected();

        String newCache = this.cacheStrings.stream()
                .filter(cache -> !done.contains(cache))
                .reduce((v1, v2) -> R.nextBoolean() ? v1 : v2)
                .orElse(null);

        if (newCache == null) {
            data.setActionBar(false);
            data.save();

            if (player != null)
                Messages.NO_GEO.send(player);
        } else {
            this.setCache(data, LocationUtils.fromString(newCache));
        }
    }

    /**
     * It sets the cache location of the player to the given location
     * 
     * @param data The PlayerData object of the player.
     * @param cache The location of the cache
     */
    private void setCache(PlayerData data, Location cache) {
        data.setCache(cache);
        data.setCacheHint(this.getCacheHint(cache));
        data.save();
    }

    /**
     * It returns a random location within a 20 block radius of the given location
     * 
     * @param cache The location of the cache
     * @return A random location within a radius of 25 blocks of the cache.
     */
    private Location getCacheHint(Location cache) {
        double radian = Math.toRadians(R.nextDouble() * 360.0);
        double radius = R.nextDouble() * 20 + 5;

        return cache.clone().add(Math.sin(radian) * radius, 0, Math.cos(radian) * radius);
    }
}
