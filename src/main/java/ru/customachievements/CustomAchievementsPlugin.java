package ru.customachievements;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.customachievements.command.AchievementCommand;
import ru.customachievements.gui.AdminGuiListener;
import ru.customachievements.gui.AdminGuiManager;
import ru.customachievements.gui.PlayerGuiListener;
import ru.customachievements.gui.PlayerGuiManager;
import ru.customachievements.listener.AchievementProgressListener;
import ru.customachievements.manager.AchievementManager;
import ru.customachievements.manager.PlayerDataManager;
import ru.customachievements.manager.VaultHook;

public final class CustomAchievementsPlugin extends JavaPlugin {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private VaultHook vaultHook;
    private AchievementManager achievementManager;
    private PlayerDataManager playerDataManager;
    private PlayerGuiManager playerGuiManager;
    private AdminGuiManager adminGuiManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists() && !getDataFolder().mkdirs()) {
            getLogger().warning("Не удалось создать папку плагина!");
        }

        this.vaultHook = new VaultHook(this);
        if (!vaultHook.setupEconomy()) {
            getLogger().severe("Vault или плагин экономики не найден! Отключение плагина...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.achievementManager = new AchievementManager(this);
        this.achievementManager.loadAchievements();

        this.playerDataManager = new PlayerDataManager(this);
        this.playerGuiManager = new PlayerGuiManager(this);
        this.adminGuiManager = new AdminGuiManager(this);

        Bukkit.getOnlinePlayers().forEach(player -> playerDataManager.loadPlayer(player.getUniqueId()));

        var cmd = getCommand("achievements");
        if (cmd != null) {
            AchievementCommand executor = new AchievementCommand(this);
            cmd.setExecutor(executor);
            cmd.setTabCompleter(executor);
        }

        var pm = getServer().getPluginManager();
        pm.registerEvents(new AchievementProgressListener(this), this);
        pm.registerEvents(new PlayerGuiListener(this), this);
        pm.registerEvents(new AdminGuiListener(this), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> playerDataManager.saveAllOnline(), 6000L, 6000L);

        getLogger().info("CustomAchievements успешно запущен! Загружено достижений: " + achievementManager.getAllAchievements().size());
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.saveAllSync();
        }
        getLogger().info("CustomAchievements отключен, данные игроков сохранены.");
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }

    public AchievementManager getAchievementManager() {
        return achievementManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public PlayerGuiManager getPlayerGuiManager() {
        return playerGuiManager;
    }

    public AdminGuiManager getAdminGuiManager() {
        return adminGuiManager;
    }
}
