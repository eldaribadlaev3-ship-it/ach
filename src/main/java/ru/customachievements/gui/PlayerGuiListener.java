package ru.customachievements.gui;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.DifficultyCategory;
import ru.customachievements.model.PlayerAchievementData;

public class PlayerGuiListener implements Listener {

    private final CustomAchievementsPlugin plugin;

    public PlayerGuiListener(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof PlayerMenuHolder holder)) return;
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        PlayerGuiManager gui = plugin.getPlayerGuiManager();

        if (holder.getViewType() == PlayerMenuHolder.ViewType.MAIN_CATEGORIES) {
            String catName = clicked.getItemMeta().getPersistentDataContainer().get(gui.getCategoryKey(), PersistentDataType.STRING);
            if (catName != null) {
                DifficultyCategory category = DifficultyCategory.fromString(catName);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
                gui.openCategoryMenu(player, category, 0);
            }
            return;
        }

        if (holder.getViewType() == PlayerMenuHolder.ViewType.CATEGORY_LIST) {
            int slot = event.getRawSlot();
            if (slot == 49) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.0f);
                gui.openMainMenu(player);
                return;
            }
            if (slot == 45 && holder.getPage() > 0) {
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 0.8f, 1.0f);
                gui.openCategoryMenu(player, holder.getCategory(), holder.getPage() - 1);
                return;
            }
            if (slot == 53) {
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 0.8f, 1.0f);
                gui.openCategoryMenu(player, holder.getCategory(), holder.getPage() + 1);
                return;
            }

            String achId = clicked.getItemMeta().getPersistentDataContainer().get(gui.getAchievementIdKey(), PersistentDataType.STRING);
            if (achId == null) return;

            Achievement ach = plugin.getAchievementManager().getAchievement(achId);
            if (ach == null) return;

            PlayerAchievementData data = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());
            if (!data.isCompleted(achId)) {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.7f, 1.0f);
                return;
            }

            if (data.isClaimed(achId)) {
                player.sendMessage(plugin.getMiniMessage().deserialize("<yellow>Вы уже забрали награду за это достижение!"));
                return;
            }

            if (plugin.getVaultHook().deposit(player, ach.getReward())) {
                data.setClaimed(achId, true);
                String formatted = plugin.getVaultHook().format(ach.getReward());
                player.sendMessage(plugin.getMiniMessage().deserialize(
                        "<green><bold>✔ НАГРАДА ПОЛУЧЕНА!</bold> <gray>Вам начислено <gold><bold>" + formatted + "</bold> <gray>за достижение <white>" + ach.getName()
                ));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.9f, 1.3f);
                gui.openCategoryMenu(player, holder.getCategory(), holder.getPage());
            } else {
                player.sendMessage(plugin.getMiniMessage().deserialize("<red>Ошибка экономики Vault при начислении награды!"));
            }
        }
    }
}
