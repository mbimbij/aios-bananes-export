package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.example.aiosbananesexport.infra.in.DateTimeFormat.DATE_PATTERN;

public class OrderFactory {
    Order createOrder(CreateOrderRequestDto requestDto) {
        return new Order(generateId(),
                         requestDto.getFirstName(),
                         requestDto.getLastName(),
                         requestDto.getAddress(),
                         requestDto.getPostalCode(),
                         requestDto.getCity(),
                         requestDto.getCountry(),
                         LocalDate.parse(requestDto.getDeliveryDate(),
                                         DateTimeFormatter.ofPattern(DATE_PATTERN)),
                         requestDto.getQuantityKg(),
                         requestDto.getQuantityKg() * 2.5);
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
