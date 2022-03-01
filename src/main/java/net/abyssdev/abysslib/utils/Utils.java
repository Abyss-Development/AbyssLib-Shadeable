package net.abyssdev.abysslib.utils;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * A bunch of various utility methods
 */
@UtilityClass
public class Utils {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");

    /**
     * Get a generic type from an array at an index
     *
     * @param index The index
     * @param array The array
     * @param <T>   The type
     * @return The type received, or null
     */
    public <T> T getFromArray(final int index, final T[] array) {
        return getFromArray(index, array, null);
    }

    /**
     * Get a generic type from an array at an index
     *
     * @param index     The index
     * @param array     The array
     * @param defaultTo The object to default to if it's not found
     * @param <T>       The  type
     * @return The type received, or defaulted to
     */
    public <T> T getFromArray(final int index, final T[] array, final T defaultTo) {
        if (array.length > index) {
            return array[index];
        }

        return defaultTo;
    }

    /**
     * Check if a string can be converted to an Integer
     *
     * @param arg The string to test
     * @return The {@link Boolean} value of if it can become a {@link Integer}
     */
    public boolean isInteger(final String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Get a {@link List} of slots from a String List
     *
     * @param slots The raw list of slots
     * @return The list of integer slots
     */
    public List<Integer> getSlots(List<String> slots) {
        List<Integer> array = new ArrayList<>();

        for (final String rawSlot : slots) {
            if (!rawSlot.contains("-") && isInteger(rawSlot)) {
                array.add(Integer.parseInt(rawSlot));
                continue;
            }

            if (!rawSlot.contains("-")) {
                continue;
            }

            final String[] slotSplit = rawSlot.split("-");

            if (isInteger(slotSplit[0]) && isInteger(slotSplit[1])) {
                int min = Integer.parseInt(slotSplit[0]);
                int max = Integer.parseInt(slotSplit[1]);

                if (min > max) {
                    continue;
                }

                for (int i = min; i <= max; i++) {
                    array.add(i);
                }
            }
        }

        return array;
    }

    /**
     * Get a {@link String} time format from milliseconds
     *
     * @param time The milliseconds to format
     * @return The formatted date
     */
    public String getTimeFormat(final long time) {
        if (time <= 0) {
            return "now";
        }

        final long days = TimeUnit.MILLISECONDS.toDays(time);
        final long hours = TimeUnit.MILLISECONDS.toHours(time) - (days * 24);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (TimeUnit.MILLISECONDS.toHours(time) * 60);
        final long second = TimeUnit.MILLISECONDS.toSeconds(time) - (TimeUnit.MILLISECONDS.toMinutes(time) * 60);

        final StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append("d,");
        }

        if (hours > 0) {
            sb.append(hours).append("h,");
        }

        if (minutes > 0) {
            sb.append(minutes).append("m,");
        }

        if (second > 0) {
            sb.append(second).append("s.");
        }

        final String s = sb.toString();
        return s.isEmpty() ? "now" : s;
    }

    /**
     * {@link DecimalFormat} a {@link String}
     *
     * @param object The {@link Object} to format
     * @param <T>    The type of object
     * @return The {@link String} that is formatted
     */
    public <T> String format(final T object) {
        return DECIMAL_FORMAT.format(object);
    }

    /**
     * Check if a {@link String} is a {@link Long}
     *
     * @param args The {@link String} to test
     * @return The {@link Boolean} value of if it can be a {@link Long}
     */
    public boolean isLong(final String args) {
        try {
            Long.parseLong(args);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Check if a {@link String} is a {@link Double}
     *
     * @param args The {@link String} to test
     * @return The {@link Boolean} value of if it can be a {@link Double}
     */
    public boolean isDouble(final String args) {
        try {
            Double.parseDouble(args);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Get an {@link Optional} {@link Long}
     *
     * @param args The {@link String} to get
     * @return The {@link Optional} {@link Long}
     */
    public Optional<Long> getLong(final String args) {
        final Long value = Utils.isLong(args) ? Long.parseLong(args) : null;

        return Optional.ofNullable(value);
    }

    /**
     * Get an {@link Optional} {@link Double}
     *
     * @param args The {@link String} to get
     * @return The {@link Optional} {@link Double}
     */
    public Optional<Double> getDouble(final String args) {
        final Double value = Utils.isDouble(args) ? Double.parseDouble(args) : null;

        return Optional.ofNullable(value);
    }

}
