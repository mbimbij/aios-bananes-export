package com.example.aiosbananesexport.order.domain;

import com.example.aiosbananesexport.common.ValueObject;

public class PricePerKilogram extends ValueObject<Double> {
    public PricePerKilogram(Double value) {
        super(value);
    }
}
