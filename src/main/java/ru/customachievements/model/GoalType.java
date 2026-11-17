package ru.customachievements.model;

public enum GoalType {
    BREAK_BLOCK("Сломать блоки", true, false),
    PLACE_BLOCK("Поставить блоки", true, false),
    CRAFT_ITEM("Скрафтить предметы", true, false),
    SMELT_ITEM("Переплавить предметы", true, false),
    KILL_ENTITY("Убить существ", false, true),
    WALK_DISTANCE("Пройти пешком (блоков)", false, false),
    SPRINT_DISTANCE("Пробежать спринтом (блоков)", false, false),
    SWIM_DISTANCE("Проплыть (блоков)", false, false),
    FLY_ELYTRA("Пролететь на элитрах (блоков)", false, false),
    FISH_CATCH("Поймать на удочку", false, false),
    BREW_POTION("Сварить зелья", false, false),
    ENCHANT_ITEM("Зачаровать предметы", false, false),
    CONSUME_ITEM("Съесть / выпить предмет", true, false),
    TAME_ENTITY("Приручить животных", false, true),
    BREED_ENTITY("Развести животных", false, true),
    SHEAR_SHEEP("Постричь овец", false, false),
    TRADE_VILLAGER("Совершить торгов с жителями", false, false),
    WIN_RAID("Выиграть рейды", false, false),
    ENTER_NETHER("Посетить Нижний мир", false, false),
    ENTER_END("Посетить Край", false, false),
    GAIN_XP_LEVEL("Достичь уровня опыта", false, false),
    REPAIR_ANVIL("Починить/объединить на наковальне", false, false),
    SHIELD_BLOCK("Заблокировать урон щитом", false, false);

    private final String description;
    private final boolean requiresMaterial;
    private final boolean requiresEntity;

    GoalType(String description, boolean requiresMaterial, boolean requiresEntity) {
        this.description = description;
        this.requiresMaterial = requiresMaterial;
        this.requiresEntity = requiresEntity;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequiresMaterial() {
        return requiresMaterial;
    }

    public boolean isRequiresEntity() {
        return requiresEntity;
    }

    public static GoalType fromString(String value) {
        if (value == null) return BREAK_BLOCK;
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return BREAK_BLOCK;
        }
    }
}
