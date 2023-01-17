package nl.scoutcraft.geocache.menu;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import nl.scoutcraft.eagle.libs.utils.Colors;
import nl.scoutcraft.eagle.server.gui.inventory.base.AbstractPaginationMenu;
import nl.scoutcraft.eagle.server.gui.inventory.base.Button;
import nl.scoutcraft.eagle.server.gui.inventory.base.ButtonClickType;
import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import nl.scoutcraft.geocache.locale.Messages;
import nl.scoutcraft.geocache.utils.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It's a menu that shows all the caches the player has collected
 */
public class CachesMenu extends AbstractPaginationMenu {

    private static final ItemBuilder GEOCACHE = new ItemBuilder(Material.MAP).name(Messages.ITEM_GEOCACHE_NAME);
    private static final ItemBuilder BACK = new ItemBuilder(Material.BARRIER).name(Messages.ITEM_BACK_NAME).lore(Messages.ITEM_BACK_LORE);

    public CachesMenu(Player player) {
        super(player);
        super.setTitle(Colors.GRAY + "" + ChatColor.BOLD + "» " + Colors.SCOUT + ChatColor.BOLD + "Geocaches Menu");
    }

    /**
     * "Get a list of buttons that are the cache buttons for each cache that the player has collected."
     * 
     * The first line of the function is the annotation `@Override`. This is a Java annotation that
     * tells the compiler that this function is overriding a function from a parent class. In this
     * case, the parent class is `AbstractMenu`
     * 
     * @param player The player who is viewing the inventory
     * @return A list of buttons
     */
    @Override
    protected List<Button> getListButtons(Player player) {
        return PlayerData.get(player.getUniqueId()).getCollected().stream().map(this::getCacheButton).collect(Collectors.toList());
    }

    /**
     * It returns a button that has the item of a geocache, and the lore of the geocache.
     * 
     * @param cache The cache name
     * @return A button that is a copy of the GEOCACHE item with lore that is the result of the
     * getCacheLore method.
     */
    private Button getCacheButton(String cache) {
        return Button.builder().setItem(GEOCACHE.copy().lore(this.getCacheLore(super.player, cache.split(",")))).build();
    }

    /**
     * It takes a player and a cache, and returns a list of strings that are the lore of the cache
     * 
     * @param player The player who is viewing the item.
     * @param cache The cache that is being added to the inventory.
     * @return A list of strings
     */
    private List<String> getCacheLore(Player player, String[] cache) {
        return Lists.newArrayList(Messages.ITEM_GEOCACHE_COMPLETED_LORE.get(player, true).replace("%x%", cache[1]).replace("%y%", cache[2]).replace("%z%", cache[3]).split("\n"));
    }

    /**
     * It returns a list of buttons that will be displayed in the menu
     * 
     * @param player The player who opened the menu
     * @return A list of buttons
     */
    @Override
    protected List<Button> getMenuButtons(Player player) {
        List<Button> buttons = new ArrayList<>();

        buttons.add(Button.builder()
                .setSlots(49)
                .setItem(BACK)
                .addAction(ButtonClickType.ANY, p -> new GeoMenu(p).open())
                .build());

        return buttons;
    }
}
