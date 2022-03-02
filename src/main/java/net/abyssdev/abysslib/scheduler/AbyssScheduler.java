package net.abyssdev.abysslib.scheduler;

import net.abyssdev.abysslib.scheduler.impl.AsyncScheduler;
import net.abyssdev.abysslib.scheduler.impl.SyncScheduler;
import org.bukkit.scheduler.BukkitTask;

public interface AbyssScheduler {

    static AbyssScheduler async() {
        return new AsyncScheduler();
    }

    static AbyssScheduler sync() {
        return new SyncScheduler();
    }

    void run(Runnable r);

    BukkitTask runLater(Runnable r, long l);

    BukkitTask runRepeating(Runnable r, long l);

}