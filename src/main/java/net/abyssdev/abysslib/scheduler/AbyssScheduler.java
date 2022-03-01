package net.abyssdev.abysslib.scheduler;

import net.abyssdev.abysslib.scheduler.impl.AsyncAbyssScheduler;
import net.abyssdev.abysslib.scheduler.impl.SyncAbyssScheduler;
import org.bukkit.scheduler.BukkitTask;

public interface AbyssScheduler {

    static AbyssScheduler async() {
        return new AsyncAbyssScheduler();
    }

    static AbyssScheduler sync() {
        return new SyncAbyssScheduler();
    }

    void run(Runnable r);

    BukkitTask runLater(Runnable r, long l);

    BukkitTask runRepeating(Runnable r, long l);

}