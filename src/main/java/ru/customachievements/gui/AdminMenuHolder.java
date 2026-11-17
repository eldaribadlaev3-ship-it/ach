package ru.customachievements.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.customachievements.model.DifficultyCategory;

public class AdminMenuHolder implements InventoryHolder {

    public enum AdminView {
        MAIN,
        CATEGORY_LIST,
        EDIT_ACHIEVEMENT,
        CREATE_STEP_CATEGORY,
        CREATE_STEP_GOAL_TYPE,
        CREATE_FINAL_REVIEW
    }

    private final AdminView view;
    private final DifficultyCategory category;
    private final int page;
    private final String editingAchievementId;
    private Inventory inventory;

    public AdminMenuHolder(AdminView view, DifficultyCategory category, int page, String editingAchievementId) {
        this.view = view;
        this.category = category;
        this.page = page;
        this.editingAchievementId = editingAchievementId;
    }

    @Override
    public @NotNull Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public AdminView getView() { return view; }
    public DifficultyCategory getCategory() { return category; }
    public int getPage() { return page; }
    public String getEditingAchievementId() { return editingAchievementId; }
}
