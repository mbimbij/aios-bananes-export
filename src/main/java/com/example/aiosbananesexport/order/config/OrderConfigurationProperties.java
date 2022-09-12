package com.example.aiosbananesexport.order.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderConfigurationProperties {
    private double pricePerKilogram;
    private int minOrderQuantityKg;
    private int maxOrderQuantityKg;
    private int incrementOrderQuantityKg;
}
