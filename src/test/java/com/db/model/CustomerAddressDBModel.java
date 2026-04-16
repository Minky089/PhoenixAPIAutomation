package com.db.model;

public record CustomerAddressDBModel(
        int id,
        String flat_number,
        String apartment_name,
        String street_name,
        String landmark,
        String area,
        String pincode,
        String country,
        String state)  {
}
