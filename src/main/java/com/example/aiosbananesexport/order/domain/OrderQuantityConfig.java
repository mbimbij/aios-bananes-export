package com.example.aiosbananesexport.order.domain;

import lombok.Value;

@Value
public class OrderQuantityConfig {
    int minOrderQuantityKg;
    int maxOrderQuantityKg;
    int incrementOrderQuantityKg;
}
