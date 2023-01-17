package nl.scoutcraft.geocache.utils;

import nl.scoutcraft.eagle.server.locale.Placeholder;
import nl.scoutcraft.geocache.locale.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * > This class is a Runnable that updates the ActionBar title
 */
public class ActionBarTask implements Runnable {

    /**
     * For each online player, send them an action bar
     */
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(this::sendActionBar);
    }

    /**
     * If the player has action bar enabled, and the cache hint is in the same world as the player,
     * send the action bar message
     * 
     * @param player The player to send the action bar to.
     */
    private void sendActionBar(Player player) {
        PlayerData data = PlayerData.get(player.getUniqueId());

        if (!data.isActionBar())
            return;

        Location cacheHint = data.getCacheHint();
        if (cacheHint == null)
            return;

        if (!cacheHint.getWorld().getName().equals(player.getWorld().getName()))
            return;

        int distance = (int) Math.floor(player.getLocation().distance(cacheHint));
        Messages.ACTION_BAR.sendActionBar(player,
                new Placeholder("%x%", Integer.toString(cacheHint.getBlockX())),
                new Placeholder("%y%", Integer.toString(cacheHint.getBlockY())),
                new Placeholder("%z%", Integer.toString(cacheHint.getBlockZ())),
                new Placeholder("%distance%", distance < 25 ? "< 25" : Integer.toString(distance)));
    }
}
