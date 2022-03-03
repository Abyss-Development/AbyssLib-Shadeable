package net.abyssdev.abysslib.logger;

import net.abyssdev.abysslib.AbyssLib;
import net.abyssdev.abysslib.text.Color;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class AbyssLogger {

    private static final boolean IS_DEBUG = AbyssLib.getInstance().getConfig().getBoolean("debug-mode", false);

    public static void debug(final Level level, final String... messages) {
        if (!AbyssLogger.IS_DEBUG) {
            return;
        }

        for (final String msg : messages) {
            Bukkit.getLogger().log(level, Color.parse("&3[Abyss] &b" + msg));
        }
    }

    public static void debug(final String... messages) {
        if (!AbyssLogger.IS_DEBUG) {
            return;
        }

        for (final String msg : messages) {
            Bukkit.getLogger().info(Color.parse("&3[Abyss] &b" + msg));
        }
    }

    public static void log(final Level level, final String... messages) {
        for (final String msg : messages) {
            Bukkit.getLogger().log(level, Color.parse("&3[Abyss] &b" + msg));
        }
    }

    public static void log(final String... messages) {
        for (final String msg : messages) {
            Bukkit.getLogger().info(Color.parse("&3[Abyss] &b" + msg));
        }
    }

}