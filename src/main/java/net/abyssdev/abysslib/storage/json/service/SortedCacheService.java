package net.abyssdev.abysslib.storage.json.service;

import net.abyssdev.abysslib.storage.patterns.service.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class SortedCacheService<S> implements Service<S> {

    private final List<S> service = new LinkedList<>();

    @Override
    public Collection<S> getService() {
        return this.service;
    }

}
