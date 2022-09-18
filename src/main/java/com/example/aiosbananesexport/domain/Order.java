package com.example.aiosbananesexport.domain;

import lombok.*;

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

    public void validate() throws OrderDeliveryTooEarlyException, OrderQuantityNotInRangeException {
        if (deliveryDateTooEarly()) {
            throw new OrderDeliveryTooEarlyException(this);
        }
        if (quantityKg <= 0 || quantityKg > 10000) {
            throw new OrderQuantityNotInRangeException(this);
        }
    }

    private boolean deliveryDateTooEarly() {
        return deliveryDate.isBefore(LocalDate.now().plusWeeks(1));
    }
}
