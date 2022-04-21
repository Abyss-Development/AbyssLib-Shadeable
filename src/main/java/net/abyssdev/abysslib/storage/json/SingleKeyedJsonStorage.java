package net.abyssdev.abysslib.storage.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import net.abyssdev.abysslib.storage.SingleKeyedStorage;
import net.abyssdev.abysslib.storage.json.service.UnsortedCacheService;
import net.abyssdev.abysslib.storage.patterns.service.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

//Note: DO NOT USE THE VARIABLE, USE THE METHOD cache(). THIS IS NEEDED FOR IF YOU WANT THE STORAGE SORTED
public abstract class SingleKeyedJsonStorage<S> implements SingleKeyedStorage<S> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Service<S> cache = new UnsortedCacheService<>();
    private final File file;
    
    @SneakyThrows
    public SingleKeyedJsonStorage(final File file, final Class<S> valueClass) {
        this.file = file;

        final S[] values = (S[]) SingleKeyedJsonStorage.GSON.fromJson(new FileReader(this.file), Array.newInstance(valueClass, 0).getClass());

        this.cache.addAll(Arrays.asList(values));
    }

    @Override
    public void save(final S value) {
        this.cache().add(value);
    }

    @Override
    public void saveAll(final Collection<S> values) {
        this.cache().addAll(values);
    }

    @Override
    public void remove(final S value) {
        this.cache().remove(value);
    }

    @Override
    @SneakyThrows
    public void write() {
        final Writer writer = new FileWriter(this.file);

        SingleKeyedJsonStorage.GSON.toJson(this.cache().getService(), writer);
        writer.close();
    }

    @Override
    public Service<S> cache() {
        return this.cache;
    }

    @Override
    public boolean contains(final S value) {
        return this.cache().contains(value);
    }
}
