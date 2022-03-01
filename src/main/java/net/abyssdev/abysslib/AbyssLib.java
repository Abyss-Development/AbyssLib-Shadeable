package net.abyssdev.abysslib;

import org.bukkit.plugin.java.JavaPlugin;

public final class AbyssLib extends JavaPlugin {

    private static AbyssLib instance;

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public static AbyssLib getInstance() {
        return AbyssLib.instance;
    }

}
