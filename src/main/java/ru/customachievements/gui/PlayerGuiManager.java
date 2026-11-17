package ru.customachievements.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.DifficultyCategory;
import ru.customachievements.model.PlayerAchievementData;

import java.util.ArrayList;
import java.util.List;

public class PlayerGuiManager {

    private final CustomAchievementsPlugin plugin;
    private final NamespacedKey achievementIdKey;
    private final NamespacedKey categoryKey;
    private static final int ITEMS_PER_PAGE = 36;

    public PlayerGuiManager(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
        this.achievementIdKey = new NamespacedKey(plugin, "ach_id");
        this.categoryKey = new NamespacedKey(plugin, "cat_id");
    }

    public NamespacedKey getAchievementIdKey() { return achievementIdKey; }
    public NamespacedKey getCategoryKey() { return categoryKey; }

    public void openMainMenu(Player player) {
        PlayerMenuHolder holder = new PlayerMenuHolder(PlayerMenuHolder.ViewType.MAIN_CATEGORIES, null, 0);
        Inventory inv = Bukkit.createInventory(holder, 36, mm("<dark_gray>✦ <bold>Достижения сервера</bold> ✦"));
        holder.setInventory(inv);

        ItemStack glass = createFiller(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, glass);
        }

        PlayerAchievementData data = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        for (DifficultyCategory cat : DifficultyCategory.values()) {
            List<Achievement> catAchievements = plugin.getAchievementManager().getByCategory(cat);
            int total = catAchievements.size();
            long completed = catAchievements.stream().filter(a -> data.isCompleted(a.getId())).count();
            long unclaimed = catAchievements.stream().filter(a -> data.isCompleted(a.getId()) && !data.isClaimed(a.getId())).count();
            double totalPool = catAchievements.stream().mapToDouble(Achievement::getReward).sum();

            ItemStack item = new ItemStack(cat.getIcon());
            ItemMeta meta = item.getItemMeta();
            meta.displayName(mm(cat.getColorTag() + "<bold>" + cat.getDisplayName() + "</bold>"));

            List<Component> lore = new ArrayList<>();
            lore.add(mm("<gray>Выполнено: <white>" + completed + " <dark_gray>/ <white>" + total));
            lore.add(mm("<gray>Общий пул наград: <gold>" + plugin.getVaultHook().format(totalPool)));
            lore.add(Component.empty());
            if (unclaimed > 0) {
                lore.add(mm("<green><bold>● Доступно наград к получению: " + unclaimed + "!</bold>"));
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add(mm("<yellow>Нажмите, чтобы открыть список"));
            }

            meta.lore(lore);
            meta.getPersistentDataContainer().set(categoryKey, PersistentDataType.STRING, cat.name());
            item.setItemMeta(meta);

            inv.setItem(cat.getMainMenuSlot(), item);
        }

        int totalAll = plugin.getAchievementManager().getAllAchievements().size();
        int completedAll = data.getCompletedIds().size();
        ItemStack stats = new ItemStack(Material.NETHER_STAR);
        ItemMeta statsMeta = stats.getItemMeta();
        statsMeta.displayName(mm("<gold><bold>Ваш общий прогресс</bold>"));
        statsMeta.lore(List.of(
                mm("<gray>Всего выполнено: <yellow>" + completedAll + " <dark_gray>/ <yellow>" + totalAll),
                mm("<gray>Забрано наград: <green>" + data.getClaimedIds().size())
        ));
        stats.setItemMeta(statsMeta);
        inv.setItem(31, stats);

        player.openInventory(inv);
    }

    public void openCategoryMenu(Player player, DifficultyCategory category, int page) {
        List<Achievement> list = plugin.getAchievementManager().getByCategory(category);
        int maxPages = Math.max(1, (int) Math.ceil((double) list.size() / ITEMS_PER_PAGE));
        int currentPage = Math.max(0, Math.min(page, maxPages - 1));

        PlayerMenuHolder holder = new PlayerMenuHolder(PlayerMenuHolder.ViewType.CATEGORY_LIST, category, currentPage);
        Inventory inv = Bukkit.createInventory(holder, 54,
                mm(category.getColorTag() + "<bold>" + category.getDisplayName() + "</bold> <dark_gray>(" + (currentPage + 1) + "/" + maxPages + ")"));
        holder.setInventory(inv);

        PlayerAchievementData data = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, list.size());

        for (int i = start; i < end; i++) {
            Achievement ach = list.get(i);
            inv.setItem(i - start, buildAchievementItem(ach, data));
        }

        ItemStack border = createFiller(Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 36; i < 54; i++) {
            inv.setItem(i, border);
        }

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.displayName(mm("<red><bold>← Назад к категориям</bold>"));
        back.setItemMeta(backMeta);
        inv.setItem(49, back);

        if (currentPage > 0) {
            ItemStack prev = new ItemStack(Material.ARROW);
            ItemMeta prevMeta = prev.getItemMeta();
            prevMeta.displayName(mm("<yellow>← Предыдущая страница"));
            prev.setItemMeta(prevMeta);
            inv.setItem(45, prev);
        }

        if (currentPage < maxPages - 1) {
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta nextMeta = next.getItemMeta();
            nextMeta.displayName(mm("<yellow>Следующая страница →"));
            next.setItemMeta(nextMeta);
            inv.setItem(53, next);
        }

        player.openInventory(inv);
    }

    private ItemStack buildAchievementItem(Achievement ach, PlayerAchievementData data) {
        boolean completed = data.isCompleted(ach.getId());
        boolean claimed = data.isClaimed(ach.getId());
        int progress = Math.min(ach.getTargetAmount(), data.getProgress(ach.getId()));

        Material displayMat = claimed ? Material.LIME_DYE : ach.getIcon();
        ItemStack item = new ItemStack(displayMat);
        ItemMeta meta = item.getItemMeta();

        String statusPrefix = claimed ? "<green>✔ " : (completed ? "<gold>★ " : "<gray>○ ");
        meta.displayName(mm(statusPrefix + ach.getCategory().getColorTag() + "<bold>" + ach.getName() + "</bold>"));

        List<Component> lore = new ArrayList<>();
        lore.add(mm("<gray>" + ach.getDescription()));
        lore.add(Component.empty());
        lore.add(mm("<gray>Прогресс: <white>" + progress + " <dark_gray>/ <white>" + ach.getTargetAmount() + " " + buildProgressBar(progress, ach.getTargetAmount())));
        lore.add(mm("<gray>Награда: <gold><bold>" + plugin.getVaultHook().format(ach.getReward()) + "</bold>"));
        lore.add(Component.empty());

        if (claimed) {
            lore.add(mm("<green><bold>✔ Награда получена</bold>"));
        } else if (completed) {
            lore.add(mm("<gold><bold>▶ НАЖМИТЕ, ЧТОБЫ ЗАБРАТЬ НАГРАДУ!</bold>"));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            lore.add(mm("<red>✖ В процессе выполнения"));
        }

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.getPersistentDataContainer().set(achievementIdKey, PersistentDataType.STRING, ach.getId());
        item.setItemMeta(meta);
        return item;
    }

    private String buildProgressBar(int current, int max) {
        int bars = 10;
        int filled = (int) Math.round(((double) current / Math.max(1, max)) * bars);
        StringBuilder sb = new StringBuilder("<dark_gray>[");
        for (int i = 0; i < bars; i++) {
            sb.append(i < filled ? "<green>■" : "<gray>■");
        }
        sb.append("<dark_gray>]");
        return sb.toString();
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
