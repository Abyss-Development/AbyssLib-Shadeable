package net.abyssdev.abysslib.storage;

import net.abyssdev.abysslib.storage.patterns.registry.Registry;

import java.util.Collection;

public interface Storage<K, V> {

    Registry<K, V> cache();

    V get(final K key);

    void save(final V value);
    void saveAll(final Collection<V> values);

    void remove(final K key);

    void write();

    boolean contains(final K key);

    Collection<V> allValues();
    Collection<K> allKeys();

}
