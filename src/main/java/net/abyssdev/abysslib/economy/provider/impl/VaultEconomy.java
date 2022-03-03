package net.abyssdev.abysslib.economy.provider.impl;

import net.abyssdev.abysslib.economy.provider.EconomyProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy extends EconomyProvider {

    private final boolean isValid;
    private Economy economy;

    public VaultEconomy() {
        super("Vault");

        isValid = setupEconomy();
    }

    @Override
    public double getBalance(Player player) {
        if (isValid) {
            return economy.getBalance(player);
        }
        return 0;
    }

    @Override
    public void addBalance(Player player, double amount) {
        if (isValid) {
            economy.depositPlayer(player, amount);
        }
    }

    @Override
    public void withdrawBalance(Player player, double amount) {
        if (isValid) {
            economy.withdrawPlayer(player, amount);
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
