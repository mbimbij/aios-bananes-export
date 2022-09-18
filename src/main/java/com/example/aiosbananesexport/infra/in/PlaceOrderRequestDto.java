package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.PlaceOrderCommand;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
@With
public class PlaceOrderRequestDto {
    String firstName;
    String lastName;
    String address;
    int postalCode;
    String city;
    String country;
    String deliveryDate;
    int quantityKg;

    public PlaceOrderCommand toDomainCommand() {
        return new PlaceOrderCommand(firstName,
                                     lastName,
                                     address,
                                     postalCode,
                                     city,
                                     country,
                                     LocalDate.parse(deliveryDate, DateTimeFormat.DATE_FORMATTER),
                                     quantityKg);
    }
}
