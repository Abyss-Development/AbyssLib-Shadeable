package net.abyssdev.abysslib.command.registration;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistration {

    private static CommandMap commandMap;

    static {
        CommandRegistration.commandMap = (CommandMap) CommandRegistration.getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap", false);
    }

    @SneakyThrows
    private static Object getPrivateField(final Object object, final String fieldName, final boolean superClass) {
        final Class<?> clazz = object.getClass();
        final Field field = superClass ? clazz.getSuperclass().getDeclaredField(fieldName) : clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        final Object result = field.get(object);
        field.setAccessible(false);
        return result;
    }

    private static Map<String, Command> getKnownCommands() {
        return (Map<String, Command>) CommandRegistration.getPrivateField(CommandRegistration.commandMap, "knownCommands", true);
    }

    public static void register(final Command command) {
        CommandRegistration.commandMap.register("AbyssLib", command);
    }

    public static void unregister(final Command command) {
        command.unregister(CommandRegistration.commandMap);

        final Map<String, Command> knownCommands = new HashMap<>(getKnownCommands());
        for (final Map.Entry<String, Command> entry : knownCommands.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(command.getName())) {
                return;
            }
            CommandRegistration.getKnownCommands().remove(entry.getKey());
        }
    }

}
