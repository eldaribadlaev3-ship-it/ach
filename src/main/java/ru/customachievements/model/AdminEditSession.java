package ru.customachievements.model;

public class AdminEditSession {

    public enum EditField {
        REWARD,
        TARGET_AMOUNT,
        NAME,
        DESCRIPTION
    }

    private final String achievementId;
    private final EditField field;

    public AdminEditSession(String achievementId, EditField field) {
        this.achievementId = achievementId;
        this.field = field;
    }

    public String getAchievementId() { return achievementId; }
    public EditField getField() { return field; }
}
