package com.example.aiosbananesexport.domain;

import lombok.*;
import lombok.experimental.Wither;

import java.time.LocalDate;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@With
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

    public void validate() throws OrderDeliveryTooEarlyException {
        if (deliveryDate.isBefore(LocalDate.now().plusWeeks(1))) {
            throw new OrderDeliveryTooEarlyException(this);
        }
    }
}
