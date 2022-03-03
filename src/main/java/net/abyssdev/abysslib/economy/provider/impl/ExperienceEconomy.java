package net.abyssdev.abysslib.economy.provider.impl;

import net.abyssdev.abysslib.economy.provider.EconomyProvider;
import net.abyssdev.abysslib.utils.ExperienceUtils;
import org.bukkit.entity.Player;

public class ExperienceEconomy extends EconomyProvider {

    public ExperienceEconomy() {
        super("Experience");
    }

    @Override
    public double getBalance(Player player) {
        return ExperienceUtils.getTotalExperience(player);
    }

    @Override
    public void addBalance(Player player, double amount) {
        ExperienceUtils.setTotalExperience(player, (int) (getBalance(player) + amount));
    }

    @Override
    public void withdrawBalance(Player player, double amount) {
        ExperienceUtils.setTotalExperience(player, (int) (getBalance(player) - amount));
    }
}
