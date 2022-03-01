package net.abyssdev.abysslib.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ImmutablePair<A, B> {

    private final A key;
    private final B value;

}