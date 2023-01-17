package nl.scoutcraft.geocache.menu;

import net.md_5.bungee.api.ChatColor;
import nl.scoutcraft.eagle.libs.utils.Colors;
import nl.scoutcraft.eagle.server.gui.inventory.base.AbstractInventoryMenu;
import nl.scoutcraft.eagle.server.gui.inventory.base.Button;
import nl.scoutcraft.eagle.server.gui.inventory.base.ButtonClickType;
import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import nl.scoutcraft.geocache.locale.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * It's a menu that has two buttons, one that confirms an action and one that cancels it
 */
public class ConfirmMenu extends AbstractInventoryMenu {

    private static final ItemBuilder CONFIRM = new ItemBuilder(Material.LIME_DYE).name(Messages.ITEM_CONFIRM_NAME);
    private static final ItemBuilder CANCEL = new ItemBuilder(Material.RED_DYE).name(Messages.ITEM_CANCEL_NAME);

    private final Consumer<Player> confirmAction;
    private final Consumer<Player> cancelAction;

    public ConfirmMenu(Consumer<Player> confirmAction, Consumer<Player> cancelAction) {
        this.confirmAction = confirmAction;
        this.cancelAction = cancelAction;

        super.setTitle(Colors.GRAY + "" + ChatColor.BOLD + "» " + Colors.SCOUT + ChatColor.BOLD + "Confirm Deletion");
        super.setType(InventoryType.HOPPER);
    }

    /**
     * "Return a list of buttons that will be displayed in the GUI."
     * 
     * The first line of the function is a Java annotation. It's not required, but it's good practice
     * to include it
     * 
     * @return A list of buttons.
     */
    @Override
    protected List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();

        buttons.add(Button.spacer(0, 2, 4).build());
        buttons.add(Button.builder().setSlots(1).setItem(CONFIRM).addAction(ButtonClickType.ANY, this.confirmAction).build());
        buttons.add(Button.builder().setSlots(3).setItem(CANCEL).addAction(ButtonClickType.ANY, this.cancelAction).build());

        return buttons;
    }
}
