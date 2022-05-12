package net.abyssdev.abysslib.menu.templates;

import lombok.Getter;
import net.abyssdev.abysslib.menu.MenuBuilder;
import net.abyssdev.abysslib.menu.item.BorderItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

@Getter
public abstract class AbyssMenu {

    private final String title;
    private final int size;
    private final BorderItem[] borders;

    public AbyssMenu(final FileConfiguration config, final String path) {
        this.title = config.getString(path + "title");
        this.size = config.getInt(path + "rows") * 9;

        // Borders

        final ConfigurationSection bordersSection = config.getConfigurationSection(path + "borders");
        final Set<String> bordersKeys = bordersSection.getKeys(false);

        this.borders = new BorderItem[bordersKeys.size()];

        int bordersIndex = 0;

        for (final String key : config.getConfigurationSection(path + "borders").getKeys(false)) {
            this.borders[bordersIndex] = new BorderItem(config, path + "borders." + key);
            bordersIndex++;
        }
    }

    public void open(final Player player) {}

    //retarded relo put an unused param! smh!!
    @Deprecated
    protected MenuBuilder createBase(final Player player) {
        final MenuBuilder builder = new MenuBuilder(this.getTitle(), this.getSize());
        builder.setBorders(this.borders);
        return builder;
    }

    protected MenuBuilder createBase() {
        final MenuBuilder builder = new MenuBuilder(this.getTitle(), this.getSize());
        builder.setBorders(this.borders);
        return builder;
    }

}