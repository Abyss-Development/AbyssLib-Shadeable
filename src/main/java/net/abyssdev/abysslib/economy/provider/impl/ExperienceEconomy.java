package net.abyssdev.abysslib.economy.provider.impl;

import net.abyssdev.abysslib.economy.provider.Economy;
import net.abyssdev.abysslib.utils.ExperienceUtils;
import org.bukkit.entity.Player;

public class ExperienceEconomy implements Economy {

    @Override
    public String getName() {
        return "Experience";
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
