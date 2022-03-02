package net.abyssdev.abysslib.text;

import lombok.Getter;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.text.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chubbyduck1
 */
public class MessageCache {

    @Getter
    private final FileConfiguration config;
    private final Map<String, Message> messages;

    public MessageCache(FileConfiguration config) {
        this.config = config;
        this.messages = new HashMap<>();
    }

    public MessageCache sendMessage(Player player, String path) {
        if (hasMessage(path))
            getMessage(path).send(player);

        return this;
    }

    public MessageCache sendMessage(CommandSender sender, String path) {
        if (hasMessage(path))
            getMessage(path).send(sender);

        return this;
    }

    public MessageCache sendMessage(Player player, PlaceholderReplacer placeholders, String path) {
        if (hasMessage(path))
            getMessage(path).send(player, placeholders);

        return this;
    }

    public MessageCache sendMessage(CommandSender sender, PlaceholderReplacer placeholders, String path) {
        if (hasMessage(path))
            getMessage(path).send(sender, placeholders);

        return this;
    }

    public MessageCache sendMessage(Player player, String path, PlaceholderReplacer placeholders) {
        if (hasMessage(path))
            getMessage(path).send(player, placeholders);

        return this;
    }

    public MessageCache sendMessage(CommandSender sender, String path, PlaceholderReplacer placeholders) {
        if (hasMessage(path))
            getMessage(path).send(sender, placeholders);

        return this;
    }


    public MessageCache loadMessage(String path) {
        messages.put(path.toLowerCase(), new Message(this, path));
        return this;
    }

    public boolean hasMessage(String path) {
        return messages.containsKey(path.toLowerCase());
    }

    public Message getMessage(String path) {
        return messages.get(path.toLowerCase());
    }

}
