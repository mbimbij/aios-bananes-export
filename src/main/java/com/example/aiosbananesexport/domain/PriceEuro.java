package com.example.aiosbananesexport.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PriceEuro {
    double value;

    public PriceEuro(double pricePerKgEuro, int quantity) {
        value = pricePerKgEuro * quantity;
    }

    public String formatValue(String format) {
        return String.format(format, value);
    }
}
