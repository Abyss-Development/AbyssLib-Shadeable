package net.abyssdev.abysslib.economy.provider;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public abstract class EconomyProvider {

    private final String name;

    public EconomyProvider(String name) {
        this.name = name;
    }

    public abstract double getBalance(Player player);
    public abstract void addBalance(Player player, double amount);
    public abstract void withdrawBalance(Player player, double amount);

}
