package net.abyssdev.abysslib.storage.json.service;

import net.abyssdev.abysslib.storage.patterns.service.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UnsortedCacheService<S> implements Service<S> {

    private final Set<S> service = new HashSet<>();

    @Override
    public Collection<S> getService() {
        return this.service;
    }


}
