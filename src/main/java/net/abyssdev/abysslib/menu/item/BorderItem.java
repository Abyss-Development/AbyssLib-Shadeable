package net.abyssdev.abysslib.menu.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.abyssdev.abysslib.builders.ItemBuilder;
import net.abyssdev.abysslib.utils.ImmutablePair;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class BorderItem {

    private final ItemStack borderItem;
    private final Set<ImmutablePair<Integer, Integer>> minMax;

    /**
     * Automatically sets up the border-item for you to load into the menu builder.
     * @param config The config to grab the border from.
     * @param path The path to the border
     */
    public BorderItem(final FileConfiguration config, final String path) {
        this.borderItem = new ItemBuilder(config, path).parse();
        this.minMax = new HashSet<>();

        for (final String key : config.getStringList(path + ".slots")) {
            final String[] data = key.split("-");
            this.minMax.add(new ImmutablePair<>(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
        }
    }

}
