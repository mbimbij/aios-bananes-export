package com.example.aiosbananesexport.infra.in;

import lombok.Value;

@Value
public class CreateOrderResponseDto {
    String id;
    String firstName;
    String lastName;
    String address;
    int postalCode;
    String city;
    String country;
    String deliveryDate;
    int quantityKg;
    String price;
}
