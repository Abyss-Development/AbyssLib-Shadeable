package net.abyssdev.abysslib.utils.file;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

@UtilityClass
public final class Files {

    @SneakyThrows
    public static FileConfiguration config(final String name, final Plugin plugin) {
        final FileConfiguration config = new YamlConfiguration();
        config.load(Files.file(name, plugin));
        return config;
    }

    @SneakyThrows
    public static File file(final String name, final Plugin plugin) {
        plugin.getDataFolder().mkdirs();
        final File f = new File(plugin.getDataFolder(), name);

        if (!f.exists()) {
            f.getParentFile().mkdir();
            plugin.saveResource(name, false);
        }

        return f;
    }

}
