package net.abyssdev.abysslib.economy.provider;

import org.bukkit.entity.Player;

public interface Economy {

    String getName();
    double getBalance(Player player);
    void addBalance(Player player, double amount);
    void withdrawBalance(Player player, double amount);

}
