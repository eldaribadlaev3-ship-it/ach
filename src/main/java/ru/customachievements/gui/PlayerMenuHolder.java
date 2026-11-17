package ru.customachievements.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.customachievements.model.DifficultyCategory;

public class PlayerMenuHolder implements InventoryHolder {

    public enum ViewType {
        MAIN_CATEGORIES,
        CATEGORY_LIST
    }

    private final ViewType viewType;
    private final DifficultyCategory category;
    private final int page;
    private Inventory inventory;

    public PlayerMenuHolder(ViewType viewType, DifficultyCategory category, int page) {
        this.viewType = viewType;
        this.category = category;
        this.page = page;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ViewType getViewType() { return viewType; }
    public DifficultyCategory getCategory() { return category; }
    public int getPage() { return page; }
}
