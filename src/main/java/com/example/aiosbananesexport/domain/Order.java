package com.example.aiosbananesexport.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Order {
    String id;
    String firstName;
    String lastName;
    String address;
    int postalCode;
    String city;
    String country;
    LocalDate deliveryDate;
    int quantityKg;
    double price;
}
