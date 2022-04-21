package net.abyssdev.abysslib.storage.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.experimental.filters.Filters;
import lombok.Data;
import net.abyssdev.abysslib.storage.ConstructableValue;
import net.abyssdev.abysslib.storage.Storage;
import net.abyssdev.abysslib.storage.id.util.IdUtils;
import net.abyssdev.abysslib.storage.patterns.registry.Registry;
import net.abyssdev.abysslib.storage.registry.DefaultCacheRegistry;
import org.bson.UuidRepresentation;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public abstract class MongoStorage<K, V> implements Storage<K, V>, ConstructableValue<K, V> {

    private final Registry<K, V> cache = new DefaultCacheRegistry<>();

    private final Datastore datastore;
    private final Class<V> valueClass;
    private final Class<K> keyClass;
    private final String idFieldName;

    public MongoStorage(final String uri, final String databaseName, final Class<? extends Plugin> mainClass, final Class<V> valueClass) {

        this.valueClass = valueClass;
        this.idFieldName = IdUtils.getIdFieldName(this.valueClass);
        this.keyClass = (Class<K>) IdUtils.getIdClass(this.valueClass);

        this.datastore = Morphia.createDatastore(
                MongoClients.create(
                        MongoClientSettings.builder()
                                .applyConnectionString(new ConnectionString(uri))
                                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                                .build()
                ),
                databaseName,
                MapperOptions.builder()
                        .storeNulls(true)
                        .storeEmpties(true)
                        .classLoader(mainClass.getClassLoader())
                        .build()

        );

        this.datastore.getMapper().map(valueClass);

    }

    @Override
    public Registry<K, V> cache() {
        return this.cache;
    }

    @Override
    public V get(final K key) {
        final Optional<V> cached = this.cache.get(key);

        if (cached.isPresent()) {
            return cached.get();
        }

        final V fromDB = this.datastore.find(this.valueClass).filter(Filters.eq(this.idFieldName, key)).first();

        if (fromDB == null) {
            final V value = this.constructValue(key);
            this.cache.register(key, value);
            this.save(value);
            return value;
        }

        this.cache.register(key, fromDB);

        return fromDB;
    }

    @Override
    public void save(final V value) {
        this.datastore.save(value);
    }

    @Override
    public void saveAll(final Collection<V> values) {
        this.datastore.save(new ArrayList<>(values));
    }

    @Override
    public void remove(final K key) {
        this.datastore.delete(this.get(key));
    }

    //Not used
    @Override
    public void write() {
    }

    @Override
    public boolean contains(final K key) {
        return this.datastore.find(this.valueClass).filter(Filters.eq(this.idFieldName, key)).first() != null;
    }

    @Override
    public Collection<K> allKeys() {
        return this.datastore.find(this.keyClass).stream().collect(Collectors.toSet());
    }

    @Override
    public Collection<V> allValues() {
        return this.datastore.find(this.valueClass).stream().collect(Collectors.toSet());
    }
}
