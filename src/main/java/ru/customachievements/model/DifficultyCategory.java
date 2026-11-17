package ru.customachievements.model;

import org.bukkit.Material;

public enum DifficultyCategory {
    EASY("Лёгкие", "<green>", Material.LIME_CONCRETE, 10),
    SIMPLE("Простые", "<aqua>", Material.LIGHT_BLUE_CONCRETE, 12),
    MEDIUM("Средние", "<yellow>", Material.YELLOW_CONCRETE, 14),
    HARD("Сложные", "<red>", Material.RED_CONCRETE, 16),
    IMPOSSIBLE("Невозможные", "<dark_purple>", Material.PURPLE_CONCRETE, 22);

    private final String displayName;
    private final String colorTag;
    private final Material icon;
    private final int mainMenuSlot;

    DifficultyCategory(String displayName, String colorTag, Material icon, int mainMenuSlot) {
        this.displayName = displayName;
        this.colorTag = colorTag;
        this.icon = icon;
        this.mainMenuSlot = mainMenuSlot;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorTag() {
        return colorTag;
    }

    public Material getIcon() {
        return icon;
    }

    public int getMainMenuSlot() {
        return mainMenuSlot;
    }

    public static DifficultyCategory fromString(String value) {
        if (value == null) return EASY;
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return EASY;
        }
    }
}
