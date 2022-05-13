package net.abyssdev.abysslib.placeholder;

import lombok.Setter;
import lombok.experimental.Accessors;
import me.clip.placeholderapi.PlaceholderAPI;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chubbyduck1
 */

@Setter
@Accessors(chain = true)
public class PlaceholderReplacer {

    private final Map<String, String> placeholders = new HashMap<>();
    private boolean usePlaceholderAPI;

    public PlaceholderReplacer addPlaceholder(String key, String value) {
        this.placeholders.put(key, value);
        return this;
    }

    public PlaceholderReplacer addPlaceholder(String key, int value) {
        this.placeholders.put(key, Utils.format(value));
        return this;
    }

    public PlaceholderReplacer addPlaceholder(String key, long value) {
        this.placeholders.put(key, Utils.format(value));
        return this;
    }

    public PlaceholderReplacer addPlaceholder(String key, double value) {
        this.placeholders.put(key, Utils.format(value));
        return this;
    }

    public boolean hasPlaceholder(final String key) {
        return this.placeholders.containsKey(key);
    }

    public PlaceholderReplacer addTimePlaceholder(String key, int seconds) {
        this.placeholders.put(key, Utils.getTimeFormat(seconds * 1000L));

        String uppercase = key.replace("%", "");
        uppercase = "%" + uppercase + "_uppercase%";
        
        this.placeholders.put(uppercase, Utils.getTimeFormat(seconds * 1000L).toUpperCase());
        return this;
    }

    public String parse(String args) {
        for (Map.Entry<String, String> entry : this.placeholders.entrySet()) {
            args = args.replace(entry.getKey(), entry.getValue());
        }

        return args;
    }

    public String parse(OfflinePlayer player, String args) {
        args = parse(args);

        if (!(this.usePlaceholderAPI && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))) {
            return args;
        }

        return PlaceholderAPI.setPlaceholders(player, args);
    }

    public List<String> parse(List<String> list) {

        final List<String> cloned = new ArrayList<>(list);

        for (int i = 0; i < cloned.size(); i++) {
            cloned.set(i, parse(cloned.get(i)));
        }

        return cloned;
    }

    public List<String> parse(OfflinePlayer player, List<String> list) {

        final List<String> cloned = new ArrayList<>(list);

        for (int i = 0; i < cloned.size(); i++) {
            cloned.set(i, parse(player, cloned.get(i)));
        }

        return cloned;
    }

}
