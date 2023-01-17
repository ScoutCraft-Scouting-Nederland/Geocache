package nl.scoutcraft.geocache.command;

import nl.scoutcraft.geocache.menu.GeoMenu;
import nl.scoutcraft.geocache.utils.Items;
import nl.scoutcraft.geocache.utils.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GeoCommand implements CommandExecutor {

/**
 * If the player has permission, give them a wand, otherwise open the menu
 * 
 * @param sender The player who sent the command.
 * @param command The command that was executed.
 * @param label The command label.
 * @param args The arguments passed to the command.
 * @return A boolean value.
 */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        if (args.length >= 1 && args[0].equalsIgnoreCase("wand") && player.hasPermission(Perms.MANAGER)) {
            player.getInventory().addItem(Items.GEO_WAND);
        } else {
            new GeoMenu(player).open();
        }

        return true;
    }
}
