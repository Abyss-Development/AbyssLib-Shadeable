package net.abyssdev.abysslib.economy.registry.impl;


import net.abyssdev.abysslib.economy.provider.Economy;
import net.abyssdev.abysslib.economy.provider.impl.ExperienceEconomy;
import net.abyssdev.abysslib.economy.provider.impl.VaultEconomy;
import net.abyssdev.abysslib.economy.registry.EconomyRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DefaultEconomyRegistry implements EconomyRegistry {

    private final Map<String, Economy> economyProviderMap;

    public DefaultEconomyRegistry() {
        this.economyProviderMap = new HashMap<>();
        this.registerDefaults();

        DefaultEconomyRegistry.INSTANCE = this;
    }

    private void registerDefaults() {
        Arrays.asList(
                new VaultEconomy(),
                new ExperienceEconomy()
        ).forEach(economy -> this.economyProviderMap.put(economy.getName().toLowerCase(), economy));
    }

    @Override
    public boolean hasEconomy(String id) {
        return economyProviderMap.containsKey(id.toLowerCase());
    }

    @Override
    public Economy getEconomy(String id) {
        return economyProviderMap.get(id.toLowerCase());
    }

    @Override
    public void addEconomy(Economy economy) {
        economyProviderMap.put(economy.getName().toLowerCase(), economy);
    }

    private static DefaultEconomyRegistry INSTANCE;

    public static DefaultEconomyRegistry get() {
        return DefaultEconomyRegistry.INSTANCE;
    }

}
