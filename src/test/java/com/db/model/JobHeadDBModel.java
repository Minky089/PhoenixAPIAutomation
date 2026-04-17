package com.db.model;

public record JobHeadDBModel(
        int id,
        String job_number,
        int tr_customer_id,
        int tr_customer_product_id,
        int mst_service_location_id,
        int mst_platform_id,
        int mst_warrenty_status_id,
        int mst_oem_id) {
}
