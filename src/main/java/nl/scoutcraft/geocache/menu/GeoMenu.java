package nl.scoutcraft.geocache.menu;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import nl.scoutcraft.eagle.libs.utils.Colors;
import nl.scoutcraft.eagle.server.gui.inventory.base.AbstractPlayerInventoryMenu;
import nl.scoutcraft.eagle.server.gui.inventory.base.Button;
import nl.scoutcraft.eagle.server.gui.inventory.base.ButtonClickType;
import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import nl.scoutcraft.geocache.locale.Messages;
import nl.scoutcraft.geocache.utils.Perms;
import nl.scoutcraft.geocache.utils.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * It's a menu that allows players to view their current geocache, toggle the action bar, and view all
 * caches
 */
public class GeoMenu extends AbstractPlayerInventoryMenu {

    private static final ItemBuilder GEOCACHE = new ItemBuilder(Material.COMPASS).name(Messages.ITEM_GEOCACHE_NAME);
    private static final ItemBuilder ALL_CACHES = new ItemBuilder(Material.FILLED_MAP).name(Messages.ITEM_ALL_CACHES_NAME).lore(Messages.ITEM_ALL_CACHES_LORE);
    private static final ItemBuilder CACHES = new ItemBuilder(Material.FILLED_MAP).name(Messages.ITEM_CACHES_NAME).lore(Messages.ITEM_CACHES_LORE);
    private static final ItemBuilder ACTIONBAR_GREEN = new ItemBuilder(Material.LIME_DYE).name(Messages.ITEM_ACTIONBAR_NAME).lore(Messages.ITEM_ACTIONBAR_ENABLED_LORE);
    private static final ItemBuilder ACTIONBAR_GRAY = new ItemBuilder(Material.GRAY_DYE).name(Messages.ITEM_ACTIONBAR_NAME).lore(Messages.ITEM_ACTIONBAR_DISABLED_LORE);
    private static final ItemBuilder EXIT = new ItemBuilder(Material.BARRIER).name(Messages.ITEM_EXIT_NAME).lore(Messages.ITEM_EXIT_LORE);

    public GeoMenu(Player player) {
        super(player);
        super.setTitle(Colors.GRAY + "" + ChatColor.BOLD + "» " + Colors.SCOUT + ChatColor.BOLD + "Geocaching");
        super.setSize(45);
    }

    /**
     * "This function returns a list of buttons that will be displayed in the menu."
     * 
     * The first thing we do is create a new list of buttons. This is where we will store all the
     * buttons that we want to display in the menu
     * 
     * @param player The player who opened the menu
     * @return A list of buttons.
     */
    @Override
    protected List<Button> getButtons(Player player) {
        List<Button> buttons = new ArrayList<>();

        PlayerData data = PlayerData.get(player.getUniqueId());
        Location cacheHint = data.getCache();

        buttons.add(Button.spacer(IntStream.range(0, 45).toArray()).build());

        if (cacheHint != null) {
            buttons.add(Button.builder()
                    .setSlots(13)
                    .setItem(GEOCACHE.copy().lore(this.getCacheLore(player, cacheHint)))
                    .build());
        }

        buttons.add(Button.builder()
                .setSlots(15)
                .setItem(data.isActionBar() ? ACTIONBAR_GREEN : ACTIONBAR_GRAY)
                .addAction(ButtonClickType.ANY, p -> this.toggleActionBar(data))
                .build());

        buttons.add(Button.builder()
                .setSlots(31)
                .setItem(EXIT)
                .addAction(ButtonClickType.ANY, HumanEntity::closeInventory)
                .build());

        Button.Builder button = Button.builder().setSlots(11);
        if (player.hasPermission(Perms.MANAGER)) {
            button.setItem(ALL_CACHES).addAction(ButtonClickType.ANY, p -> new AdminCacheMenu(p).open());
        } else {
            button.setItem(CACHES).addAction(ButtonClickType.ANY, p -> new CachesMenu(p).open());
        }
        buttons.add(button.build());

        return buttons;
    }

    /**
     * It takes a player and a location, and returns a list of strings that are the lore of the
     * geocache item
     * 
     * @param player The player who is viewing the cache
     * @param cache The location of the cache
     * @return A list of strings
     */
    private List<String> getCacheLore(Player player, Location cache) {
        int distance = (int) player.getLocation().distance(cache);

        return Lists.newArrayList(Messages.ITEM_GEOCACHE_LORE.get(player, true)
                .replace("%x%", Integer.toString(cache.getBlockX()))
                .replace("%y%", Integer.toString(cache.getBlockY()))
                .replace("%z%", Integer.toString(cache.getBlockZ()))
                .replace("%distance%", distance < 25 ? "< 25" : Integer.toString(distance))
                .split("\n"));
    }

    /**
     * Toggle the action bar, save the data, and update the scoreboard.
     * 
     * @param data The PlayerData object of the player who clicked the button.
     */
    private void toggleActionBar(PlayerData data) {
        data.setActionBar(!data.isActionBar());
        data.save();
        super.update();
    }
}
