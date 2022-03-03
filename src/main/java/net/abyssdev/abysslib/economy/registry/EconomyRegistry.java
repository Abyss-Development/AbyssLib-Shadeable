package net.abyssdev.abysslib.economy.registry;

import net.abyssdev.abysslib.economy.provider.EconomyProvider;

public interface EconomyRegistry {

    void addEconomy(EconomyProvider provider);

    boolean hasEconomy(String id);

    EconomyProvider getEconomy(String id);

}
