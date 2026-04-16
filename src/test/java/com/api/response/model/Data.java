package com.api.response.model;

public record Data(int id,
                   int mst_service_location_id,
                   int mst_platform_id,
                   int mst_warrenty_status_id,
                   int mst_oem_id,
                   int tr_customer_id,
                   int tr_customer_product_id,
                   String job_number) {}
