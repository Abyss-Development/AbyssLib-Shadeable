package net.abyssdev.abysslib.storage.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import net.abyssdev.abysslib.storage.ConstructableValue;
import net.abyssdev.abysslib.storage.Storage;
import net.abyssdev.abysslib.storage.id.util.IdUtils;
import net.abyssdev.abysslib.storage.json.registry.JsonCacheRegistry;
import net.abyssdev.abysslib.storage.patterns.registry.Registry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Collection;

public abstract class JsonStorage<K, V> implements Storage<K, V>, ConstructableValue<K, V> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //cannot do liskov custom shit in this class
    private final JsonCacheRegistry<K, V> cache = new JsonCacheRegistry<>();
    private final File file;
    private final Class<V> valueClass;

    @SneakyThrows
    public JsonStorage(final File file, final Class<V> valueClass) {
        this.file = file;
        this.valueClass = valueClass;

        final V[] values = (V[]) JsonStorage.GSON.fromJson(new FileReader(this.file), Array.newInstance(valueClass, 0).getClass());

        for (final V value : values) {
            this.cache.register((K) IdUtils.getId(valueClass, value), value);
        }
    }


    @Override
    public Registry<K, V> cache() {
        return this.cache;
    }

    @Override
    public V get(final K key) {
        return this.cache.get(key).orElseGet(() -> {
            final V value = this.constructValue(key);

            if (value == null) {
                return null;
            }

            this.save(value);
            return value;
        });
    }

    @Override
    public void save(final V value) {
        this.cache.register((K) IdUtils.getId(this.valueClass, value), value);
    }

    @Override
    public void saveAll(final Collection<V> values) {
        for (final V value : values) {
            this.cache.register((K) IdUtils.getId(this.valueClass, value), value);
        }
    }

    @Override
    public void remove(final K key) {
        this.cache.unregister(key);
    }

    @SneakyThrows
    public void write() {
        //Clear the file
        final Writer writer = new FileWriter(this.file);

        JsonStorage.GSON.toJson(this.cache.values(), writer);
        writer.close();
    }

    @Override
    public boolean contains(final K key) {
        return this.cache.containsKey(key);
    }

    @Override
    public Collection<K> allKeys() {
        return this.cache.keySet();
    }

    @Override
    public Collection<V> allValues() {
        return this.cache.values();
    }
}
