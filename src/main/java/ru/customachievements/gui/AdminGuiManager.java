package ru.customachievements.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.AdminCreationSession;
import ru.customachievements.model.DifficultyCategory;
import ru.customachievements.model.GoalType;

import java.util.ArrayList;
import java.util.List;

public class AdminGuiManager {

    private final CustomAchievementsPlugin plugin;
    private final NamespacedKey actionKey;
    private final NamespacedKey valueKey;
    private static final int ITEMS_PER_PAGE = 36;

    public AdminGuiManager(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
        this.actionKey = new NamespacedKey(plugin, "admin_action");
        this.valueKey = new NamespacedKey(plugin, "admin_value");
    }

    public NamespacedKey getActionKey() { return actionKey; }
    public NamespacedKey getValueKey() { return valueKey; }

    public void openAdminMainMenu(Player player) {
        AdminMenuHolder holder = new AdminMenuHolder(AdminMenuHolder.AdminView.MAIN, null, 0, null);
        Inventory inv = Bukkit.createInventory(holder, 36, mm("<dark_red><bold>⚙ Админ-панель достижений</bold>"));
        holder.setInventory(inv);

        fillGlass(inv, Material.BLACK_STAINED_GLASS_PANE);

        for (DifficultyCategory cat : DifficultyCategory.values()) {
            int count = plugin.getAchievementManager().getByCategory(cat).size();
            ItemStack item = createButton(cat.getIcon(),
                    cat.getColorTag() + "<bold>" + cat.getDisplayName() + "</bold> <gray>(" + count + " шт.)",
                    List.of(mm("<yellow>Нажмите для редактирования списка")),
                    "OPEN_CAT", cat.name());
            inv.setItem(cat.getMainMenuSlot(), item);
        }

        ItemStack createBtn = createButton(Material.EMERALD_BLOCK,
                "<green><bold>+ Создать новое достижение</bold>",
                List.of(
                        mm("<gray>Пошаговый конструктор нового достижения"),
                        mm("<gray>с автоматическим сохранением в achievements.yml")
                ),
                "START_CREATE", "NEW");
        inv.setItem(31, createBtn);

        player.openInventory(inv);
    }

    public void openAdminCategoryList(Player player, DifficultyCategory category, int page) {
        List<Achievement> list = plugin.getAchievementManager().getByCategory(category);
        int maxPages = Math.max(1, (int) Math.ceil((double) list.size() / ITEMS_PER_PAGE));
        int currentPage = Math.max(0, Math.min(page, maxPages - 1));

        AdminMenuHolder holder = new AdminMenuHolder(AdminMenuHolder.AdminView.CATEGORY_LIST, category, currentPage, null);
        Inventory inv = Bukkit.createInventory(holder, 54,
                mm("<dark_red>Редактор: " + category.getColorTag() + category.getDisplayName() + " <dark_gray>(" + (currentPage + 1) + "/" + maxPages + ")"));
        holder.setInventory(inv);

        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, list.size());

        for (int i = start; i < end; i++) {
            Achievement ach = list.get(i);
            List<Component> lore = new ArrayList<>();
            lore.add(mm("<dark_gray>ID: " + ach.getId()));
            lore.add(mm("<gray>" + ach.getDescription()));
            lore.add(Component.empty());
            lore.add(mm("<gray>Тип: <aqua>" + ach.getGoalType().name()));
            if (ach.getTargetMaterial() != null) lore.add(mm("<gray>Материал: <white>" + ach.getTargetMaterial().name()));
            if (ach.getTargetEntity() != null) lore.add(mm("<gray>Существо: <white>" + ach.getTargetEntity().name()));
            lore.add(mm("<gray>Требуется: <yellow>" + ach.getTargetAmount()));
            lore.add(mm("<gray>Награда: <gold>" + plugin.getVaultHook().format(ach.getReward())));
            lore.add(Component.empty());
            lore.add(mm("<green>▶ Нажмите, чтобы редактировать"));

            inv.setItem(i - start, createButton(ach.getIcon(),
                    category.getColorTag() + "<bold>" + ach.getName() + "</bold>",
                    lore, "EDIT_ACH", ach.getId()));
        }

        for (int i = 36; i < 54; i++) {
            inv.setItem(i, createFiller(Material.GRAY_STAINED_GLASS_PANE));
        }

        inv.setItem(49, createButton(Material.BARRIER, "<red><bold>← В главное админ-меню</bold>", List.of(), "BACK_MAIN", ""));
        if (currentPage > 0) {
            inv.setItem(45, createButton(Material.ARROW, "<yellow>← Предыдущая страница", List.of(), "PREV_PAGE", ""));
        }
        if (currentPage < maxPages - 1) {
            inv.setItem(53, createButton(Material.ARROW, "<yellow>Следующая страница →", List.of(), "NEXT_PAGE", ""));
        }

