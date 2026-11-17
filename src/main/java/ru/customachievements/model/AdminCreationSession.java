package ru.customachievements.model;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class AdminCreationSession {

    public enum Step {
        SELECT_CATEGORY,
        SELECT_GOAL_TYPE,
        INPUT_TARGET,
        INPUT_NAME,
        INPUT_DESCRIPTION,
        INPUT_AMOUNT,
        INPUT_REWARD,
        CONFIRM_ICON
    }

    private Step step = Step.SELECT_CATEGORY;
    private DifficultyCategory category = DifficultyCategory.EASY;
    private GoalType goalType = GoalType.BREAK_BLOCK;
    private Material targetMaterial = null;
    private EntityType targetEntity = null;
    private String name = "Новое достижение";
    private String description = "Выполните условие достижения";
    private int targetAmount = 10;
    private double reward = 500.0;
    private Material icon = Material.NETHER_STAR;

    public Step getStep() { return step; }
    public void setStep(Step step) { this.step = step; }
    public DifficultyCategory getCategory() { return category; }
    public void setCategory(DifficultyCategory category) { this.category = category; }
    public GoalType getGoalType() { return goalType; }
    public void setGoalType(GoalType goalType) { this.goalType = goalType; }
    public Material getTargetMaterial() { return targetMaterial; }
    public void setTargetMaterial(Material targetMaterial) { this.targetMaterial = targetMaterial; }
    public EntityType getTargetEntity() { return targetEntity; }
    public void setTargetEntity(EntityType targetEntity) { this.targetEntity = targetEntity; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getTargetAmount() { return targetAmount; }
    public void setTargetAmount(int targetAmount) { this.targetAmount = targetAmount; }
    public double getReward() { return reward; }
    public void setReward(double reward) { this.reward = reward; }
    public Material getIcon() { return icon; }
    public void setIcon(Material icon) { this.icon = icon; }
}
