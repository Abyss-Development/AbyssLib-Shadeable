package net.abyssdev.abysslib.plugin;

import net.abyssdev.abysslib.economy.registry.impl.DefaultEconomyRegistry;
import net.abyssdev.abysslib.menu.listeners.MenuClickListener;
import net.abyssdev.abysslib.menu.listeners.MenuCloseListener;
import net.abyssdev.abysslib.text.MessageCache;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class AbyssPlugin extends JavaPlugin {

    private static AbyssPlugin instance;

    public static AbyssPlugin getInstance() {
        return AbyssPlugin.instance;
    }

    @Override
    public void onEnable() {
        AbyssPlugin.instance = this;

        this.getServer().getPluginManager().registerEvents(new MenuClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new MenuCloseListener(), this);

        // Economy
        new DefaultEconomyRegistry();

        this.onStart();
    }

    @Override
    public void onDisable() {
        this.onStop();
    }

    public abstract void onStart();
    public abstract void onStop();

    /**
     * Loads and gives you a configuration file.
     * You don't need to add ".yml" to the end, just put the file name.
     * @param path The files path
     * @return returns a {@link FileConfiguration} with the specified name.
     */

    public FileConfiguration getConfig(String path) {
        path = path + ".yml";
        final File file = new File(this.getDataFolder(), path);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            this.saveResource(path, false);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    protected void loadMessages(final MessageCache cache, final FileConfiguration config) {
        for (final String key : config.getConfigurationSection("messages").getKeys(false)) {
            cache.loadMessage("messages." + key);
        }
    }

}