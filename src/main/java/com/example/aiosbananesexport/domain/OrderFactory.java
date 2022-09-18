package com.example.aiosbananesexport.domain;

import java.util.UUID;

public class OrderFactory {
    Order createOrder(PlaceOrderCommand requestDto) {
        return new Order(generateId(),
                         requestDto.getFirstName(),
                         requestDto.getLastName(),
                         requestDto.getAddress(),
                         requestDto.getPostalCode(),
                         requestDto.getCity(),
                         requestDto.getCountry(),
                         requestDto.getDeliveryDate(),
                         requestDto.getQuantityKg(),
                         requestDto.getQuantityKg() * 2.5);
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
