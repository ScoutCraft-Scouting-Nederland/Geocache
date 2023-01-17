package nl.scoutcraft.geocache.listener;

import nl.scoutcraft.geocache.utils.CacheList;
import nl.scoutcraft.geocache.Geocache;
import nl.scoutcraft.geocache.locale.Messages;
import nl.scoutcraft.geocache.utils.Items;
import nl.scoutcraft.geocache.utils.Perms;
import nl.scoutcraft.geocache.utils.PlayerData;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClientListener implements Listener {

    private static final BlockFace[] FACES = {BlockFace.SELF, BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    private final Geocache plugin;

    public ClientListener(Geocache plugin) {
        this.plugin = plugin;
    }

    /**
     * When a player joins the server, load their data from the database
     * 
     * @param event The event that is being listened for.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerData.load(event.getPlayer().getUniqueId());
    }

    /**
     * When a player quits, unload their data
     * 
     * @param event The event that is being listened for.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData.unload(event.getPlayer().getUniqueId());
    }

    /**
     * If the block broken is a button, and the button is attached to a cache, cancel the event
     * 
     * @param event The event that is being listened for.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        for (BlockFace face : FACES) {
            Block attached = event.getBlock().getRelative(face);

            if (Tag.BUTTONS.isTagged(attached.getType()) && this.plugin.getCacheList().isCache(attached.getLocation())) {
                event.setCancelled(true);
                if (player.hasPermission(Perms.MANAGER))
                    Messages.BREAK_WAND.send(player);
                return;
            }
        }
    }

    /**
     * If a player drops a GeoWand, cancel the event and send them a message
     * 
     * @param event The event that is being listened for.
     */
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().isSimilar(Items.GEO_WAND)) {
            event.setCancelled(true);
            Messages.ITEM_DROP.send(event.getPlayer());
        }
    }

   /**
    * If the player dies, remove the GeoWand from their inventory
    * 
    * @param event The event that is being listened for.
    */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(item -> item.isSimilar(Items.GEO_WAND));
    }

    /**
     * If the player is holding a GeoWand, and they left click a button, remove the cache at that
     * location. If they right click a button, add a cache at that location. If they are not holding a
     * GeoWand, and they click a button, check if the button is the cache they are looking for. If it
     * is, they have completed the cache
     * 
     * @param event The event that was called.
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || !Tag.BUTTONS.isTagged(event.getClickedBlock().getType()))
            return;

        CacheList caches = this.plugin.getCacheList();
        Player player = event.getPlayer();
        if (Items.GEO_WAND.isSimilar(player.getInventory().getItemInMainHand()) && player.hasPermission(Perms.MANAGER)) {

            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setUseInteractedBlock(Event.Result.DENY);

                if (caches.removeCache(event.getClickedBlock().getLocation())) {
                    Messages.CACHE_REMOVE.send(player);
                } else {
                    Messages.CACHE_EXISTS_NOT.send(player);
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setUseInteractedBlock(Event.Result.DENY);

                if (caches.addCache(event.getClickedBlock().getLocation())) {
                    Messages.CACHE_ADD.send(player);
                } else {
                    Messages.CACHE_EXISTS.send(player);
                }
            }
        } else {
            PlayerData data = PlayerData.get(player.getUniqueId());
            if (event.getClickedBlock().getLocation().equals(data.getCache())) {
                Messages.SUCCEED.send(player);
                caches.completeCache(player, data);
            }
        }
    }
}
