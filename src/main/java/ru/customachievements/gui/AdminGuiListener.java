package ru.customachievements.gui;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.AdminCreationSession;
import ru.customachievements.model.AdminEditSession;
import ru.customachievements.model.DifficultyCategory;
import ru.customachievements.model.GoalType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AdminGuiListener implements Listener {

    private final CustomAchievementsPlugin plugin;
    private final Map<UUID, AdminEditSession> editSessions = new ConcurrentHashMap<>();
    private final Map<UUID, AdminCreationSession> creationSessions = new ConcurrentHashMap<>();

    public AdminGuiListener(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        editSessions.remove(uuid);
        creationSessions.remove(uuid);
    }

    @EventHandler
    public void onAdminInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof AdminMenuHolder holder)) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getClickedInventory() != null && event.getClickedInventory().equals(player.getInventory())) {
            return;
        }

        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        AdminGuiManager gui = plugin.getAdminGuiManager();
        String action = clicked.getItemMeta().getPersistentDataContainer().get(gui.getActionKey(), PersistentDataType.STRING);
        String value = clicked.getItemMeta().getPersistentDataContainer().get(gui.getValueKey(), PersistentDataType.STRING);
        if (action == null) return;

        switch (action) {
            case "OPEN_CAT" -> {
                DifficultyCategory cat = DifficultyCategory.fromString(value);
                gui.openAdminCategoryList(player, cat, 0);
            }
            case "BACK_MAIN" -> {
                creationSessions.remove(player.getUniqueId());
                gui.openAdminMainMenu(player);
            }
            case "BACK_CAT" -> {
                DifficultyCategory cat = DifficultyCategory.fromString(value);
                gui.openAdminCategoryList(player, cat, 0);
            }
            case "PREV_PAGE" -> gui.openAdminCategoryList(player, holder.getCategory(), holder.getPage() - 1);
            case "NEXT_PAGE" -> gui.openAdminCategoryList(player, holder.getCategory(), holder.getPage() + 1);
            case "EDIT_ACH" -> {
                Achievement ach = plugin.getAchievementManager().getAchievement(value);
                if (ach != null) gui.openEditAchievementMenu(player, ach);
            }
            case "SET_ICON" -> {
                ItemStack cursor = event.getCursor();
                if (cursor.getType() == Material.AIR) {
                    player.sendMessage(plugin.getMiniMessage().deserialize("<yellow>Возьмите нужный предмет курсором из вашего инвентаря и кликните сюда!"));
                    return;
                }
                Achievement ach = plugin.getAchievementManager().getAchievement(holder.getEditingAchievementId());
                if (ach != null) {
                    ach.setIcon(cursor.getType());
                    plugin.getAchievementManager().saveAchievements();
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, 1.2f);
                    gui.openEditAchievementMenu(player, ach);
                }
            }
            case "REWARD_DELTA" -> {
                Achievement ach = plugin.getAchievementManager().getAchievement(holder.getEditingAchievementId());
                if (ach != null && value != null) {
                    double delta = Double.parseDouble(value);
                    ach.setReward(Math.max(0, ach.getReward() + delta));
                    plugin.getAchievementManager().saveAchievements();
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
                    gui.openEditAchievementMenu(player, ach);
                }
            }
            case "CHAT_REWARD" -> {
                editSessions.put(player.getUniqueId(), new AdminEditSession(holder.getEditingAchievementId(), AdminEditSession.EditField.REWARD));
                player.closeInventory();
                player.sendMessage(plugin.getMiniMessage().deserialize("<gold>Введите новую сумму денежной награды в чат (или <red>отмена<gold>):"));
            }
            case "CHAT_AMOUNT" -> {
                editSessions.put(player.getUniqueId(), new AdminEditSession(holder.getEditingAchievementId(), AdminEditSession.EditField.TARGET_AMOUNT));
                player.closeInventory();
                player.sendMessage(plugin.getMiniMessage().deserialize("<yellow>Введите новое требуемое количество в чат (целое число, или <red>отмена<yellow>):"));
            }
            case "CHAT_TEXT" -> {
                AdminEditSession.EditField field = (event.getClick() == ClickType.RIGHT)
                        ? AdminEditSession.EditField.DESCRIPTION
                        : AdminEditSession.EditField.NAME;
                editSessions.put(player.getUniqueId(), new AdminEditSession(holder.getEditingAchievementId(), field));
                player.closeInventory();
                String label = (field == AdminEditSession.EditField.NAME) ? "новое название" : "новое описание";
                player.sendMessage(plugin.getMiniMessage().deserialize("<aqua>Введите " + label + " достижения в чат (или <red>отмена<aqua>):"));
            }
            case "DELETE_ACH" -> {
                Achievement ach = plugin.getAchievementManager().getAchievement(holder.getEditingAchievementId());
                if (ach != null) {
                    DifficultyCategory cat = ach.getCategory();
                    plugin.getAchievementManager().removeAchievement(ach.getId());
                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>Достижение <white>" + ach.getName() + " <red>удалено!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.8f, 0.9f);
                    gui.openAdminCategoryList(player, cat, 0);
                }
            }
            case "START_CREATE" -> {
                creationSessions.put(player.getUniqueId(), new AdminCreationSession());
                gui.openCreateStepCategory(player);
            }
            case "CREATE_CAT" -> {
                AdminCreationSession session = creationSessions.get(player.getUniqueId());
                if (session != null) {
                    session.setCategory(DifficultyCategory.fromString(value));
                    session.setStep(AdminCreationSession.Step.SELECT_GOAL_TYPE);
                    gui.openCreateStepGoalType(player);
                }
            }
            case "CREATE_GOAL" -> {
                AdminCreationSession session = creationSessions.get(player.getUniqueId());
                if (session == null) return;
                GoalType gt = GoalType.fromString(value);
                session.setGoalType(gt);
                player.closeInventory();

                if (gt.isRequiresMaterial()) {
                    session.setStep(AdminCreationSession.Step.INPUT_TARGET);
                    player.sendMessage(plugin.getMiniMessage().deserialize(
                            "<green>Введите название материала (например, <white>DIAMOND_ORE<green>) или слово <yellow>рука<green>, чтобы взять предмет из основной руки:"
                    ));
                } else if (gt.isRequiresEntity()) {
                    session.setStep(AdminCreationSession.Step.INPUT_TARGET);
                    player.sendMessage(plugin.getMiniMessage().deserialize(
                            "<green>Введите тип существа на английском (например, <white>ZOMBIE<green>, <white>CREEPER<green>, <white>WARDEN<green>):"
                    ));
                } else {
                    session.setStep(AdminCreationSession.Step.INPUT_NAME);
                    player.sendMessage(plugin.getMiniMessage().deserialize("<green>Введите название нового достижения в чат:"));
                }
            }
            case "CREATE_SET_ICON" -> {
                AdminCreationSession session = creationSessions.get(player.getUniqueId());
                ItemStack cursor = event.getCursor();
                if (session != null && cursor.getType() != Material.AIR) {
                    session.setIcon(cursor.getType());
                    gui.openCreateFinalReview(player, session);
                }
            }
            case "CREATE_CONFIRM" -> {
                AdminCreationSession session = creationSessions.remove(player.getUniqueId());
                if (session == null) return;

                String newId = plugin.getAchievementManager().generateUniqueId(session.getCategory());
                Achievement created = new Achievement(
                        newId,
                        session.getName(),
                        session.getDescription(),
                        session.getCategory(),
                        session.getGoalType(),
                        session.getTargetMaterial(),
                        session.getTargetEntity(),
                        session.getTargetAmount(),
                        session.getReward(),
                        session.getIcon()
                );

                plugin.getAchievementManager().addAchievement(created);
                player.sendMessage(plugin.getMiniMessage().deserialize(
                        "<green><bold>✔ Новое достижение создано и сохранено!</bold> <gray>(ID: <white>" + newId + "<gray>)"
                ));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
                gui.openAdminCategoryList(player, created.getCategory(), 0);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAdminChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!editSessions.containsKey(uuid) && !creationSessions.containsKey(uuid)) {
            return;
        }

        event.setCancelled(true);
        String input = PlainTextComponentSerializer.plainText().serialize(event.message()).trim();

        if (input.equalsIgnoreCase("отмена") || input.equalsIgnoreCase("cancel")) {
            editSessions.remove(uuid);
            creationSessions.remove(uuid);
            player.sendMessage(plugin.getMiniMessage().deserialize("<red>Действие отменено."));
            return;
        }

        if (editSessions.containsKey(uuid)) {
            AdminEditSession session = editSessions.remove(uuid);
            Bukkit.getScheduler().runTask(plugin, () -> handleEditChatInput(player, session, input));
            return;
        }

        AdminCreationSession creation = creationSessions.get(uuid);
        if (creation != null) {
            Bukkit.getScheduler().runTask(plugin, () -> handleCreationChatInput(player, creation, input));
        }
    }

    private void handleEditChatInput(Player player, AdminEditSession session, String input) {
        Achievement ach = plugin.getAchievementManager().getAchievement(session.getAchievementId());
        if (ach == null) return;

        switch (session.getField()) {
            case REWARD -> {
                try {
                    double val = Double.parseDouble(input.replace(",", "."));
                    if (val < 0) throw new NumberFormatException();
                    ach.setReward(val);
                    plugin.getAchievementManager().saveAchievements();
                    player.sendMessage(plugin.getMiniMessage().deserialize("<green>Награда обновлена: <gold>" + plugin.getVaultHook().format(val)));
                    plugin.getAdminGuiManager().openEditAchievementMenu(player, ach);
                } catch (NumberFormatException e) {
                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>Некорректное число! Попробуйте снова."));
                }
            }
            case TARGET_AMOUNT -> {
                try {
                    int val = Integer.parseInt(input);
                    if (val <= 0) throw new NumberFormatException();
                    ach.setTargetAmount(val);
                    plugin.getAchievementManager().saveAchievements();
                    player.sendMessage(plugin.getMiniMessage().deserialize("<green>Требуемое количество обновлено: <yellow>" + val));
                    plugin.getAdminGuiManager().openEditAchievementMenu(player, ach);
                } catch (NumberFormatException e) {
                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>Введите положительное целое число!"));
                }
            }
            case NAME -> {
                ach.setName(input);
                plugin.getAchievementManager().saveAchievements();
                player.sendMessage(plugin.getMiniMessage().deserialize("<green>Название обновлено!"));
                plugin.getAdminGuiManager().openEditAchievementMenu(player, ach);
            }
            case DESCRIPTION -> {
                ach.setDescription(input);
                plugin.getAchievementManager().saveAchievements();
                player.sendMessage(plugin.getMiniMessage().deserialize("<green>Описание обновлено!"));
                plugin.getAdminGuiManager().openEditAchievementMenu(player, ach);
            }
        }
    }

    private void handleCreationChatInput(Player player, AdminCreationSession session, String input) {
        switch (session.getStep()) {
            case INPUT_TARGET -> {
                if (session.getGoalType().isRequiresMaterial()) {
                    Material mat;
                    if (input.equalsIgnoreCase("рука") || input.equalsIgnoreCase("hand")) {
                        mat = player.getInventory().getItemInMainHand().getType();
                        if (mat == Material.AIR) {
                            player.sendMessage(plugin.getMiniMessage().deserialize("<red>В вашей руке пусто! Возьмите предмет или введите его ID:"));
                            return;
                        }
                    } else {
                        mat = Material.matchMaterial(input.toUpperCase());
                        if (mat == null) {
                            player.sendMessage(plugin.getMiniMessage().deserialize("<red>Материал не найден! Попробуйте еще раз (например, STONE):"));
                            return;
                        }
                    }
                    session.setTargetMaterial(mat);
                    if (mat.isItem()) session.setIcon(mat);
                } else if (session.getGoalType().isRequiresEntity()) {
                    try {
                        EntityType entity = EntityType.valueOf(input.toUpperCase());
                        session.setTargetEntity(entity);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(plugin.getMiniMessage().deserialize("<red>Тип существа не найден! Попробуйте еще раз (например, ZOMBIE):"));
                        return;
                    }
                }
                session.setStep(AdminCreationSession.Step.INPUT_NAME);
                player.sendMessage(plugin.getMiniMessage().deserialize("<green>Цель принята! Теперь введите <white>название<green> достижения:"));
            }
            case INPUT_NAME -> {
                session.setName(input);
                session.setStep(AdminCreationSession.Step.INPUT_DESCRIPTION);
                player.sendMessage(plugin.getMiniMessage().deserialize("<green>Название сохранено! Введите <white>описание<green> условия:"));
            }
            case INPUT_DESCRIPTION -> {
                session.setDescription(input);
                session.setStep(AdminCreationSession.Step.INPUT_AMOUNT);
                player.sendMessage(plugin.getMiniMessage().deserialize("<green>Описание сохранено! Введите <yellow>требуемое количество<green> (целое число):"));
            }
            case INPUT_AMOUNT -> {
                try {
                    int amount = Integer.parseInt(input);
                    if (amount <= 0) throw new NumberFormatException();
                    session.setTargetAmount(amount);
                    session.setStep(AdminCreationSession.Step.INPUT_REWARD);
                    player.sendMessage(plugin.getMiniMessage().deserialize("<green>Количество принято! Введите <gold>денежную награду<green>:"));
                } catch (NumberFormatException e) {
                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>Введите корректное целое число больше 0:"));
                }
            }
            case INPUT_REWARD -> {
                try {
                    double reward = Double.parseDouble(input.replace(",", "."));
                    if (reward < 0) throw new NumberFormatException();
                    session.setReward(reward);
                    session.setStep(AdminCreationSession.Step.CONFIRM_ICON);
                    plugin.getAdminGuiManager().openCreateFinalReview(player, session);
                } catch (NumberFormatException e) {
                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>Введите корректную сумму награды:"));
                }
            }
            default -> {
            }
        }
    }
}
