package com.db.model;

public record CustomerDBModel(
        int id,
        String first_name,
        String last_name,
        String mobile_number,
        String mobile_number_alt,
        String email_id,
        String email_id_alt,
        int tr_customer_address_id) {
}
