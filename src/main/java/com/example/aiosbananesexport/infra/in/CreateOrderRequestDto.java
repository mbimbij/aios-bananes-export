package com.example.aiosbananesexport.infra.in;

import lombok.Value;
import lombok.With;

@Value
@With
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
