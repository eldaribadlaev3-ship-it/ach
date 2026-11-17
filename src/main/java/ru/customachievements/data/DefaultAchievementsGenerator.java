package ru.customachievements.data;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import ru.customachievements.model.Achievement;
import ru.customachievements.model.DifficultyCategory;
import ru.customachievements.model.GoalType;

import java.util.ArrayList;
import java.util.List;

public final class DefaultAchievementsGenerator {

    private DefaultAchievementsGenerator() {
    }

    public static List<Achievement> generateAll200() {
        List<Achievement> list = new ArrayList<>(200);

        addEasyAchievements(list);
        addSimpleAchievements(list);
        addMediumAchievements(list);
        addHardAchievements(list);
        addImpossibleAchievements(list);

        return list;
    }

    private static void addEasyAchievements(List<Achievement> list) {
        list.add(mat("easy_1", "Первые шаги лесоруба", "Добудьте 1 дубовое бревно", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.OAK_LOG, 1, 50, Material.OAK_LOG));
        list.add(mat("easy_2", "Рабочее место", "Скрафтите верстак", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.CRAFTING_TABLE, 1, 50, Material.CRAFTING_TABLE));
        list.add(mat("easy_3", "Деревянный старт", "Скрафтите деревянную кирку", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.WOODEN_PICKAXE, 1, 50, Material.WOODEN_PICKAXE));
        list.add(mat("easy_4", "Да будет свет", "Скрафтите 16 факелов", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.TORCH, 16, 50, Material.TORCH));

        list.add(mat("easy_5", "Каменный век", "Добудьте 16 булыжника", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.COBBLESTONE, 16, 100, Material.COBBLESTONE));
        list.add(mat("easy_6", "Каменный инструмент", "Скрафтите каменную кирку", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.STONE_PICKAXE, 1, 100, Material.STONE_PICKAXE));
        list.add(mat("easy_7", "Пора плавить", "Скрафтите печь", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.FURNACE, 1, 100, Material.FURNACE));
        list.add(mat("easy_8", "Сладких снов", "Скрафтите белую кровать", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.WHITE_BED, 1, 100, Material.WHITE_BED));
        list.add(mat("easy_9", "Порядок в вещах", "Скрафтите 4 сундука", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.CHEST, 4, 100, Material.CHEST));
        list.add(mat("easy_10", "Первое тепло", "Добудьте 16 угля", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.COAL_ORE, 16, 100, Material.COAL));
        list.add(mat("easy_11", "Начинающий пекарь", "Скрафтите 8 буханок хлеба", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.BREAD, 8, 100, Material.BREAD));
        list.add(mat("easy_12", "Надежная защита", "Скрафтите щит", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.SHIELD, 1, 100, Material.SHIELD));

        list.add(mat("easy_13", "Железная жила", "Добудьте 8 железной руды", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.IRON_ORE, 8, 150, Material.RAW_IRON));
        list.add(mat("easy_14", "Металлург-новичок", "Переплавьте 16 железных слитков", DifficultyCategory.EASY, GoalType.SMELT_ITEM, Material.IRON_INGOT, 16, 150, Material.IRON_INGOT));
        list.add(mat("easy_15", "Железная хватка", "Скрафтите железную кирку", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.IRON_PICKAXE, 1, 150, Material.IRON_PICKAXE));
        list.add(mat("easy_16", "Железный клинок", "Скрафтите железный меч", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.IRON_SWORD, 1, 150, Material.IRON_SWORD));
        list.add(mat("easy_17", "Водонос", "Скрафтите ведро", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.BUCKET, 1, 150, Material.WATER_BUCKET));
        list.add(mat("easy_18", "Медный век", "Переплавьте 16 медных слитков", DifficultyCategory.EASY, GoalType.SMELT_ITEM, Material.COPPER_INGOT, 16, 150, Material.COPPER_INGOT));
        list.add(mat("easy_19", "Парикмахер", "Постригите 5 овец", DifficultyCategory.EASY, GoalType.SHEAR_SHEEP, null, 5, 150, Material.SHEARS));
        list.add(mat("easy_20", "Первая рыбалка", "Поймайте 5 предметов на удочку", DifficultyCategory.EASY, GoalType.FISH_CATCH, null, 5, 150, Material.FISHING_ROD));

        list.add(ent("easy_21", "Ночной дозор", "Убейте 10 зомби", DifficultyCategory.EASY, GoalType.KILL_ENTITY, EntityType.ZOMBIE, 10, 200, Material.ROTTEN_FLESH));
        list.add(ent("easy_22", "Охотник на лучников", "Убейте 10 скелетов", DifficultyCategory.EASY, GoalType.KILL_ENTITY, EntityType.SKELETON, 10, 200, Material.BONE));
        list.add(ent("easy_23", "Сапер", "Убейте 5 криперов", DifficultyCategory.EASY, GoalType.KILL_ENTITY, EntityType.CREEPER, 5, 200, Material.GUNPOWDER));
        list.add(ent("easy_24", "Арахнофобия", "Убейте 10 пауков", DifficultyCategory.EASY, GoalType.KILL_ENTITY, EntityType.SPIDER, 10, 200, Material.STRING));
        list.add(ent("easy_25", "Верный друг", "Приручите волка", DifficultyCategory.EASY, GoalType.TAME_ENTITY, EntityType.WOLF, 1, 200, Material.BONE));
        list.add(ent("easy_26", "Мурлыка", "Приручите кошку", DifficultyCategory.EASY, GoalType.TAME_ENTITY, EntityType.CAT, 1, 200, Material.SALMON));
        list.add(mat("easy_27", "Стрелок", "Скрафтите лук и 32 стрелы", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.ARROW, 32, 200, Material.BOW));
        list.add(mat("easy_28", "Мореплаватель", "Скрафтите дубовую лодку", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.OAK_BOAT, 1, 200, Material.OAK_BOAT));

        list.add(mat("easy_29", "Сытный обед", "Съешьте 16 жареной говядины", DifficultyCategory.EASY, GoalType.CONSUME_ITEM, Material.COOKED_BEEF, 16, 250, Material.COOKED_BEEF));
        list.add(mat("easy_30", "Свиная отбивная", "Съешьте 16 жареной свинины", DifficultyCategory.EASY, GoalType.CONSUME_ITEM, Material.COOKED_PORKCHOP, 16, 250, Material.COOKED_PORKCHOP));
        list.add(mat("easy_31", "Стекольный завод", "Переплавьте 64 стекла", DifficultyCategory.EASY, GoalType.SMELT_ITEM, Material.GLASS, 64, 250, Material.GLASS));
        list.add(mat("easy_32", "Песчаный карьер", "Добудьте 128 блоков песка", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.SAND, 128, 250, Material.SAND));
        list.add(mat("easy_33", "Землекоп", "Добудьте 256 блоков земли", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.DIRT, 256, 250, Material.DIRT));
        list.add(mat("easy_34", "Каменотес", "Добудьте 256 булыжника", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.COBBLESTONE, 256, 250, Material.STONE));
        list.add(mat("easy_35", "Железный запас", "Переплавьте 64 железных слитка", DifficultyCategory.EASY, GoalType.SMELT_ITEM, Material.IRON_INGOT, 64, 250, Material.IRON_BLOCK));
        list.add(mat("easy_36", "Пшеница в закрома", "Соберите 64 снопа пшеницы", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.WHEAT, 64, 250, Material.WHEAT));

        list.add(mat("easy_37", "Огненный кремень", "Скрафтите огниво", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.FLINT_AND_STEEL, 1, 300, Material.FLINT_AND_STEEL));
        list.add(mat("easy_38", "Ориентирование", "Скрафтите компас", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.COMPASS, 1, 300, Material.COMPASS));
        list.add(mat("easy_39", "Делу время", "Скрафтите часы", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.CLOCK, 1, 300, Material.CLOCK));
        list.add(mat("easy_40", "Юный книголюб", "Скрафтите 16 книг", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.BOOK, 16, 300, Material.BOOK));
        list.add(mat("easy_41", "Защита щитом", "Заблокируйте щитом 15 ударов", DifficultyCategory.EASY, GoalType.SHIELD_BLOCK, null, 15, 300, Material.SHIELD));
        list.add(mat("easy_42", "Первые уровни", "Достигните 10-го уровня опыта", DifficultyCategory.EASY, GoalType.GAIN_XP_LEVEL, null, 10, 300, Material.EXPERIENCE_BOTTLE));
        list.add(mat("easy_43", "Уютный лагерь", "Скрафтите 4 костра", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.CAMPFIRE, 4, 300, Material.CAMPFIRE));
        list.add(mat("easy_44", "Уличное освещение", "Скрафтите 16 фонарей", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.LANTERN, 16, 300, Material.LANTERN));

        list.add(ent("easy_45", "Фермер-животновод", "Разведите 10 коров", DifficultyCategory.EASY, GoalType.BREED_ENTITY, EntityType.COW, 10, 350, Material.LEATHER));
        list.add(ent("easy_46", "Птицеферма", "Разведите 15 куриц", DifficultyCategory.EASY, GoalType.BREED_ENTITY, EntityType.CHICKEN, 15, 350, Material.EGG));
        list.add(mat("easy_47", "Золотой блеск", "Переплавьте 16 золотых слитков", DifficultyCategory.EASY, GoalType.SMELT_ITEM, Material.GOLD_INGOT, 16, 350, Material.GOLD_INGOT));
        list.add(mat("easy_48", "Красная пыль", "Добудьте 32 редстоун-руды", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.REDSTONE_ORE, 32, 350, Material.REDSTONE));
        list.add(mat("easy_49", "Синий камень", "Добудьте 16 лазуритовой руды", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.LAPIS_ORE, 16, 350, Material.LAPIS_LAZULI));
        list.add(mat("easy_50", "Пешая прогулка", "Пройдите пешком 2 500 блоков", DifficultyCategory.EASY, GoalType.WALK_DISTANCE, null, 2500, 350, Material.LEATHER_BOOTS));

        list.add(mat("easy_51", "Сахарный тростник", "Соберите 128 сахарного тростника", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.SUGAR_CANE, 128, 450, Material.SUGAR_CANE));
        list.add(mat("easy_52", "Первая сделка", "Совершите 5 торговых сделок с жителями", DifficultyCategory.EASY, GoalType.TRADE_VILLAGER, null, 5, 450, Material.EMERALD));
        list.add(mat("easy_53", "Пловец", "Проплывите 1 000 блоков", DifficultyCategory.EASY, GoalType.SWIM_DISTANCE, null, 1000, 450, Material.COD));
        list.add(mat("easy_54", "Спринтер", "Пробегите спринтом 3 000 блоков", DifficultyCategory.EASY, GoalType.SPRINT_DISTANCE, null, 3000, 450, Material.FEATHER));
        list.add(mat("easy_55", "Полезный перекус", "Съешьте 3 золотых яблока", DifficultyCategory.EASY, GoalType.CONSUME_ITEM, Material.GOLDEN_APPLE, 3, 450, Material.GOLDEN_APPLE));
        list.add(mat("easy_56", "Дровосек", "Срубите 128 дубовых бревен", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.OAK_LOG, 128, 450, Material.IRON_AXE));

        list.add(mat("easy_57", "Железный рыцарь", "Скрафтите железный нагрудник", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.IRON_CHESTPLATE, 1, 500, Material.IRON_CHESTPLATE));
        list.add(mat("easy_58", "Вкусный торт", "Скрафтите 2 торта", DifficultyCategory.EASY, GoalType.CRAFT_ITEM, Material.CAKE, 2, 500, Material.CAKE));
        list.add(mat("easy_59", "Подземный рудокоп", "Добудьте 64 глубинного сланца", DifficultyCategory.EASY, GoalType.BREAK_BLOCK, Material.DEEPSLATE, 64, 500, Material.DEEPSLATE));
        list.add(mat("easy_60", "Опытный рыбак", "Поймайте 25 предметов на удочку", DifficultyCategory.EASY, GoalType.FISH_CATCH, null, 25, 500, Material.TROPICAL_FISH));
    }

