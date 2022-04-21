package net.abyssdev.abysslib.storage.registry;

import net.abyssdev.abysslib.storage.patterns.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public final class DefaultCacheRegistry<K, V> implements Registry<K, V> {

    private final Map<K, V> registry = new HashMap<>();

    @Override
    public Map<K, V> getRegistry() {
        return this.registry;
    }

}
