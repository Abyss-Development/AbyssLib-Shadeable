package net.abyssdev.abysslib.storage;

import net.abyssdev.abysslib.storage.patterns.service.Service;

import java.util.Collection;

public interface SingleKeyedStorage<S> {

    Service<S> cache();

    void save(final S value);
    void saveAll(final Collection<S> values);

    void remove(final S key);

    void write();

    boolean contains(final S key);

}
