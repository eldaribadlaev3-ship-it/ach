package ru.customachievements.listener;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.raid.RaidFinishEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import ru.customachievements.CustomAchievementsPlugin;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.GoalType;

public class AchievementProgressListener implements Listener {

    private final CustomAchievementsPlugin plugin;

    public AchievementProgressListener(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getPlayerDataManager().loadPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getPlayerDataManager().savePlayerAsync(event.getPlayer().getUniqueId(), true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material type = event.getBlock().getType();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.BREAK_BLOCK)) {
            if (ach.getTargetMaterial() == null || ach.getTargetMaterial() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material type = event.getBlockPlaced().getType();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.PLACE_BLOCK)) {
            if (ach.getTargetMaterial() == null || ach.getTargetMaterial() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack result = event.getRecipe().getResult();
        Material type = result.getType();
        int amount = result.getAmount();

        if (event.isShiftClick()) {
            int maxCraftable = calculateMaxShiftCraft(event.getInventory());
            amount = maxCraftable * result.getAmount();
        }
        if (amount <= 0) return;

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.CRAFT_ITEM)) {
            if (ach.getTargetMaterial() == null || ach.getTargetMaterial() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, amount);
            }
        }
    }

    private int calculateMaxShiftCraft(CraftingInventory inv) {
        int minStack = Integer.MAX_VALUE;
        for (ItemStack item : inv.getMatrix()) {
            if (item != null && item.getType() != Material.AIR) {
                minStack = Math.min(minStack, item.getAmount());
            }
        }
        return minStack == Integer.MAX_VALUE ? 1 : minStack;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        Material type = event.getItemType();
        int amount = event.getItemAmount();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.SMELT_ITEM)) {
            if (ach.getTargetMaterial() == null || ach.getTargetMaterial() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, amount);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        EntityType entityType = event.getEntityType();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.KILL_ENTITY)) {
            if (ach.getTargetEntity() == null || ach.getTargetEntity() == entityType) {
                plugin.getPlayerDataManager().addProgress(killer, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStatisticIncrement(PlayerStatisticIncrementEvent event) {
        Statistic stat = event.getStatistic();
        GoalType goalType = switch (stat) {
            case WALK_ONE_CM -> GoalType.WALK_DISTANCE;
            case SPRINT_ONE_CM -> GoalType.SPRINT_DISTANCE;
            case SWIM_ONE_CM -> GoalType.SWIM_DISTANCE;
            case AVIATE_ONE_CM -> GoalType.FLY_ELYTRA;
            default -> null;
        };

        if (goalType == null) return;

        int totalBlocks = event.getNewValue() / 100;
        if (totalBlocks <= 0) return;

        Player player = event.getPlayer();
        for (Achievement ach : plugin.getAchievementManager().getByGoalType(goalType)) {
            plugin.getPlayerDataManager().setMaxProgress(player, ach, totalBlocks);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        Player player = event.getPlayer();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.FISH_CATCH)) {
            plugin.getPlayerDataManager().addProgress(player, ach, 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        int brewedCount = 0;
        for (int i = 0; i < 3; i++) {
            ItemStack item = event.getContents().getItem(i);
            if (item != null && item.getType() != Material.AIR) {
                brewedCount++;
            }
        }
        if (brewedCount == 0) return;

        Player nearest = null;
        double minDist = 64.0;
        for (Player p : event.getBlock().getWorld().getPlayers()) {
            double dist = p.getLocation().distanceSquared(event.getBlock().getLocation());
            if (dist < minDist) {
                minDist = dist;
                nearest = p;
            }
        }

        if (nearest != null) {
            for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.BREW_POTION)) {
                plugin.getPlayerDataManager().addProgress(nearest, ach, brewedCount);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.ENCHANT_ITEM)) {
            plugin.getPlayerDataManager().addProgress(player, ach, 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        Material type = event.getItem().getType();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.CONSUME_ITEM)) {
            if (ach.getTargetMaterial() == null || ach.getTargetMaterial() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTame(EntityTameEvent event) {
        if (!(event.getOwner() instanceof Player player)) return;
        EntityType type = event.getEntityType();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.TAME_ENTITY)) {
            if (ach.getTargetEntity() == null || ach.getTargetEntity() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreed(EntityBreedEvent event) {
        if (!(event.getBreeder() instanceof Player player)) return;
        EntityType type = event.getEntityType();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.BREED_ENTITY)) {
            if (ach.getTargetEntity() == null || ach.getTargetEntity() == type) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onShear(PlayerShearEntityEvent event) {
        Player player = event.getPlayer();
        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.SHEAR_SHEEP)) {
            plugin.getPlayerDataManager().addProgress(player, ach, 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        if (event.getInventory().getType() == InventoryType.MERCHANT && event.getRawSlot() == 2) {
            for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.TRADE_VILLAGER)) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }

        if (event.getInventory().getType() == InventoryType.ANVIL && event.getRawSlot() == 2) {
            for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.REPAIR_ANVIL)) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRaidFinish(RaidFinishEvent event) {
        for (Player winner : event.getWinners()) {
            for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.WIN_RAID)) {
                plugin.getPlayerDataManager().addProgress(winner, ach, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        switch (player.getWorld().getEnvironment()) {
            case NETHER -> {
                for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.ENTER_NETHER)) {
                    plugin.getPlayerDataManager().addProgress(player, ach, 1);
                }
            }
            case THE_END -> {
                for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.ENTER_END)) {
                    plugin.getPlayerDataManager().addProgress(player, ach, 1);
                }
            }
            default -> {
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        int newLevel = event.getNewLevel();

        for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.GAIN_XP_LEVEL)) {
            plugin.getPlayerDataManager().setMaxProgress(player, ach, newLevel);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onShieldBlock(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.isBlocking()) {
            for (Achievement ach : plugin.getAchievementManager().getByGoalType(GoalType.SHIELD_BLOCK)) {
                plugin.getPlayerDataManager().addProgress(player, ach, 1);
            }
        }
    }
}
