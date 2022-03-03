package net.abyssdev.abysslib;

import net.abyssdev.abysslib.economy.registry.impl.DefaultEconomyRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbyssLib extends JavaPlugin {

    private static AbyssLib instance;

    @Override
    public void onEnable() {
        AbyssLib.instance = this;
        this.saveDefaultConfig();
        new DefaultEconomyRegistry();
    }

    public static AbyssLib getInstance() {
        return AbyssLib.instance;
    }

}
