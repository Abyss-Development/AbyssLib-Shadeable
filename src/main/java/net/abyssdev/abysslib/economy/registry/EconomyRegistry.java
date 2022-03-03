package net.abyssdev.abysslib.economy.registry;

import net.abyssdev.abysslib.economy.provider.Economy;

public interface EconomyRegistry {

    void addEconomy(Economy provider);

    boolean hasEconomy(String id);

    Economy getEconomy(String id);

}
