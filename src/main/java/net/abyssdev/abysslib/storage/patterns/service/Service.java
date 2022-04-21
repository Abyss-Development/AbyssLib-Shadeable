package net.abyssdev.abysslib.storage.patterns.service;

import java.util.*;
import java.util.function.Consumer;

public interface Service<S> {

    Collection<S> getService();

    default void add(final S s) {
        this.getService().add(s);
    }

    default void remove(final S s) {
        this.getService().remove(s);
    }

    default S poll() {
        return this.asQueue().poll();
    }

    default S peek() {
        return this.asQueue().peek();
    }

    default boolean contains(final S s) {
        return this.getService().contains(s);
    }

    default void clear() {
        this.getService().clear();
    }

    default void addAll(final Collection<S> collection) {
        this.getService().addAll(collection);
    }

    default void iterate(final Consumer<S> consumer) {
        for (final S s : this.getService()) {
            consumer.accept(s);
        }
    }

    default S get(final int index) {
        return this.asList().get(index);
    }

    default List<S> asList() {
        return (List<S>) this.getService();
    }

    default Queue<S> asQueue() {
        return (Queue<S>) this.getService();
    }

}
