package nl.scoutcraft.geocache.utils;

import nl.scoutcraft.geocache.Geocache;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * It's a wrapper for a YAML file that contains player data
 */
public class PlayerData extends YamlConfiguration {

    private static final Map<UUID, PlayerData> CACHE = new HashMap<>();

    private final File file;

    // It's creating a new file for the player.
    public PlayerData(UUID uuid) {
        file = new File(Geocache.getInstance().getDataFolder(), "userdata" + File.separator + uuid.toString() + ".yaml");
        reload();
    }

    /**
     * If the cache doesn't contain the player's UUID, create a new PlayerData object for them and add
     * it to the cache
     * 
     * @param uuid The UUID of the player you want to get the data of.
     * @return A new PlayerData object.
     */
    public static PlayerData get(UUID uuid) {
        return CACHE.computeIfAbsent(uuid, PlayerData::new);
    }

    /**
     * For each player data in the cache, do the action.
     * 
     * @param action The action to be performed for each element
     */
    public static void forEach(Consumer<PlayerData> action) {
        CACHE.values().forEach(action);
    }

    /**
     * If the player doesn't have an actionbar setting, set it to true and save it. If the player
     * doesn't have a cache, create a new one
     * 
     * @param uuid The UUID of the player to load.
     */
    public static void load(UUID uuid) {
        PlayerData data = get(uuid);

        if (!data.contains("actionbar")) {
            data.setActionBar(true);
            data.save();
        }

        if (!data.hasCache())
            Geocache.getInstance().getCacheList().newCache(null, data);
    }

    /**
     * It removes the player from the cache
     * 
     * @param uuid The UUID of the player you want to unload.
     */
    public static void unload(UUID uuid) {
        CACHE.remove(uuid);
    }

    /**
     * It tries to load the file, and if it fails, it ignores the error
     */
    public void reload() {
        try {
            load(file);
        } catch (Exception ignore) {
        }
    }

   /**
    * If the save() function throws an exception, ignore it.
    */
    public void save() {
        try {
            save(file);
        } catch (Exception ignore) {
        }
    }

    /**
     * Returns true if the current activity is an action bar.
     * 
     * @return A boolean value.
     */
    public boolean isActionBar() {
        return super.getBoolean("actionbar");
    }

    /**
     * This function sets the actionbar to the value of the boolean passed in.
     * 
     * @param value The value to set the parameter to.
     */
    public void setActionBar(boolean value) {
        super.set("actionbar", value);
    }

    /**
     * If the cache key is not null, return true.
     * 
     * @return A boolean value.
     */
    public boolean hasCache() {
        return super.getString("cache", null) != null;
    }

    /**
     * If the cache is null, return null, otherwise return the cache.
     * 
     * @return A Location object
     */
    @Nullable
    public Location getCache() {
        String cache = super.getString("cache", null);
        return cache == null ? null : LocationUtils.fromString(cache);
    }

    /**
     * If the cache is null, set the cache to null, otherwise set the cache to the string
     * representation of the cache.
     * 
     * @param cache The location of the cache.
     */
    public void setCache(@Nullable Location cache) {
        super.set("cache", cache == null ? null : LocationUtils.toString(cache));
    }

    /**
     * If the cachehint key is not null, return the location from the cachehint key, otherwise return
     * null.
     * 
     * @return A Location object
     */
    @Nullable
    public Location getCacheHint() {
        String cachehint = super.getString("cachehint", null);
        return cachehint == null ? null : LocationUtils.fromString(cachehint);
    }

    /**
     * Sets the cache hint for this request.
     * 
     * @param cache The location of the cache.
     */
    public void setCacheHint(@Nullable Location cache) {
        super.set("cachehint", cache == null ? null : LocationUtils.toString(cache));
    }

    /**
     * This function returns a list of strings that are stored in the config file under the key
     * 'collected'.
     * 
     * @return A list of strings
     */
    public List<String> getCollected() {
        return super.getStringList("collected");
    }

    /**
     * It adds a location to the list of collected locations
     * 
     * @param location The location of the block that was broken.
     */
    public void addCollected(Location location) {
        List<String> collected = this.getCollected();
        collected.add(LocationUtils.toString(location));
        super.set("collected", collected);
    }
}
