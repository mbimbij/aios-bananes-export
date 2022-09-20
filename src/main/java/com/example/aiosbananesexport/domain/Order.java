package com.example.aiosbananesexport.domain;

import lombok.*;

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
    DeliveryDate deliveryDate;
    OrderQuantity orderQuantity;
    PriceEuro priceEuro;

    public void validate() throws OrderDeliveryTooEarlyException, OrderQuantityNotInRangeException, OrderQuantityNotMultipleOfIncrementException {
        deliveryDate.validate();
        orderQuantity.validate();
    }
}
