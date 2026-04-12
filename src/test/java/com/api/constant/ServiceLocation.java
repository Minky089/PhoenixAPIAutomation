package com.api.constant;

public enum ServiceLocation {
    SERVICE_LOCATION_A(1),
    SERVICE_LOCATION_B(2),
    SERVICE_LOCATION_C(3);

    final int id;

    private ServiceLocation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