    private static void addSimpleAchievements(List<Achievement> list) {
        list.add(mat("simple_1", "Первые алмазы", "Добудьте 8 алмазной руды", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.DIAMOND_ORE, 8, 500, Material.DIAMOND));
        list.add(mat("simple_2", "Глубинные алмазы", "Добудьте 16 алмазоносного глубинного сланца", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.DEEPSLATE_DIAMOND_ORE, 16, 500, Material.DEEPSLATE_DIAMOND_ORE));
        list.add(mat("simple_3", "Алмазный инструмент", "Скрафтите алмазную кирку", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.DIAMOND_PICKAXE, 1, 500, Material.DIAMOND_PICKAXE));
        list.add(mat("simple_4", "Магия книг", "Скрафтите стол зачарований", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.ENCHANTING_TABLE, 1, 500, Material.ENCHANTING_TABLE));
        list.add(mat("simple_5", "Библиотекарь", "Скрафтите 15 книжных полок", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.BOOKSHELF, 15, 500, Material.BOOKSHELF));

        list.add(mat("simple_6", "Алхимическая лаборатория", "Скрафтите зельеварку", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.BREWING_STAND, 1, 600, Material.BREWING_STAND));
        list.add(mat("simple_7", "Начинающий алхимик", "Сварите 10 зелий", DifficultyCategory.SIMPLE, GoalType.BREW_POTION, null, 10, 600, Material.POTION));
        list.add(mat("simple_8", "Чародей-новичок", "Зачаруйте 10 предметов", DifficultyCategory.SIMPLE, GoalType.ENCHANT_ITEM, null, 10, 600, Material.ENCHANTED_BOOK));
        list.add(mat("simple_9", "Кузнечное дело", "Скрафтите наковальню", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.ANVIL, 1, 600, Material.ANVIL));
        list.add(mat("simple_10", "Вулканическое стекло", "Добудьте 14 блоков обсидиана", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.OBSIDIAN, 14, 600, Material.OBSIDIAN));

        list.add(mat("simple_11", "Врата в Преисподнюю", "Посетите Нижний мир", DifficultyCategory.SIMPLE, GoalType.ENTER_NETHER, null, 1, 800, Material.NETHERRACK));
        list.add(mat("simple_12", "Адский камень", "Добудьте 256 незерака", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.NETHERRACK, 256, 800, Material.NETHERRACK));
        list.add(mat("simple_13", "Кварцевый магнат", "Добудьте 64 незер-кварцевой руды", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.NETHER_QUARTZ_ORE, 64, 800, Material.QUARTZ));
        list.add(mat("simple_14", "Светокамень", "Добудьте 32 блока светокамня", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.GLOWSTONE, 32, 800, Material.GLOWSTONE_DUST));
        list.add(ent("simple_15", "Охотник на свинозомби", "Убейте 30 зомбифицированных пиглинов", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.ZOMBIFIED_PIGLIN, 30, 800, Material.GOLD_NUGGET));
        list.add(ent("simple_16", "Странник Края", "Убейте 15 эндерменов", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.ENDERMAN, 15, 800, Material.ENDER_PEARL));
        list.add(ent("simple_17", "Утопленники", "Убейте 25 утопленников", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.DROWNED, 25, 800, Material.COPPER_INGOT));
        list.add(ent("simple_18", "Ночные фантомы", "Убейте 10 фантомов", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.PHANTOM, 10, 800, Material.PHANTOM_MEMBRANE));
        list.add(ent("simple_19", "Слизневая охота", "Убейте 25 слизней", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.SLIME, 25, 800, Material.SLIME_BALL));
        list.add(ent("simple_20", "Колдовство", "Убейте 10 ведьм", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.WITCH, 10, 800, Material.GLASS_BOTTLE));

        list.add(ent("simple_21", "Большая ферма", "Разведите 30 животных (овец)", DifficultyCategory.SIMPLE, GoalType.BREED_ENTITY, EntityType.SHEEP, 30, 900, Material.WHITE_WOOL));
        list.add(ent("simple_22", "Конюх", "Приручите 3 лошадей", DifficultyCategory.SIMPLE, GoalType.TAME_ENTITY, EntityType.HORSE, 3, 900, Material.SADDLE));
        list.add(mat("simple_23", "Торговец", "Совершите 30 сделок с жителями", DifficultyCategory.SIMPLE, GoalType.TRADE_VILLAGER, null, 30, 900, Material.EMERALD));
        list.add(mat("simple_24", "Инженер редстоуна", "Скрафтите 16 поршней", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.PISTON, 16, 900, Material.PISTON));
        list.add(mat("simple_25", "Логистика", "Скрафтите 16 воронок", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.HOPPER, 16, 900, Material.HOPPER));
        list.add(mat("simple_26", "Железная дорога", "Скрафтите 128 рельсов", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.RAIL, 128, 900, Material.MINECART));
        list.add(mat("simple_27", "Подзорная труба", "Скрафтите подзорную трубу", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.SPYGLASS, 1, 900, Material.SPYGLASS));
        list.add(mat("simple_28", "Аметистовый звон", "Добудьте 64 блока аметиста", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.AMETHYST_BLOCK, 64, 900, Material.AMETHYST_SHARD));
        list.add(mat("simple_29", "Взрывоопасно", "Скрафтите 32 блока динамита", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.TNT, 32, 900, Material.TNT));
        list.add(mat("simple_30", "Золотая морковь", "Съешьте 32 золотые моркови", DifficultyCategory.SIMPLE, GoalType.CONSUME_ITEM, Material.GOLDEN_CARROT, 32, 900, Material.GOLDEN_CARROT));

        list.add(mat("simple_31", "Мудрец", "Достигните 30-го уровня опыта", DifficultyCategory.SIMPLE, GoalType.GAIN_XP_LEVEL, null, 30, 1000, Material.EXPERIENCE_BOTTLE));
        list.add(mat("simple_32", "Кузнечный ремонт", "Почините или объедините 10 предметов на наковальне", DifficultyCategory.SIMPLE, GoalType.REPAIR_ANVIL, null, 10, 1000, Material.ANVIL));
        list.add(mat("simple_33", "Марафонец", "Пройдите пешком 15 000 блоков", DifficultyCategory.SIMPLE, GoalType.WALK_DISTANCE, null, 15000, 1000, Material.IRON_BOOTS));
        list.add(mat("simple_34", "Стайер", "Пробегите спринтом 20 000 блоков", DifficultyCategory.SIMPLE, GoalType.SPRINT_DISTANCE, null, 20000, 1000, Material.SUGAR));
        list.add(mat("simple_35", "Ихтиолог", "Поймайте 75 предметов на удочку", DifficultyCategory.SIMPLE, GoalType.FISH_CATCH, null, 75, 1000, Material.PUFFERFISH));
        list.add(mat("simple_36", "Пчеловод", "Выпейте 16 бутылочек мёда", DifficultyCategory.SIMPLE, GoalType.CONSUME_ITEM, Material.HONEY_BOTTLE, 16, 1000, Material.HONEY_BOTTLE));
        list.add(mat("simple_37", "Тыквенный пирог", "Съешьте 32 тыквенных пирога", DifficultyCategory.SIMPLE, GoalType.CONSUME_ITEM, Material.PUMPKIN_PIE, 32, 1000, Material.PUMPKIN_PIE));
        list.add(mat("simple_38", "Глубокая шахта", "Добудьте 512 глубинного сланца", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.DEEPSLATE, 512, 1000, Material.DEEPSLATE));
        list.add(mat("simple_39", "Железный магнат", "Скрафтите 16 железных блоков", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.IRON_BLOCK, 16, 1000, Material.IRON_BLOCK));
        list.add(mat("simple_40", "Золотой запас", "Скрафтите 8 золотых блоков", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.GOLD_BLOCK, 8, 1000, Material.GOLD_BLOCK));

        list.add(ent("simple_41", "Защитник деревни", "Убейте 25 разбойников", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.PILLAGER, 25, 1100, Material.CROSSBOW));
        list.add(ent("simple_42", "Зимогор", "Убейте 20 зимогоров", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.STRAY, 20, 1100, Material.TIPPED_ARROW));
        list.add(ent("simple_43", "Пустынный скиталец", "Убейте 25 кадавров", DifficultyCategory.SIMPLE, GoalType.KILL_ENTITY, EntityType.HUSK, 25, 1100, Material.SANDSTONE));
        list.add(mat("simple_44", "Адский нарост", "Соберите 64 адского нароста", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.NETHER_WART, 64, 1100, Material.NETHER_WART));
        list.add(mat("simple_45", "Песок душ", "Добудьте 128 блоков песка душ", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.SOUL_SAND, 128, 1100, Material.SOUL_SAND));

        list.add(mat("simple_46", "Алмазная защита", "Скрафтите алмазный нагрудник", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.DIAMOND_CHESTPLATE, 1, 1200, Material.DIAMOND_CHESTPLATE));
        list.add(mat("simple_47", "Алмазный шлем", "Скрафтите алмазный шлем", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.DIAMOND_HELMET, 1, 1200, Material.DIAMOND_HELMET));
        list.add(mat("simple_48", "Алмазные поножи", "Скрафтите алмазные поножи", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.DIAMOND_LEGGINGS, 1, 1200, Material.DIAMOND_LEGGINGS));
        list.add(mat("simple_49", "Алмазные сапоги", "Скрафтите алмазные ботинки", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.DIAMOND_BOOTS, 1, 1200, Material.DIAMOND_BOOTS));
        list.add(mat("simple_50", "Алмазный клинок", "Скрафтите алмазный меч", DifficultyCategory.SIMPLE, GoalType.CRAFT_ITEM, Material.DIAMOND_SWORD, 1, 1200, Material.DIAMOND_SWORD));
        list.add(mat("simple_51", "Врата Края", "Посетите измерение Край", DifficultyCategory.SIMPLE, GoalType.ENTER_END, null, 1, 1200, Material.END_STONE));
        list.add(mat("simple_52", "Первые обломки", "Добудьте 4 древних обломка", DifficultyCategory.SIMPLE, GoalType.BREAK_BLOCK, Material.ANCIENT_DEBRIS, 4, 1200, Material.ANCIENT_DEBRIS));
        list.add(mat("simple_53", "Зельевар", "Сварите 30 зелий", DifficultyCategory.SIMPLE, GoalType.BREW_POTION, null, 30, 1200, Material.BREWING_STAND));
        list.add(mat("simple_54", "Мастер чар", "Зачаруйте 25 предметов", DifficultyCategory.SIMPLE, GoalType.ENCHANT_ITEM, null, 25, 1200, Material.ENCHANTING_TABLE));
        list.add(mat("simple_55", "Тяжелый щит", "Заблокируйте щитом 75 ударов", DifficultyCategory.SIMPLE, GoalType.SHIELD_BLOCK, null, 75, 1200, Material.SHIELD));
    }

