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
    int deliveryMinDelayDays;
    int quantityKg;
    private final int orderIncrementQuantityKg;
    private final int orderMinQuantityKg;
    private final int orderMaxQuantityKg;
    double price;

    public void validate() throws OrderDeliveryTooEarlyException, OrderQuantityNotInRangeException, OrderQuantityNotMultipleOfIncrementException {
        if (deliveryDateTooEarly()) {
            throw new OrderDeliveryTooEarlyException(this);
        }
        if (!quantityInAllowedRange()) {
            throw new OrderQuantityNotInRangeException(this);
        }
        if (!quantityMultipleOfAllowedIncrement()) {
            throw new OrderQuantityNotMultipleOfIncrementException(this);
        }
    }

    private boolean quantityMultipleOfAllowedIncrement() {
        return quantityKg % orderIncrementQuantityKg==0;
    }

    private boolean quantityInAllowedRange() {
        return quantityKg > orderMinQuantityKg && quantityKg <= orderMaxQuantityKg;
    }

    private boolean deliveryDateTooEarly() {
        return deliveryDate.isBefore(LocalDate.now().plusDays(deliveryMinDelayDays));
    }


}
