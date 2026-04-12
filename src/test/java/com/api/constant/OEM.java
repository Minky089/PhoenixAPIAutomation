package com.api.constant;

public enum OEM {
    GOOGLE(1),
    APPLE(2);

    final int id;

    private OEM(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
