package net.abyssdev.abysslib.text.message;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import lombok.Getter;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.text.Color;
import net.abyssdev.abysslib.text.MessageCache;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

@Getter
public class Message {

    private final boolean soundEnabled,
            actionBarEnabled,
            titleEnabled,
            messageEnabled;

    private final String sound,
            actionBar,
            title,
            subTitle;

    private final int volume,
            pitch,
            fadeInTicks,
            stayTicks,
            fadeOutTicks;

    private final List<String> messages;

    public Message(MessageCache messageCache, String path) {
        FileConfiguration config = messageCache.getConfig();

        this.soundEnabled = config.getBoolean(path + ".sound.enabled", false);
        this.sound = config.getString(path + ".sound.value", "ENTITY_PLAYER_LEVELUP");
        this.volume = config.getInt(path + ".sound.volume", (int) XSound.DEFAULT_VOLUME);
        this.pitch = config.getInt(path + ".sound.pitch", (int) XSound.DEFAULT_PITCH);

        this.actionBarEnabled = config.getBoolean(path + ".action-bar.enabled", false);
        this.actionBar = Color.parse(config.getString(path + ".action-bar.value", ""));

        this.titleEnabled = config.getBoolean(path + ".title.enabled", false);
        this.title = Color.parse(config.getString(path + ".title.title", ""));
        this.subTitle = Color.parse(config.getString(path + ".title.sub-title"));

        this.fadeInTicks = config.getInt(path + ".title.advanced.fade-in-ticks", 20);
        this.stayTicks = config.getInt(path + ".title.advanced.stay-ticks", 20);
        this.fadeOutTicks = config.getInt(path + ".title.advanced.fade-out-ticks", 20);

        this.messageEnabled = config.getBoolean(path + ".message.enabled", false);
        this.messages = Color.parse(config.getStringList(path + ".message.value"));
    }

    public void send(CommandSender sender) {
        send(sender, new PlaceholderReplacer());
    }

    public void send(CommandSender sender, PlaceholderReplacer placeholders) {

        if (sender instanceof Player && this.messageEnabled) {
            for (String message : this.messages) {
                sender.sendMessage(placeholders.parse((OfflinePlayer) sender, message));
            }
        } else if (this.messageEnabled) {
            for (String message : this.messages) {
                sender.sendMessage(placeholders.parse(message));
            }
        }

        if (!(sender instanceof Player)) {
            return;
        }

        final Player player = (Player) sender;

        if (this.soundEnabled) {
            Optional<XSound> xSoundOptional = XSound.matchXSound(this.sound);
            xSoundOptional.ifPresent(xSound -> xSound.play((Player) sender, this.volume, this.pitch));
        }

        if (this.actionBarEnabled) {
            ActionBar.sendActionBar(player, placeholders.parse(this.actionBar));
        }

        if (this.titleEnabled) {
            Titles.sendTitle(player, this.fadeInTicks, this.stayTicks, this.fadeOutTicks,
                    placeholders.parse(this.title), placeholders.parse(this.subTitle));
        }
    }

}
