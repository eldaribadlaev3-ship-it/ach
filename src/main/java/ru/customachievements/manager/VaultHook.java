package ru.customachievements.manager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.customachievements.CustomAchievementsPlugin;

public class VaultHook {

    private final CustomAchievementsPlugin plugin;
    private Economy economy;

    public VaultHook(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public boolean deposit(OfflinePlayer player, double amount) {
        if (economy == null || amount <= 0) return false;
        return economy.depositPlayer(player, amount).transactionSuccess();
    }

    public String format(double amount) {
        if (economy == null) {
            return String.format("%.0f", amount);
        }
        return economy.format(amount);
    }
}
