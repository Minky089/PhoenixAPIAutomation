package com.api.constant;

public enum Problem {
    SMART_PHONE_IS_RUNNING_SLOW(1),
    POOR_BATTERY_LIFE(2),
    PHONE_OR_APP_CRASHES(3),
    SYNC_ISSUE(4),
    MICRO_SD_CARD_IS_NOT_WORKING(5),
    OVERHEATING(6);

    final int id;

    private Problem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