        player.openInventory(inv);
    }

    public void openEditAchievementMenu(Player player, Achievement ach) {
        AdminMenuHolder holder = new AdminMenuHolder(AdminMenuHolder.AdminView.EDIT_ACHIEVEMENT, ach.getCategory(), 0, ach.getId());
        Inventory inv = Bukkit.createInventory(holder, 45, mm("<dark_red>Редактирование: <white>" + ach.getName()));
        holder.setInventory(inv);

        fillGlass(inv, Material.GRAY_STAINED_GLASS_PANE);

        inv.setItem(4, createButton(ach.getIcon(),
                "<aqua><bold>Иконка достижения: " + ach.getIcon().name() + "</bold>",
                List.of(
                        mm("<gray>Возьмите любой предмет курсором"),
                        mm("<gray>и кликните по этому слоту, чтобы заменить иконку!")
                ),
                "SET_ICON", ach.getId()));

        inv.setItem(19, createButton(Material.RED_CONCRETE, "<red><bold>-1 000 монет</bold>", List.of(), "REWARD_DELTA", "-1000"));
        inv.setItem(20, createButton(Material.ORANGE_CONCRETE, "<gold><bold>-100 монет</bold>", List.of(), "REWARD_DELTA", "-100"));
        inv.setItem(22, createButton(Material.GOLD_INGOT,
                "<gold><bold>Награда: " + plugin.getVaultHook().format(ach.getReward()) + "</bold>",
                List.of(mm("<yellow>Нажмите, чтобы ввести точную сумму в чат")),
                "CHAT_REWARD", ach.getId()));
        inv.setItem(24, createButton(Material.LIME_CONCRETE, "<green><bold>+100 монет</bold>", List.of(), "REWARD_DELTA", "100"));
        inv.setItem(25, createButton(Material.GREEN_CONCRETE, "<dark_green><bold>+1 000 монет</bold>", List.of(), "REWARD_DELTA", "1000"));

        inv.setItem(30, createButton(Material.CLOCK,
                "<yellow><bold>Требуемое количество: " + ach.getTargetAmount() + "</bold>",
                List.of(mm("<gray>Нажмите, чтобы ввести новое число в чат")),
                "CHAT_AMOUNT", ach.getId()));

        inv.setItem(32, createButton(Material.NAME_TAG,
                "<aqua><bold>Название и описание</bold>",
                List.of(
                        mm("<gray>Название: <white>" + ach.getName()),
                        mm("<gray>Описание: <white>" + ach.getDescription()),
                        mm("<yellow>ЛКМ — изменить название в чате"),
                        mm("<yellow>ПКМ — изменить описание в чате")
                ),
                "CHAT_TEXT", ach.getId()));

        inv.setItem(36, createButton(Material.TNT,
                "<dark_red><bold>✖ УДАЛИТЬ ДОСТИЖЕНИЕ</bold>",
                List.of(mm("<red>Внимание! Действие необратимо.")),
                "DELETE_ACH", ach.getId()));

        inv.setItem(40, createButton(Material.BARRIER, "<red><bold>← Назад к списку</bold>", List.of(), "BACK_CAT", ach.getCategory().name()));

        player.openInventory(inv);
    }

    public void openCreateStepCategory(Player player) {
        AdminMenuHolder holder = new AdminMenuHolder(AdminMenuHolder.AdminView.CREATE_STEP_CATEGORY, null, 0, null);
        Inventory inv = Bukkit.createInventory(holder, 27, mm("<dark_green>Создание [1/3]: Выберите сложность"));
        holder.setInventory(inv);

        fillGlass(inv, Material.BLACK_STAINED_GLASS_PANE);

        int[] slots = {11, 12, 13, 14, 15};
        DifficultyCategory[] cats = DifficultyCategory.values();
        for (int i = 0; i < cats.length; i++) {
            DifficultyCategory cat = cats[i];
            inv.setItem(slots[i], createButton(cat.getIcon(),
                    cat.getColorTag() + "<bold>" + cat.getDisplayName() + "</bold>",
                    List.of(mm("<yellow>Нажмите для выбора этой категории")),
                    "CREATE_CAT", cat.name()));
        }

        player.openInventory(inv);
    }

    public void openCreateStepGoalType(Player player) {
        AdminMenuHolder holder = new AdminMenuHolder(AdminMenuHolder.AdminView.CREATE_STEP_GOAL_TYPE, null, 0, null);
        Inventory inv = Bukkit.createInventory(holder, 36, mm("<dark_green>Создание [2/3]: Тип события"));
        holder.setInventory(inv);

        fillGlass(inv, Material.GRAY_STAINED_GLASS_PANE);

        GoalType[] types = GoalType.values();
        for (int i = 0; i < types.length && i < 27; i++) {
            GoalType gt = types[i];
            Material icon = switch (gt) {
                case BREAK_BLOCK -> Material.DIAMOND_PICKAXE;
                case PLACE_BLOCK -> Material.BRICKS;
                case CRAFT_ITEM -> Material.CRAFTING_TABLE;
                case SMELT_ITEM -> Material.FURNACE;
                case KILL_ENTITY -> Material.DIAMOND_SWORD;
                case WALK_DISTANCE, SPRINT_DISTANCE -> Material.LEATHER_BOOTS;
                case SWIM_DISTANCE -> Material.WATER_BUCKET;
                case FLY_ELYTRA -> Material.ELYTRA;
                case FISH_CATCH -> Material.FISHING_ROD;
                case BREW_POTION -> Material.BREWING_STAND;
                case ENCHANT_ITEM -> Material.ENCHANTING_TABLE;
                case CONSUME_ITEM -> Material.GOLDEN_APPLE;
                case TAME_ENTITY -> Material.BONE;
                case BREED_ENTITY -> Material.WHEAT;
                case SHEAR_SHEEP -> Material.SHEARS;
                case TRADE_VILLAGER -> Material.EMERALD;
                case WIN_RAID -> Material.TOTEM_OF_UNDYING;
                case ENTER_NETHER -> Material.NETHERRACK;
                case ENTER_END -> Material.END_STONE;
                case GAIN_XP_LEVEL -> Material.EXPERIENCE_BOTTLE;
                case REPAIR_ANVIL -> Material.ANVIL;
                case SHIELD_BLOCK -> Material.SHIELD;
            };

            inv.setItem(i, createButton(icon,
                    "<aqua><bold>" + gt.name() + "</bold>",
                    List.of(
                            mm("<gray>" + gt.getDescription()),
                            mm("<yellow>Нажмите для выбора")
                    ),
                    "CREATE_GOAL", gt.name()));
        }

        player.openInventory(inv);
    }

    public void openCreateFinalReview(Player player, AdminCreationSession session) {
        AdminMenuHolder holder = new AdminMenuHolder(AdminMenuHolder.AdminView.CREATE_FINAL_REVIEW, session.getCategory(), 0, null);
        Inventory inv = Bukkit.createInventory(holder, 27, mm("<dark_green>Создание [3/3]: Подтверждение"));
        holder.setInventory(inv);

        fillGlass(inv, Material.BLACK_STAINED_GLASS_PANE);

        List<Component> lore = new ArrayList<>();
        lore.add(mm("<gray>Категория: " + session.getCategory().getColorTag() + session.getCategory().getDisplayName()));
        lore.add(mm("<gray>Тип события: <aqua>" + session.getGoalType().name()));
        if (session.getTargetMaterial() != null) lore.add(mm("<gray>Материал: <white>" + session.getTargetMaterial().name()));
        if (session.getTargetEntity() != null) lore.add(mm("<gray>Существо: <white>" + session.getTargetEntity().name()));
        lore.add(mm("<gray>Описание: <white>" + session.getDescription()));
        lore.add(mm("<gray>Количество: <yellow>" + session.getTargetAmount()));
        lore.add(mm("<gray>Награда: <gold>" + plugin.getVaultHook().format(session.getReward())));
        lore.add(Component.empty());
        lore.add(mm("<aqua>Кликните предметом по этой иконке, чтобы сменить её!"));

        inv.setItem(11, createButton(session.getIcon(),
                "<gold><bold>" + session.getName() + "</bold>",
                lore, "CREATE_SET_ICON", ""));

        inv.setItem(15, createButton(Material.LIME_WOOL,
                "<green><bold>✔ СОХРАНИТЬ И ДОБАВИТЬ</bold>",
                List.of(mm("<gray>Достижение сразу появится в списке и в achievements.yml")),
                "CREATE_CONFIRM", ""));

        inv.setItem(22, createButton(Material.BARRIER, "<red>Отмена", List.of(), "BACK_MAIN", ""));

        player.openInventory(inv);
    }

    private ItemStack createButton(Material mat, String name, List<Component> lore, String action, String value) {
        ItemStack item = new ItemStack(mat.isItem() ? mat : Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(mm(name));
        if (!lore.isEmpty()) {
            meta.lore(lore);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.getPersistentDataContainer().set(actionKey, PersistentDataType.STRING, action);
        meta.getPersistentDataContainer().set(valueKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return item;
    }

    private void fillGlass(Inventory inv, Material mat) {
        ItemStack glass = createFiller(mat);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, glass);
        }
    }

    private ItemStack createFiller(Material mat) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());
        item.setItemMeta(meta);
        return item;
    }

    private Component mm(String text) {
        return plugin.getMiniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false);
    }
}
