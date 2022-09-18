package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PlaceOrderResponseDto {
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

    public PlaceOrderResponseDto(Order order) {
        this(order.getId(),
             order.getFirstName(),
             order.getLastName(),
             order.getAddress(),
             order.getPostalCode(),
             order.getCity(),
             order.getCountry(),
             order.getDeliveryDate().format(DateTimeFormat.DATE_FORMATTER),
             order.getQuantityKg(),
             String.format("%.2f", order.getPrice()));
    }
}
