package net.abyssdev.abysslib.scheduler.impl;

import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.scheduler.AbyssScheduler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SyncScheduler implements AbyssScheduler {

    @Override
    public void run(Runnable r) {
        new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        }.runTask(AbyssPlugin.getInstance());
    }

    @Override
    public BukkitTask runLater(Runnable r, long l) {
       return new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        }.runTaskLater(AbyssPlugin.getInstance(), l);
    }

    @Override
    public BukkitTask runRepeating(Runnable r, long l) {
       return new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        }.runTaskTimer(AbyssPlugin.getInstance(), 0, l);
    }

}
