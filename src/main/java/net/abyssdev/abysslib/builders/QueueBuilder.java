package net.abyssdev.abysslib.builders;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

/**
 * @author Chubbyduck1
 * @param <T>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class QueueBuilder<T> extends BukkitRunnable {

    private Consumer<T> consumer;
    private Consumer<QueueBuilder<T>> onceEmptyConsumer, onceIntervalFinishedConsumer;

    private boolean calledEmptyConsumer = false;

    private int interval = 1;
    private int maxPerInterval = 1;

    private boolean doRandom = false;
    private boolean runAsync = false;

    private ConcurrentLinkedDeque<T> queued;

    public QueueBuilder() {
        this.queued = new ConcurrentLinkedDeque<>();
    }

    /**
     * Add something to the queue
     *
     * @param toQueue The thing to add
     * @return this
     */
    public QueueBuilder<T> addQueued(T toQueue) {
        this.queued.offer(toQueue);
        return this;
    }

    @SafeVarargs
    public final QueueBuilder<T> addQueued(T... toQueue) {
        this.queued.addAll(Arrays.asList(toQueue));
        return this;
    }

    public QueueBuilder<T> addQueued(List<T> toQueue) {
        this.queued.addAll(toQueue);
        return this;
    }

    public QueueBuilder<T> addPriority(T toQueue) {
        this.queued.addFirst(toQueue);
        return this;
    }

    /**
     * Remove something from the queue
     *
     * @param toRemove The thing to remove
     * @return this
     */
    public QueueBuilder<T> removeQueued(T toRemove) {
        this.queued.remove(toRemove);
        return this;
    }

    /**
     * Check the queue position of something
     *
     * @param toCheck The thing to check
     * @return The position
     */
    public int getPlace(T toCheck) {
        return new ArrayList<>(queued).indexOf(toCheck);
    }

    /**
     * Clears the queue
     *
     * @return this
     */
    public QueueBuilder<T> clearQueue() {
        this.queued.clear();
        return this;
    }

    /**
     * The size of the queue
     *
     * @return this
     */
    public int getSize() {
        return queued.size();
    }

    /**
     * Start the queue
     *
     * @param javaPlugin The plugin
     * @return this
     */
    public QueueBuilder<T> start(JavaPlugin javaPlugin) {
        if (runAsync) {
            runTaskTimerAsynchronously(javaPlugin, interval, interval);
            return this;
        }

        runTaskTimer(javaPlugin, interval, interval);
        return this;
    }


    /**
     * Stop the queue
     */
    public void stop() {
        this.cancel();
    }

    /**
     * Run the tick
     */
    public void next() {
        if(doRandom) {
            List<T> list = new ArrayList<>(queued);
            Collections.shuffle(list);

            queued = new ConcurrentLinkedDeque<>(list);
            
        }

        for (int i = 0; i < maxPerInterval + 1; i++) {
            T toQueue = queued.poll();

            if (toQueue != null) {
                consumer.accept(toQueue);
                continue;
            }

            if (onceEmptyConsumer != null && !calledEmptyConsumer) {
                onceEmptyConsumer.accept(this);
                calledEmptyConsumer = true;
                break;
            }
        }

        if (onceIntervalFinishedConsumer == null) {
            return;
        }
        onceIntervalFinishedConsumer.accept(this);
    }

    /**
     * Run the tick
     */
    @Override
    public void run() {
        next();
    }
}