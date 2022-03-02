package net.abyssdev.abysslib.listener;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class AbyssListener<T extends JavaPlugin> implements Listener {

    private final T plugin;

    public AbyssListener(final T plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

}