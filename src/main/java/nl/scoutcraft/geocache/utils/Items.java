package nl.scoutcraft.geocache.utils;

import net.md_5.bungee.api.ChatColor;
import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {

    private static final ChatColor GREEN = ChatColor.of("#00A551");
    private static final ChatColor RED = ChatColor.of("#D72638");

    public static final ItemStack GEO_WAND = new ItemBuilder(Material.BLAZE_ROD)
            .name(GREEN + "" + ChatColor.BOLD + "Geocache " + ChatColor.GOLD + ChatColor.BOLD + "Manager")
            .lore(RED + "" + ChatColor.BOLD + "[!] " + ChatColor.GRAY + "Left-click to remove button.",
                    RED + "" + ChatColor.BOLD + "[!] " + ChatColor.GRAY + "Right-click to add button.",
                    " ",
                    ChatColor.GOLD + "" + ChatColor.BOLD + "Soulbound")
            .build();
}
