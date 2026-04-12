package pojo;

public record CustomerAddress(
        String flat_number,
        String apartment_name,
        String street_name,
        String landmark,
        String area,
        int pincode,
        String country,
        String state) {}