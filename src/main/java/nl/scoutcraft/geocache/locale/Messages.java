package nl.scoutcraft.geocache.locale;

import nl.scoutcraft.eagle.libs.locale.Internationalization;
import nl.scoutcraft.eagle.server.locale.CompoundMessage;
import nl.scoutcraft.eagle.server.locale.IMessage;
import nl.scoutcraft.eagle.server.locale.Message;
import nl.scoutcraft.eagle.server.locale.MessagePlaceholder;
import nl.scoutcraft.geocache.Geocache;

/**
 * It's a class that contains all the messages that are used in the plugin
 */
public final class Messages {

    private static final Internationalization LANG = Geocache.getInstance().getLang();

    public static final IMessage<String> PREFIX = new Message(LANG, "prefix");

    public static final IMessage<String> CACHE_ADD = new CompoundMessage(LANG, "cache.add", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> CACHE_REMOVE = new CompoundMessage(LANG, "cache.remove", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> CACHE_REMOVED = new CompoundMessage(LANG, "cache.removed", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> CACHE_EXISTS = new CompoundMessage(LANG, "cache.exists", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> CACHE_EXISTS_NOT = new CompoundMessage(LANG, "cache.exists_not", new MessagePlaceholder("%prefix%", PREFIX));

    public static final IMessage<String> BAR_ON = new CompoundMessage(LANG, "bar.on", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> BAR_OFF = new CompoundMessage(LANG, "bar.off", new MessagePlaceholder("%prefix%", PREFIX));

    public static final IMessage<String> ITEM_DROP = new CompoundMessage(LANG, "itemDrop", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> BREAK_WAND = new CompoundMessage(LANG, "breakWand", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> SUCCEED = new CompoundMessage(LANG, "succeed", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> TELEPORTING = new CompoundMessage(LANG, "teleporting", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> NO_GEO = new CompoundMessage(LANG, "noGeo", new MessagePlaceholder("%prefix%", PREFIX));
//    public static final IMessage<String> HELP = new CompoundMessage(LANG, "help", new MessagePlaceholder("%prefix%", PREFIX));
    public static final IMessage<String> NO_PERM = new CompoundMessage(LANG, "noPerm", new MessagePlaceholder("%prefix%", PREFIX));

    public static final IMessage<String> ACTION_BAR = new Message(LANG, "action_bar");

    public static final IMessage<String> ITEM_CONFIRM_NAME = new Message(LANG, "item.confirm.name");
    public static final IMessage<String> ITEM_CANCEL_NAME = new Message(LANG, "item.cancel.name");

    public static final IMessage<String> ITEM_EXIT_NAME = new Message(LANG, "item.exit.name");
    public static final IMessage<String> ITEM_EXIT_LORE = new Message(LANG, "item.exit.lore");
    public static final IMessage<String> ITEM_BACK_NAME = new Message(LANG, "item.back.name");
    public static final IMessage<String> ITEM_BACK_LORE = new Message(LANG, "item.back.lore");

    public static final IMessage<String> ITEM_GEOCACHE_NAME = new Message(LANG, "item.geocache.name");
    public static final IMessage<String> ITEM_GEOCACHE_LORE = new Message(LANG, "item.geocache.lore");
    public static final IMessage<String> ITEM_GEOCACHE_COMPLETED_LORE = new Message(LANG, "item.geocache.completed_lore");
    public static final IMessage<String> ITEM_GEOCACHE_ADMIN_LORE = new Message(LANG, "item.geocache.admin_lore");
    public static final IMessage<String> ITEM_ACTIONBAR_NAME = new Message(LANG, "item.actionbar.name");
    public static final IMessage<String> ITEM_ACTIONBAR_ENABLED_LORE = new Message(LANG, "item.actionbar_enabled.lore");
    public static final IMessage<String> ITEM_ACTIONBAR_DISABLED_LORE = new Message(LANG, "item.actionbar_disabled.lore");
    public static final IMessage<String> ITEM_ALL_CACHES_NAME = new Message(LANG, "item.all_caches.name");
    public static final IMessage<String> ITEM_ALL_CACHES_LORE = new Message(LANG, "item.all_caches.lore");
    public static final IMessage<String> ITEM_CACHES_NAME = new Message(LANG, "item.caches.name");
    public static final IMessage<String> ITEM_CACHES_LORE = new Message(LANG, "item.caches.lore");

    private Messages() {}
}
