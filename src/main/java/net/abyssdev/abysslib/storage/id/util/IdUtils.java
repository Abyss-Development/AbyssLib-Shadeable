package net.abyssdev.abysslib.storage.id.util;

import lombok.SneakyThrows;
import net.abyssdev.abysslib.storage.id.Id;
import net.abyssdev.abysslib.storage.id.exception.IdNotFoundException;

import java.lang.reflect.Field;

public final class IdUtils {

    @SneakyThrows
    public static Object getId(final Class<?> type, final Object instance) {

        for (final Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Id.class)) {
                continue;
            }

            return field.get(instance);
        }

        throw new IdNotFoundException();
    }

    @SneakyThrows
    public static String getIdFieldName(final Class<?> type) {

        for (final Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Id.class)) {
                continue;
            }

            return field.getName();
        }

        throw new IdNotFoundException();
    }

    @SneakyThrows
    public static Class<?> getIdClass(final Class<?> type) {

        for (final Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Id.class)) {
                continue;
            }

            return field.getDeclaringClass();
        }

        throw new IdNotFoundException();

    }

}
