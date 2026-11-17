package ru.customachievements.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.customachievements.CustomAchievementsPlugin;

import java.util.ArrayList;
import java.util.List;

public class AchievementCommand implements CommandExecutor, TabCompleter {

    private final CustomAchievementsPlugin plugin;

    public AchievementCommand(CustomAchievementsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("customachievements.admin")) {
                    sender.sendMessage(plugin.getMiniMessage().deserialize("<red>У вас нет прав на выполнение этой команды."));
                    return true;
                }
                plugin.getAchievementManager().loadAchievements();
                sender.sendMessage(plugin.getMiniMessage().deserialize("<green>Файл achievements.yml успешно перезагружен! Достижений: <yellow>" + plugin.getAchievementManager().getAllAchievements().size()));
                return true;
            }

            if (args[0].equalsIgnoreCase("admin")) {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("Эту команду может использовать только игрок.");
                    return true;
                }
                if (!player.hasPermission("customachievements.admin")) {
                    player.sendMessage(plugin.getMiniMessage().deserialize("<red>У вас нет прав для доступа к админ-панели."));
                    return true;
                }
                plugin.getAdminGuiManager().openAdminMainMenu(player);
                return true;
            }
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Использование из консоли: /achievements reload");
            return true;
        }

        if (!player.hasPermission("customachievements.use")) {
            player.sendMessage(plugin.getMiniMessage().deserialize("<red>У вас нет прав для открытия меню достижений."));
            return true;
        }

        plugin.getPlayerGuiManager().openMainMenu(player);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("customachievements.admin")) {
            if ("admin".startsWith(args[0].toLowerCase())) completions.add("admin");
            if ("reload".startsWith(args[0].toLowerCase())) completions.add("reload");
        }
        return completions;
    }
}
