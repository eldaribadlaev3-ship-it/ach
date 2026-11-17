package ru.customachievements.manager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.PlayerAchievementData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    private final CustomAchievementsPlugin plugin;
    private final File playerDataFolder;
    private final Map<UUID, PlayerAchievementData> cache = new ConcurrentHashMap<>();

    public PlayerDataManager(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public PlayerAchievementData getPlayerData(UUID uuid) {
        return cache.computeIfAbsent(uuid, this::loadFromFile);
    }

    public void loadPlayer(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PlayerAchievementData data = loadFromFile(uuid);
            cache.put(uuid, data);
        });
    }

    private PlayerAchievementData loadFromFile(UUID uuid) {
        PlayerAchievementData data = new PlayerAchievementData(uuid);
        File file = new File(playerDataFolder, uuid.toString() + ".yml");
        if (!file.exists()) {
            return data;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection progSec = config.getConfigurationSection("progress");
        if (progSec != null) {
            for (String key : progSec.getKeys(false)) {
                data.setProgress(key, progSec.getInt(key, 0));
            }
        }

        for (String comp : config.getStringList("completed")) {
            data.setCompleted(comp, true);
        }

        for (String claimed : config.getStringList("claimed")) {
            data.setClaimed(claimed, true);
        }

        data.setDirty(false);
        return data;
    }

    public void savePlayerAsync(UUID uuid, boolean removeFromCache) {
        PlayerAchievementData data = cache.get(uuid);
        if (data == null) return;
        if (removeFromCache) {
            cache.remove(uuid);
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> writeToFile(data));
    }

    public void saveAllOnline() {
        for (PlayerAchievementData data : cache.values()) {
            if (data.isDirty()) {
                writeToFile(data);
            }
        }
    }

    public void saveAllSync() {
        for (PlayerAchievementData data : cache.values()) {
            writeToFile(data);
        }
    }

    private synchronized void writeToFile(PlayerAchievementData data) {
        File file = new File(playerDataFolder, data.getPlayerUuid().toString() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        for (Map.Entry<String, Integer> entry : data.getProgressMap().entrySet()) {
            config.set("progress." + entry.getKey(), entry.getValue());
        }
        config.set("completed", new ArrayList<>(data.getCompletedIds()));
        config.set("claimed", new ArrayList<>(data.getClaimedIds()));

        try {
            config.save(file);
            data.setDirty(false);
        } catch (IOException e) {
            plugin.getLogger().severe("Не удалось сохранить прогресс игрока " + data.getPlayerUuid() + ": " + e.getMessage());
        }
    }

    public void addProgress(Player player, Achievement achievement, int amount) {
        if (amount <= 0) return;
        PlayerAchievementData data = getPlayerData(player.getUniqueId());
        if (data.isCompleted(achievement.getId())) {
            return;
        }

        int current = data.getProgress(achievement.getId());
        int updated = Math.min(achievement.getTargetAmount(), current + amount);
        data.setProgress(achievement.getId(), updated);

        if (updated >= achievement.getTargetAmount()) {
            completeAchievement(player, data, achievement);
        }
    }

    public void setMaxProgress(Player player, Achievement achievement, int value) {
        PlayerAchievementData data = getPlayerData(player.getUniqueId());
        if (data.isCompleted(achievement.getId())) {
            return;
        }

        int current = data.getProgress(achievement.getId());
        if (value <= current) return;

        int updated = Math.min(achievement.getTargetAmount(), value);
        data.setProgress(achievement.getId(), updated);

        if (updated >= achievement.getTargetAmount()) {
            completeAchievement(player, data, achievement);
        }
    }

    private void completeAchievement(Player player, PlayerAchievementData data, Achievement achievement) {
        data.setCompleted(achievement.getId(), true);
        String formattedReward = plugin.getVaultHook().format(achievement.getReward());

        String msg = "<gradient:#FFD700:#FFA500><bold>★ ДОСТИЖЕНИЕ ВЫПОЛНЕНО!</bold></gradient> "
                + "<gray>Вы выполнили достижение </gray>"
                + achievement.getCategory().getColorTag() + "<bold>[" + achievement.getName() + "]</bold></color>"
                + "<gray>! Откройте </gray><yellow><click:run_command:'/ach'><hover:show_text:'<green>Нажмите, чтобы открыть меню'>/ach</hover></click></yellow>"
                + "<gray>, чтобы забрать награду: </gray><gold><bold>" + formattedReward + "</bold></gold>";

        player.sendMessage(plugin.getMiniMessage().deserialize(msg));
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.85f, 1.15f);
    }

    public List<Achievement> getAchievementsByCategory(ru.customachievements.model.DifficultyCategory category) {
        return plugin.getAchievementManager().getByCategory(category);
    }
}
