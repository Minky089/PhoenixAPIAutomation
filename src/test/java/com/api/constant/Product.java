package com.api.constant;

public enum Product {
    NEXUS_2(1), PIXEL(2);

    final int id;

    private Product(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
