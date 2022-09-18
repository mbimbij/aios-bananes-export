package com.example.aiosbananesexport;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order")
@Data
public class OrderConfigurationProperties {
    private double pricePerKgEuro;
    private int minQuantityKg;
    private int maxQuantityKg;
    private int incrementQuantityKg;
}
