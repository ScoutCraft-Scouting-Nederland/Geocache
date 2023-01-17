package nl.scoutcraft.geocache.menu;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import nl.scoutcraft.eagle.libs.utils.Colors;
import nl.scoutcraft.eagle.server.gui.inventory.base.AbstractPaginationMenu;
import nl.scoutcraft.eagle.server.gui.inventory.base.Button;
import nl.scoutcraft.eagle.server.gui.inventory.base.ButtonClickType;
import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import nl.scoutcraft.geocache.Geocache;
import nl.scoutcraft.geocache.locale.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * It's a menu that shows a list of all geocaches and allows the player to teleport to them or delete
 * them
 */
public class AdminCacheMenu extends AbstractPaginationMenu {

    private static final ItemBuilder GEOCACHE = new ItemBuilder(Material.MAP).name(Messages.ITEM_GEOCACHE_NAME);
    private static final ItemBuilder BACK = new ItemBuilder(Material.BARRIER).name(Messages.ITEM_BACK_NAME).lore(Messages.ITEM_BACK_LORE);

    public AdminCacheMenu(Player player) {
        super(player);
        super.setTitle(Colors.GRAY + "" + ChatColor.BOLD + "» " + Colors.SCOUT + ChatColor.BOLD + "Admin Geocaches Menu");
    }

    /**
     * "Get a list of buttons that represent each cache in the cache list."
     * 
     * The first line of the function is a Java annotation. It's not required, but it's good practice
     * to include it. It tells Java that this function is overriding a function in the parent class
     * 
     * @param player The player who is viewing the menu
     * @return A list of buttons.
     */
    @Override
    protected List<Button> getListButtons(Player player) {
        return Geocache.getInstance().getCacheList().getCaches().stream().map(this::getCacheButton).collect(Collectors.toList());
    }

    /**
     * It returns a button that teleports the player to the cache when left clicked, and opens a
     * confirmation menu when right clicked
     * 
     * @param cache The location of the cache
     * @return A button that teleports the player to the cache when left clicked, and opens a
     * confirmation menu when right clicked.
     */
    private Button getCacheButton(Location cache) {
        return Button.builder()
                .setItem(GEOCACHE.copy().lore(this.getCacheLore(super.player, cache)))
                .addAction(ButtonClickType.ANY_LEFT, p -> p.teleport(cache))
                .addAction(ButtonClickType.ANY_RIGHT, p -> new ConfirmMenu(u1 -> this.deleteCache(cache), u2 -> super.open()).open(super.player))
                .build();
    }

    /**
     * It takes a player and a location, and returns a list of strings that are the lines of the lore
     * of the item
     * 
     * @param player The player who is viewing the item.
     * @param cache The location of the cache
     * @return A list of strings.
     */
    private List<String> getCacheLore(Player player, Location cache) {
        return Lists.newArrayList(Messages.ITEM_GEOCACHE_ADMIN_LORE.get(player, true)
                .replace("%x%", Integer.toString(cache.getBlockX()))
                .replace("%y%", Integer.toString(cache.getBlockY()))
                .replace("%z%", Integer.toString(cache.getBlockZ()))
                .split("\n"));
    }

    /**
     * Delete the cache from the cache list and update the list.
     * 
     * @param cache The cache to delete
     */
    private void deleteCache(Location cache) {
        Geocache.getInstance().getCacheList().removeCache(cache);
        super.update();
        super.open();
    }

   /**
    * Return a list of buttons that will be displayed in the menu.
    * 
    * @param player The player who opened the menu
    * @return A list of buttons.
    */
    @Override
    protected List<Button> getMenuButtons(Player player) {
        return Lists.newArrayList(Button.builder().setSlots(49).setItem(BACK).addAction(ButtonClickType.ANY, p -> new GeoMenu(p).open()).build());
    }
}
