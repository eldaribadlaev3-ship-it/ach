package ru.customachievements.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerAchievementData {

    private final UUID playerUuid;
    private final Map<String, Integer> progressMap = new HashMap<>();
    private final Set<String> completedIds = new HashSet<>();
    private final Set<String> claimedIds = new HashSet<>();
    private boolean dirty = false;

    public PlayerAchievementData(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public UUID getPlayerUuid() { return playerUuid; }

    public int getProgress(String achievementId) {
        return progressMap.getOrDefault(achievementId, 0);
    }

    public void setProgress(String achievementId, int value) {
        progressMap.put(achievementId, Math.max(0, value));
        this.dirty = true;
    }

    public boolean isCompleted(String achievementId) {
        return completedIds.contains(achievementId);
    }

    public void setCompleted(String achievementId, boolean completed) {
        if (completed) {
            completedIds.add(achievementId);
        } else {
            completedIds.remove(achievementId);
        }
        this.dirty = true;
    }

    public boolean isClaimed(String achievementId) {
        return claimedIds.contains(achievementId);
    }

    public void setClaimed(String achievementId, boolean claimed) {
        if (claimed) {
            claimedIds.add(achievementId);
        } else {
            claimedIds.remove(achievementId);
        }
        this.dirty = true;
    }

    public Map<String, Integer> getProgressMap() { return progressMap; }
    public Set<String> getCompletedIds() { return completedIds; }
    public Set<String> getClaimedIds() { return claimedIds; }
    public boolean isDirty() { return dirty; }
    public void setDirty(boolean dirty) { this.dirty = dirty; }
}
