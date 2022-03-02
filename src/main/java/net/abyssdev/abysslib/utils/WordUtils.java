package net.abyssdev.abysslib.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WordUtils {

    /**
     * Format a {@link String}
     *
     * @param text The {@link String} to format
     * @return The formatted {@link String}
     */
    public String formatText(String text) {

        text = text.toLowerCase();

        final char[] chars = new char[text.toCharArray().length];

        int index = -1;
        boolean isFirst = true;

        for (char c : text.toCharArray()) {

            index++;

            if (c == ' ') {
                isFirst = true;
                chars[index] = c;
                continue;
            }

            if (!isFirst) {
                chars[index] = c;
                continue;
            }

            chars[index] = Character.toUpperCase(c);
            isFirst = false;

        }

        return new String(chars);

    }

    /**
     * Remove characters from a {@link String}
     *
     * @param text  The text to replace in
     * @param chars The characters to replace
     * @return The processed {@link String}
     */
    public String removeChars(String text, final char... chars) {
        for (final char characters : chars) {
            text = text.replace(String.valueOf(characters), "");
        }

        return text;
    }

    /**
     * Replace characters in a string
     *
     * @param text        The {@link String} to replace
     * @param replacement The {@link String} replacement for the characters
     * @param chars       The characters to replace
     * @return The {@link String}
     */
    public String replaceChars(String text, final String replacement, final char... chars) {
        for (final char character : chars) {
            text = text.replace(String.valueOf(character), replacement);
        }

        return text;
    }

}
