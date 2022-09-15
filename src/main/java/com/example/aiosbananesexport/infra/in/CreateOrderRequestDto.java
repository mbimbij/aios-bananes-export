package com.example.aiosbananesexport.infra.in;

import lombok.Value;

@Value
public class CreateOrderRequestDto {
    String firstName;
    String lastName;
    String address;
    int postalCode;
    String city;
    String country;
    String deliveryDate;
    int quantityKg;
}
