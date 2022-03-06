package net.abyssdev.abysslib;

import net.abyssdev.abysslib.bstats.Metrics;
import net.abyssdev.abysslib.economy.registry.impl.DefaultEconomyRegistry;
import net.abyssdev.abysslib.menu.listeners.MenuClickListener;
import net.abyssdev.abysslib.menu.listeners.MenuCloseListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbyssLib extends JavaPlugin {

    private static AbyssLib instance;

    @Override
    public void onEnable() {
        AbyssLib.instance = this;
        this.saveDefaultConfig();
        new DefaultEconomyRegistry();
        this.getServer().getPluginManager().registerEvents(new MenuClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new MenuCloseListener(), this);

        // BStats
        new Metrics(this, 14553);
    }

    public static AbyssLib getInstance() {
        return AbyssLib.instance;
    }

}
