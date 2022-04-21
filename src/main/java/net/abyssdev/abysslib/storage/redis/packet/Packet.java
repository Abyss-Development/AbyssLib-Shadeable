package net.abyssdev.abysslib.storage.redis.packet;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.abyssdev.abysslib.storage.redis.data.DataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
public final class Packet {

    private static final Gson GSON = new Gson();

    private final String head;
    private final DataSet<?>[] body;

    @NotNull
    public static Packet fromJSON(@NotNull final String json) {
        return Packet.GSON.fromJson(json, Packet.class);
    }

    @NotNull
    public String toJSON() {
        return Packet.GSON.toJson(this);
    }

    @Nullable
    public DataSet<?> getData(@NotNull final String key) {

        for (@NotNull final DataSet<?> data : this.body) {
            if (!data.getKey().equals(key)) {
                continue;
            }

            return data;
        }

        return null;
    }


}