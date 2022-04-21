package net.abyssdev.abysslib.storage.redis.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public final class DataSet<V extends Serializable> {

    private final String key;
    private final V value;

}