package net.abyssdev.abysslib.storage.redis;

import net.abyssdev.abysslib.storage.patterns.registry.Registry;
import net.abyssdev.abysslib.storage.redis.data.DataSet;
import net.abyssdev.abysslib.storage.redis.packet.Packet;
import net.abyssdev.abysslib.storage.registry.DefaultCacheRegistry;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.function.Consumer;

public final class Redis {

    private final Registry<String, Consumer<Packet>> handlers = new DefaultCacheRegistry<>();

    private final Jedis jedis;
    private final String channel;

    public Redis(
            final int port,
            @NotNull final String ip,
            @NotNull final String channel,
            @NotNull final String user,
            @NotNull final String password) {

        this.channel = channel;
        this.jedis = new Jedis(ip, port);
        this.jedis.auth(user, password);

        final JedisPubSub pubSub = new JedisPubSub() {

            @Override
            public void onPMessage(final String pattern, final String channel, final String message) {
                Redis.this.receive(Packet.fromJSON(message));
            }

        };

        this.jedis.psubscribe(pubSub, channel);


    }

    public void register(@NotNull final String header, @NotNull final Consumer<Packet> consumer) {
        this.handlers.register(header, consumer);
    }

    private void receive(@NotNull final Packet packet) {
        this.handlers.get(packet.getHead()).ifPresent(consumer -> consumer.accept(packet));
    }

    public void publish(@NotNull final String head, @NotNull final DataSet... body) {
        this.jedis.publish(this.channel, new Packet(head, body).toJSON());
    }


}
