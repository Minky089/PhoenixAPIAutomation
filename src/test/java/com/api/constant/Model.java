package com.api.constant;

public enum Model {
    NEXUS_2_BLUE(1), GALAXY(2);

    final int id;

    private Model(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
