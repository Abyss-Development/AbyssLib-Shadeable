package net.abyssdev.abysslib.economy.provider.impl;

import net.abyssdev.abysslib.economy.provider.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy implements Economy {

    private final boolean isValid;
    private net.milkbowl.vault.economy.Economy economy;

    public VaultEconomy() {
        this.isValid = setupEconomy();
    }

    @Override
    public String getName() {
        return "Vault";
    }

    @Override
    public double getBalance(Player player) {
        if (this.isValid) {
            return this.economy.getBalance(player);
        }
        return 0;
    }

    @Override
    public void addBalance(Player player, double amount) {
        if (this.isValid) {
            this.economy.depositPlayer(player, amount);
        }
    }

    @Override
    public void withdrawBalance(Player player, double amount) {
        if (this.isValid) {
            this.economy.withdrawPlayer(player, amount);
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (rsp == null) {
            return false;
        }

        this.economy = rsp.getProvider();
        return true;
    }
}
