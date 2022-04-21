package net.abyssdev.abysslib.storage;

public interface ConstructableValue<K, V> {

    default V constructValue(final K key) {
        return null;
    }

}