    private static void addMediumAchievements(List<Achievement> list) {
        list.add(ent("medium_1", "Огненный страж", "Убейте 25 ифритов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.BLAZE, 25, 2000, Material.BLAZE_ROD));
        list.add(ent("medium_2", "Охотник на пиглинов", "Убейте 50 пиглинов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.PIGLIN, 50, 2000, Material.GOLD_INGOT));
        list.add(ent("medium_3", "Покоритель бастионов", "Убейте 15 жестоких пиглинов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.PIGLIN_BRUTE, 15, 2000, Material.GOLDEN_AXE));
        list.add(ent("medium_4", "Лавовый прыгун", "Убейте 40 лавовых кубов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.MAGMA_CUBE, 40, 2000, Material.MAGMA_CREAM));
        list.add(ent("medium_5", "Охотник на вепрей", "Убейте 30 хоглинов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.HOGLIN, 30, 2000, Material.PORKCHOP));

        list.add(ent("medium_6", "Слезы Незера", "Убейте 30 гастов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.GHAST, 30, 2500, Material.GHAST_TEAR));
        list.add(ent("medium_7", "Черные кости", "Убейте 40 скелетов-иссушителей", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.WITHER_SKELETON, 40, 2500, Material.WITHER_SKELETON_SKULL));
        list.add(ent("medium_8", "Подводный штурм", "Убейте 50 стражей", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.GUARDIAN, 50, 2500, Material.PRISMARINE_SHARD));
        list.add(ent("medium_9", "Ядовитые укусы", "Убейте 40 пещерных пауков", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.CAVE_SPIDER, 40, 2500, Material.FERMENTED_SPIDER_EYE));
        list.add(ent("medium_10", "Болотные скелеты", "Убейте 30 болотников", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.BOGGED, 30, 2500, Material.MUSHROOM_STEW));

        list.add(mat("medium_11", "Кварцевая лихорадка", "Добудьте 256 незер-кварцевой руды", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.NETHER_QUARTZ_ORE, 256, 3000, Material.QUARTZ_BLOCK));
        list.add(mat("medium_12", "Алмазная жила", "Добудьте 64 алмазоносного глубинного сланца", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.DEEPSLATE_DIAMOND_ORE, 64, 3000, Material.DIAMOND_BLOCK));
        list.add(mat("medium_13", "Обсидиановый шахтер", "Добудьте 128 блоков обсидиана", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.OBSIDIAN, 128, 3000, Material.OBSIDIAN));
        list.add(mat("medium_14", "Чернокаменный карьер", "Добудьте 512 блоков чернита", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.BLACKSTONE, 512, 3000, Material.BLACKSTONE));
        list.add(mat("medium_15", "Строитель из Края", "Добудьте 512 блоков эндерняка", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.END_STONE, 512, 3000, Material.END_STONE));

        list.add(mat("medium_16", "Герой деревни", "Выиграйте 3 рейда на деревню", DifficultyCategory.MEDIUM, GoalType.WIN_RAID, null, 3, 3500, Material.EMERALD_BLOCK));
        list.add(ent("medium_17", "Поборник справедливости", "Убейте 30 поборников", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.VINDICATOR, 30, 3500, Material.IRON_AXE));
        list.add(ent("medium_18", "Охотник на призывателей", "Убейте 10 вызывателей", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.EVOKER, 10, 3500, Material.TOTEM_OF_UNDYING));
        list.add(ent("medium_19", "Укротитель зверей", "Убейте 8 разорителей", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.RAVAGER, 8, 3500, Material.SADDLE));
        list.add(ent("medium_20", "Шалкеровый бой", "Убейте 25 шалкеров", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.SHULKER, 25, 3500, Material.SHULKER_SHELL));

        list.add(mat("medium_21", "Незеритовый старатель", "Добудьте 32 древних обломка", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.ANCIENT_DEBRIS, 32, 4000, Material.ANCIENT_DEBRIS));
        list.add(mat("medium_22", "Первые слитки незерита", "Скрафтите 8 незеритовых слитков", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.NETHERITE_INGOT, 8, 4000, Material.NETHERITE_INGOT));
        list.add(mat("medium_23", "Оптовый торговец", "Совершите 150 торговых сделок с жителями", DifficultyCategory.MEDIUM, GoalType.TRADE_VILLAGER, null, 150, 4000, Material.EMERALD));
        list.add(mat("medium_24", "Мастер-алхимик", "Сварите 100 зелий", DifficultyCategory.MEDIUM, GoalType.BREW_POTION, null, 100, 4000, Material.DRAGON_BREATH));
        list.add(mat("medium_25", "Архимаг", "Зачаруйте 75 предметов", DifficultyCategory.MEDIUM, GoalType.ENCHANT_ITEM, null, 75, 4000, Material.ENCHANTED_BOOK));
        list.add(mat("medium_26", "Кузнец-реставратор", "Почините/объедините 40 предметов на наковальне", DifficultyCategory.MEDIUM, GoalType.REPAIR_ANVIL, null, 40, 4000, Material.ANVIL));
        list.add(mat("medium_27", "Глубоководный пловец", "Проплывите 10 000 блоков", DifficultyCategory.MEDIUM, GoalType.SWIM_DISTANCE, null, 10000, 4000, Material.HEART_OF_THE_SEA));
        list.add(mat("medium_28", "Дальний поход", "Пройдите пешком 50 000 блоков", DifficultyCategory.MEDIUM, GoalType.WALK_DISTANCE, null, 50000, 4000, Material.DIAMOND_BOOTS));
        list.add(mat("medium_29", "Рыболов-профи", "Поймайте 250 предметов на удочку", DifficultyCategory.MEDIUM, GoalType.FISH_CATCH, null, 250, 4000, Material.FISHING_ROD));
        list.add(mat("medium_30", "Повелитель ветра", "Убейте 20 вихрей (Breeze)", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.BREEZE, 20, 4000, Material.BREEZE_ROD));

        list.add(mat("medium_31", "Шалкеровый склад", "Скрафтите 12 шалкеровых ящиков", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.SHULKER_BOX, 12, 4500, Material.SHULKER_BOX));
        list.add(mat("medium_32", "Морской источник", "Скрафтите морской проводник", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.CONDUIT, 1, 4500, Material.CONDUIT));
        list.add(mat("medium_33", "Кристаллы Края", "Скрафтите 16 кристаллов Края", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.END_CRYSTAL, 16, 4500, Material.END_CRYSTAL));
        list.add(mat("medium_34", "Автоматизация", "Скрафтите 16 сборщиков (Crafter)", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.CRAFTER, 16, 4500, Material.CRAFTER));
        list.add(mat("medium_35", "Якорь возрождения", "Скрафтите 4 якоря возрождения", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.RESPAWN_ANCHOR, 4, 4500, Material.RESPAWN_ANCHOR));

        list.add(ent("medium_36", "Истребитель эндерменов", "Убейте 150 эндерменов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.ENDERMAN, 150, 5000, Material.ENDER_EYE));
        list.add(ent("medium_37", "Чистильщик пещер", "Убейте 250 зомби", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.ZOMBIE, 250, 5000, Material.IRON_SWORD));
        list.add(ent("medium_38", "Костяной громила", "Убейте 250 скелетов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.SKELETON, 250, 5000, Material.BOW));
        list.add(ent("medium_39", "Гроза криперов", "Убейте 150 криперов", DifficultyCategory.MEDIUM, GoalType.KILL_ENTITY, EntityType.CREEPER, 150, 5000, Material.TNT));
        list.add(mat("medium_40", "Опытный ветеран", "Достигните 50-го уровня опыта", DifficultyCategory.MEDIUM, GoalType.GAIN_XP_LEVEL, null, 50, 5000, Material.EXPERIENCE_BOTTLE));

        list.add(mat("medium_41", "Скалк-археолог", "Добудьте 256 блоков скалка", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.SCULK, 256, 5500, Material.SCULK_CATALYST));
        list.add(mat("medium_42", "Незеритовый рудокоп", "Добудьте 64 древних обломка", DifficultyCategory.MEDIUM, GoalType.BREAK_BLOCK, Material.ANCIENT_DEBRIS, 64, 5500, Material.ANCIENT_DEBRIS));
        list.add(mat("medium_43", "Фейерверки в небо", "Скрафтите 256 фейерверков", DifficultyCategory.MEDIUM, GoalType.CRAFT_ITEM, Material.FIREWORK_ROCKET, 256, 5500, Material.FIREWORK_ROCKET));
        list.add(mat("medium_44", "Первый полет", "Пролетите на элитрах 25 000 блоков", DifficultyCategory.MEDIUM, GoalType.FLY_ELYTRA, null, 25000, 5500, Material.ELYTRA));
        list.add(mat("medium_45", "Абсолютная защита", "Заблокируйте щитом 300 ударов", DifficultyCategory.MEDIUM, GoalType.SHIELD_BLOCK, null, 300, 5500, Material.SHIELD));
    }

    private static void addHardAchievements(List<Achievement> list) {
        list.add(ent("hard_1", "Владыка океана", "Убейте 3 древних стражей", DifficultyCategory.HARD, GoalType.KILL_ENTITY, EntityType.ELDER_GUARDIAN, 3, 7000, Material.SPONGE));
        list.add(mat("hard_2", "Спаситель империи", "Выиграйте 10 рейдов на деревню", DifficultyCategory.HARD, GoalType.WIN_RAID, null, 10, 7000, Material.TOTEM_OF_UNDYING));
        list.add(mat("hard_3", "Зачарованное яблоко", "Съешьте зачарованное золотое яблоко", DifficultyCategory.HARD, GoalType.CONSUME_ITEM, Material.ENCHANTED_GOLDEN_APPLE, 1, 7000, Material.ENCHANTED_GOLDEN_APPLE));
        list.add(mat("hard_4", "Компас возврата", "Скрафтите компас восстановления", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.RECOVERY_COMPASS, 1, 7000, Material.RECOVERY_COMPASS));
        list.add(mat("hard_5", "Сокрушитель", "Скрафтите булаву (Mace)", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.MACE, 1, 7000, Material.MACE));

        list.add(mat("hard_6", "Незеритовый шлем", "Скрафтите незеритовый шлем", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.NETHERITE_HELMET, 1, 9000, Material.NETHERITE_HELMET));
        list.add(mat("hard_7", "Незеритовый нагрудник", "Скрафтите незеритовый нагрудник", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.NETHERITE_CHESTPLATE, 1, 9000, Material.NETHERITE_CHESTPLATE));
        list.add(mat("hard_8", "Незеритовые поножи", "Скрафтите незеритовые поножи", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.NETHERITE_LEGGINGS, 1, 9000, Material.NETHERITE_LEGGINGS));
        list.add(mat("hard_9", "Незеритовые ботинки", "Скрафтите незеритовые ботинки", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.NETHERITE_BOOTS, 1, 9000, Material.NETHERITE_BOOTS));
        list.add(mat("hard_10", "Незеритовый меч", "Скрафтите незеритовый меч", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.NETHERITE_SWORD, 1, 9000, Material.NETHERITE_SWORD));

        list.add(ent("hard_11", "Освободитель Края", "Убейте Эндер-дракона", DifficultyCategory.HARD, GoalType.KILL_ENTITY, EntityType.ENDER_DRAGON, 1, 10000, Material.DRAGON_EGG));
        list.add(mat("hard_12", "Небесный странник", "Пролетите на элитрах 150 000 блоков", DifficultyCategory.HARD, GoalType.FLY_ELYTRA, null, 150000, 10000, Material.ELYTRA));
        list.add(mat("hard_13", "Обсидиановая стена", "Добудьте 1 000 блоков обсидиана", DifficultyCategory.HARD, GoalType.BREAK_BLOCK, Material.OBSIDIAN, 1000, 10000, Material.CRYING_OBSIDIAN));
        list.add(mat("hard_14", "Алмазная империя", "Добудьте 256 алмазоносного глубинного сланца", DifficultyCategory.HARD, GoalType.BREAK_BLOCK, Material.DEEPSLATE_DIAMOND_ORE, 256, 10000, Material.DIAMOND_BLOCK));
        list.add(mat("hard_15", "Мастер-кузнец", "Скрафтите 24 незеритовых слитка", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.NETHERITE_INGOT, 24, 10000, Material.NETHERITE_INGOT));

        list.add(ent("hard_16", "Иссушение", "Убейте Визера", DifficultyCategory.HARD, GoalType.KILL_ENTITY, EntityType.WITHER, 1, 12000, Material.NETHER_STAR));
        list.add(mat("hard_17", "Да будет маяк!", "Скрафтите маяк", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.BEACON, 1, 12000, Material.BEACON));
        list.add(mat("hard_18", "Гильдия торговцев", "Совершите 500 торговых сделок с жителями", DifficultyCategory.HARD, GoalType.TRADE_VILLAGER, null, 500, 12000, Material.EMERALD_BLOCK));
        list.add(mat("hard_19", "Великий алхимик", "Сварите 300 зелий", DifficultyCategory.HARD, GoalType.BREW_POTION, null, 300, 12000, Material.BREWING_STAND));
        list.add(mat("hard_20", "Легенда опыта", "Достигните 100-го уровня опыта", DifficultyCategory.HARD, GoalType.GAIN_XP_LEVEL, null, 100, 12000, Material.EXPERIENCE_BOTTLE));

        list.add(ent("hard_21", "Покоритель Древнего города", "Убейте Хранителя (Warden)", DifficultyCategory.HARD, GoalType.KILL_ENTITY, EntityType.WARDEN, 1, 15000, Material.ECHO_SHARD));
        list.add(ent("hard_22", "Охотник на драконов", "Убейте 3 Эндер-драконов", DifficultyCategory.HARD, GoalType.KILL_ENTITY, EntityType.ENDER_DRAGON, 3, 15000, Material.DRAGON_HEAD));
        list.add(mat("hard_23", "Адский бурильщик", "Добудьте 128 древних обломков", DifficultyCategory.HARD, GoalType.BREAK_BLOCK, Material.ANCIENT_DEBRIS, 128, 15000, Material.NETHERITE_PICKAXE));
        list.add(mat("hard_24", "Истребитель нежити", "Убейте 1 000 зомби", DifficultyCategory.HARD, GoalType.KILL_ENTITY, EntityType.ZOMBIE, 1000, 15000, Material.ZOMBIE_HEAD));
        list.add(mat("hard_25", "Шалкеровый магнат", "Скрафтите 54 шалкеровых ящика", DifficultyCategory.HARD, GoalType.CRAFT_ITEM, Material.SHULKER_BOX, 54, 15000, Material.SHULKER_BOX));
    }

    private static void addImpossibleAchievements(List<Achievement> list) {
        list.add(mat("impossible_1", "Посейдон", "Выловите 1 000 предметов на удочку", DifficultyCategory.IMPOSSIBLE, GoalType.FISH_CATCH, null, 1000, 20000, Material.FISHING_ROD));
        list.add(mat("impossible_2", "Великий зачарователь", "Зачаруйте 500 предметов", DifficultyCategory.IMPOSSIBLE, GoalType.ENCHANT_ITEM, null, 500, 20000, Material.ENCHANTING_TABLE));
        list.add(mat("impossible_3", "Монополия деревни", "Совершите 2 000 торговых сделок с жителями", DifficultyCategory.IMPOSSIBLE, GoalType.TRADE_VILLAGER, null, 2000, 20000, Material.EMERALD_BLOCK));

        list.add(mat("impossible_4", "Железный человек", "Пройдите пешком 500 000 блоков", DifficultyCategory.IMPOSSIBLE, GoalType.WALK_DISTANCE, null, 500000, 25000, Material.NETHERITE_BOOTS));
        list.add(mat("impossible_5", "Неудержимый спринт", "Пробегите спринтом 500 000 блоков", DifficultyCategory.IMPOSSIBLE, GoalType.SPRINT_DISTANCE, null, 500000, 25000, Material.GOLDEN_BOOTS));
        list.add(mat("impossible_6", "Глубинный бурильщик", "Добудьте 50 000 блоков глубинного сланца", DifficultyCategory.IMPOSSIBLE, GoalType.BREAK_BLOCK, Material.DEEPSLATE, 50000, 25000, Material.NETHERITE_PICKAXE));

        list.add(mat("impossible_7", "Обсидиановый титан", "Добудьте 25 000 блоков обсидиана", DifficultyCategory.IMPOSSIBLE, GoalType.BREAK_BLOCK, Material.OBSIDIAN, 25000, 30000, Material.OBSIDIAN));
        list.add(mat("impossible_8", "Кварцевый император", "Добудьте 10 000 незер-кварцевой руды", DifficultyCategory.IMPOSSIBLE, GoalType.BREAK_BLOCK, Material.NETHER_QUARTZ_ORE, 10000, 30000, Material.QUARTZ_BLOCK));
        list.add(ent("impossible_9", "Гроза Края", "Убейте 2 500 эндерменов", DifficultyCategory.IMPOSSIBLE, GoalType.KILL_ENTITY, EntityType.ENDERMAN, 2500, 35000, Material.ENDER_PEARL));

        list.add(ent("impossible_10", "Палач Иссушителей", "Убейте 10 Визеров", DifficultyCategory.IMPOSSIBLE, GoalType.KILL_ENTITY, EntityType.WITHER, 10, 40000, Material.NETHER_STAR));
        list.add(ent("impossible_11", "Повелитель Бездны", "Убейте 5 Хранителей (Warden)", DifficultyCategory.IMPOSSIBLE, GoalType.KILL_ENTITY, EntityType.WARDEN, 5, 40000, Material.SCULK_SHRIEKER));
        list.add(ent("impossible_12", "Геноцид нежити", "Убейте 5 000 зомби", DifficultyCategory.IMPOSSIBLE, GoalType.KILL_ENTITY, EntityType.ZOMBIE, 5000, 40000, Material.NETHERITE_SWORD));

        list.add(mat("impossible_13", "Покоритель стратосферы", "Пролетите на элитрах 1 250 000 блоков", DifficultyCategory.IMPOSSIBLE, GoalType.FLY_ELYTRA, null, 1250000, 50000, Material.ELYTRA));
        list.add(ent("impossible_14", "Убийца драконов", "Убейте 10 Эндер-драконов", DifficultyCategory.IMPOSSIBLE, GoalType.KILL_ENTITY, EntityType.ENDER_DRAGON, 10, 50000, Material.DRAGON_EGG));
        list.add(mat("impossible_15", "Сердце незерита", "Добудьте 512 древних обломков", DifficultyCategory.IMPOSSIBLE, GoalType.BREAK_BLOCK, Material.ANCIENT_DEBRIS, 512, 50000, Material.NETHERITE_BLOCK));
    }

    private static Achievement mat(String id, String name, String desc, DifficultyCategory cat,
                                   GoalType type, Material targetMat, int amount, double reward, Material icon) {
        return new Achievement(id, name, desc, cat, type, targetMat, null, amount, reward, icon);
    }

    private static Achievement ent(String id, String name, String desc, DifficultyCategory cat,
                                   GoalType type, EntityType targetEnt, int amount, double reward, Material icon) {
        return new Achievement(id, name, desc, cat, type, null, targetEnt, amount, reward, icon);
    }
}
