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
    OrderQuantity orderQuantity;
    PriceEuro priceEuro;

    public void validate() throws OrderDeliveryTooEarlyException, OrderQuantityNotInRangeException, OrderQuantityNotMultipleOfIncrementException {
        if (deliveryDateTooEarly()) {
            throw new OrderDeliveryTooEarlyException(this);
        }
        orderQuantity.validate();
    }

    private boolean deliveryDateTooEarly() {
        return deliveryDate.isBefore(LocalDate.now().plusDays(deliveryMinDelayDays));
    }


}
