package net.abyssdev.abysslib.scheduler.impl;

import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.scheduler.AbyssScheduler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.CompletableFuture;

public class AsyncScheduler implements AbyssScheduler {

    @Override
    public void run(Runnable r) {
        CompletableFuture.runAsync(r);
    }

    @Override
    public BukkitTask runLater(Runnable r, long l) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        }.runTaskLaterAsynchronously(AbyssPlugin.getInstance(), l);
    }

    @Override
    public BukkitTask runRepeating(Runnable r, long l) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        }.runTaskTimerAsynchronously(AbyssPlugin.getInstance(), 0, l);
    }

}
