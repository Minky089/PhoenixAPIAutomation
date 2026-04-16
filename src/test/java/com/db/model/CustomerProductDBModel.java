package com.db.model;

public record CustomerProductDBModel(
        String dop,
        String serial_number,
        String imei1,
        String imei2,
        String popurl,
        int product_id,
        int mst_model_id) {
}
