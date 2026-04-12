package com.api.constant;

public enum Platform {
    FST(3), FRONT_DESK(2);

    final int id;

    private Platform(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
