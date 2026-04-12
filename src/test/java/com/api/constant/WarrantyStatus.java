package com.api.constant;

public enum WarrantyStatus {
    IN_WARRANTY(1),
    OUT_WARRANTY(2);

    final int id;

    private WarrantyStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
