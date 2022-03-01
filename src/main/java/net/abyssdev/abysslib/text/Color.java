package net.abyssdev.abysslib.text;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Color {

    private static final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private static final Pattern stripColorPattern = Pattern.compile("(?i)&[0-9A-FK-ORX]");

    public static Collection<String> parse(Collection<String> list) {
        return list.stream().map(Color::hex).map(Color::parse).collect(Collectors.toList());
    }

    public static List<String> parse(List<String> list) {
        return list.stream().map(Color::hex).map(Color::parse).collect(Collectors.toList());
    }

    public static List<String> parse(String... list) {
        return Arrays.stream(list).map(Color::hex).map(Color::parse).collect(Collectors.toList());
    }

    public static String parse(String s) {
        return ChatColor.translateAlternateColorCodes('&', Color.hex(s));
    }

    public static String hex(String s) {
        Matcher match = hexPattern.matcher(s);
        while(match.find()) {
            String c = s.substring(match.start(), match.end());
            s = s.replace(c, net.md_5.bungee.api.ChatColor.of(c) + "");
            match = hexPattern.matcher(s);
        }
        return s;
    }

    public static BaseComponent[] toComponent(String string) {
        return TextComponent.fromLegacyText(parse(string));
    }

    public static String strip(String s) {
        return stripColorPattern.matcher(s).replaceAll("");
    }


}
