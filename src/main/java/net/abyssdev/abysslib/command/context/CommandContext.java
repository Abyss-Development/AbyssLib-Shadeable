package net.abyssdev.abysslib.command.context;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandContext<S extends CommandSender> {

    private final String[] arguments;
    private final S sender;
    private final Class<S> type;

    public CommandContext(final S sender, final Class<S> type, final String[] arguments) {
        this.arguments = arguments;
        this.sender = sender;
        this.type = type;
    }

    public <T extends CommandSender> T getSender() {
        return (T) this.type.cast(this.sender);
    }

    public String[] getArguments() {
        return this.arguments;
    }

    public int asInt(final int index) {
        int i;
        try {
            i = Integer.parseInt(this.arguments[index]);
        } catch (final NumberFormatException exception) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    public double asDouble(final int index) {
        double d;
        try {
            d = Double.parseDouble(this.arguments[index]);
        } catch (final NumberFormatException exception) {
            return Double.MAX_VALUE;
        }
        return d;
    }

    public long asLong(final int index) {
        long l;
        try {
            l = Long.parseLong(this.arguments[index]);
        } catch (final NumberFormatException exception) {
            return Long.MAX_VALUE;
        }
        return l;
    }

    public float asFloat(final int index) {
        float f;
        try {
            f = Float.parseFloat(this.arguments[index]);
        } catch (final NumberFormatException exception) {
            return Float.MAX_VALUE;
        }
        return f;
    }

    public byte asByte(final int index) {
        byte b;
        try {
            b = Byte.parseByte(this.arguments[index]);
        } catch (final NumberFormatException exception) {
            return Byte.MAX_VALUE;
        }
        return b;
    }

    public String asString(final int index) {
        return this.arguments[index];
    }

    public Player asPlayer(final int index) {
        return Bukkit.getPlayer(this.asString(index));
    }

    public Player asPlayerExact(final int index) {
        return Bukkit.getPlayerExact(this.asString(index));
    }

    public OfflinePlayer asOfflinePlayer(final int index) {
        return Bukkit.getOfflinePlayer(this.asString(index));
    }

    public <E extends Enum<E>> E asEnum(final int index, Class<E> type) {
        return Enum.valueOf(type, this.asString(index));
    }

    public boolean isPlayer(final int index) {
        return this.asPlayer(index) != null;
    }

    public boolean isPlayerExact(final int index) {
        return this.asPlayerExact(index) != null;
    }

    public boolean isByte(final int index) {
        return this.asByte(index) != Byte.MAX_VALUE;
    }

    public boolean isDouble(final int index) {
        return this.asDouble(index) != Double.MAX_VALUE;
    }

    public boolean isFloat(final int index) {
        return this.asFloat(index) != Float.MAX_VALUE;
    }

    public boolean isInt(final int index) {
        return this.asInt(index) != Integer.MAX_VALUE;
    }

    public boolean isLong(final int index) {
        return this.asLong(index) != Long.MAX_VALUE;
    }

}
