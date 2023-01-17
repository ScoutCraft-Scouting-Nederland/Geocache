package nl.scoutcraft.geocache;

import nl.scoutcraft.eagle.libs.locale.Internationalization;
import nl.scoutcraft.geocache.command.GeoCommand;
import nl.scoutcraft.geocache.listener.ClientListener;
import nl.scoutcraft.geocache.utils.ActionBarTask;
import nl.scoutcraft.geocache.utils.CacheList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Geocache extends JavaPlugin {

    private static Geocache instance;

    private Internationalization lang;
    private CacheList cacheList;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.lang = Internationalization.builder("messages")
                .setLangDir(super.getDataFolder().toPath().resolve("lang"))
                .setDefaultClassLoader(super.getClassLoader())
                .setDefaultLocale("nl")
                .addDefaultLangFiles("nl", "en")
                .build();
        this.cacheList = new CacheList(this);

        super.getCommand("geo").setExecutor(new GeoCommand());
        super.getServer().getPluginManager().registerEvents(new ClientListener(this), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ActionBarTask(), 10L, 10L);
    }

    @Override
    public void onDisable() {

    }

    public Internationalization getLang() {
        return this.lang;
    }

    public CacheList getCacheList() {
        return this.cacheList;
    }

    public static Geocache getInstance() {
        return instance;
    }
}
