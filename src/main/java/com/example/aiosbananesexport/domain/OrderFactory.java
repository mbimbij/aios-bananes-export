package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderFactory {
    Order createOrder(CreateOrderRequestDto requestDto) {
        return new Order("anyOrderid",
                         requestDto.getFirstName(),
                         requestDto.getLastName(),
                         requestDto.getAddress(),
                         requestDto.getPostalCode(),
                         requestDto.getCity(),
                         requestDto.getCountry(),
                         LocalDate.parse(
                                        requestDto.getDeliveryDate(),
                                        DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                         requestDto.getQuantityKg(),
                                requestDto.getQuantityKg() * 2.5);
    }
}
