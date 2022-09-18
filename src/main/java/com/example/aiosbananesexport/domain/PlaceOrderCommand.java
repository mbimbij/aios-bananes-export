package com.example.aiosbananesexport.domain;

import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
@With
public class PlaceOrderCommand {
    String firstName;
    String lastName;
    String address;
    int postalCode;
    String city;
    String country;
    LocalDate deliveryDate;
    int quantityKg;
}
