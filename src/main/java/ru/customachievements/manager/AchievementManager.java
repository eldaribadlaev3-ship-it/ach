package ru.customachievements.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.data.DefaultAchievementsGenerator;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.DifficultyCategory;
import ru.customachievements.model.GoalType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AchievementManager {

    private final CustomAchievementsPlugin plugin;
    private final File file;
    private final Map<String, Achievement> achievements = new LinkedHashMap<>();

    public AchievementManager(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "achievements.yml");
    }

    public void loadAchievements() {
        achievements.clear();
        if (!file.exists()) {
            plugin.getLogger().info("Файл achievements.yml не найден. Генерация 200 базовых достижений...");
            List<Achievement> defaults = DefaultAchievementsGenerator.generateAll200();
            for (Achievement ach : defaults) {
                achievements.put(ach.getId(), ach);
            }
            saveAchievements();
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection root = config.getConfigurationSection("achievements");
        if (root == null) {
            plugin.getLogger().warning("Секция 'achievements' в achievements.yml пуста!");
            return;
        }

        for (String id : root.getKeys(false)) {
            ConfigurationSection sec = root.getConfigurationSection(id);
            if (sec == null) continue;

            String name = sec.getString("name", id);
            String description = sec.getString("description", "");
            DifficultyCategory category = DifficultyCategory.fromString(sec.getString("category", "EASY"));
            GoalType goalType = GoalType.fromString(sec.getString("goal-type", "BREAK_BLOCK"));

            Material targetMaterial = null;
            if (sec.contains("target-material")) {
                targetMaterial = Material.matchMaterial(sec.getString("target-material", ""));
            }

            EntityType targetEntity = null;
            if (sec.contains("target-entity")) {
                try {
                    targetEntity = EntityType.valueOf(sec.getString("target-entity", "").toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }
            }

            int targetAmount = sec.getInt("target-amount", 1);
            double reward = sec.getDouble("reward", 100.0);
            Material icon = Material.matchMaterial(sec.getString("icon", "PAPER"));
            if (icon == null || !icon.isItem()) {
                icon = Material.PAPER;
            }

            Achievement achievement = new Achievement(
                    id, name, description, category, goalType,
                    targetMaterial, targetEntity, targetAmount, reward, icon
            );
            achievements.put(id, achievement);
        }
    }

    public void saveAchievements() {
        YamlConfiguration config = new YamlConfiguration();
        for (Achievement ach : achievements.values()) {
            String path = "achievements." + ach.getId();
            config.set(path + ".name", ach.getName());
            config.set(path + ".description", ach.getDescription());
            config.set(path + ".category", ach.getCategory().name());
            config.set(path + ".goal-type", ach.getGoalType().name());
            if (ach.getTargetMaterial() != null) {
                config.set(path + ".target-material", ach.getTargetMaterial().name());
            }
            if (ach.getTargetEntity() != null) {
                config.set(path + ".target-entity", ach.getTargetEntity().name());
            }
            config.set(path + ".target-amount", ach.getTargetAmount());
            config.set(path + ".reward", ach.getReward());
            config.set(path + ".icon", ach.getIcon().name());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Ошибка сохранения achievements.yml: " + e.getMessage());
        }
    }

    public void addAchievement(Achievement achievement) {
        achievements.put(achievement.getId(), achievement);
        saveAchievements();
    }

    public void removeAchievement(String id) {
        if (achievements.remove(id) != null) {
            saveAchievements();
        }
    }

    public Achievement getAchievement(String id) {
        return achievements.get(id);
    }

    public Collection<Achievement> getAllAchievements() {
        return Collections.unmodifiableCollection(achievements.values());
    }

    public List<Achievement> getByCategory(DifficultyCategory category) {
        return achievements.values().stream()
                .filter(a -> a.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Achievement> getByGoalType(GoalType type) {
        List<Achievement> result = new ArrayList<>();
        for (Achievement ach : achievements.values()) {
            if (ach.getGoalType() == type) {
                result.add(ach);
            }
        }
        return result;
    }

    public String generateUniqueId(DifficultyCategory category) {
        int counter = 1;
        String prefix = category.name().toLowerCase() + "_custom_";
        while (achievements.containsKey(prefix + counter)) {
            counter++;
        }
        return prefix + counter;
    }
}
