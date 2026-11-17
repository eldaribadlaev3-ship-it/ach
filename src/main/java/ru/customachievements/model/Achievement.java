package ru.customachievements.model;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Achievement {

    private final String id;
    private String name;
    private String description;
    private DifficultyCategory category;
    private GoalType goalType;
    private Material targetMaterial;
    private EntityType targetEntity;
    private int targetAmount;
    private double reward;
    private Material icon;

    public Achievement(String id, String name, String description, DifficultyCategory category,
                       GoalType goalType, Material targetMaterial, EntityType targetEntity,
                       int targetAmount, double reward, Material icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.goalType = goalType;
        this.targetMaterial = targetMaterial;
        this.targetEntity = targetEntity;
        this.targetAmount = Math.max(1, targetAmount);
        this.reward = Math.max(0, reward);
        this.icon = (icon != null && icon.isItem()) ? icon : Material.PAPER;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public DifficultyCategory getCategory() { return category; }
    public void setCategory(DifficultyCategory category) { this.category = category; }
    public GoalType getGoalType() { return goalType; }
    public void setGoalType(GoalType goalType) { this.goalType = goalType; }
    public Material getTargetMaterial() { return targetMaterial; }
    public void setTargetMaterial(Material targetMaterial) { this.targetMaterial = targetMaterial; }
    public EntityType getTargetEntity() { return targetEntity; }
    public void setTargetEntity(EntityType targetEntity) { this.targetEntity = targetEntity; }
    public int getTargetAmount() { return targetAmount; }
    public void setTargetAmount(int targetAmount) { this.targetAmount = Math.max(1, targetAmount); }
    public double getReward() { return reward; }
    public void setReward(double reward) { this.reward = Math.max(0, reward); }
    public Material getIcon() { return icon; }
    public void setIcon(Material icon) {
        if (icon != null && icon.isItem()) {
            this.icon = icon;
        }
    }
}
