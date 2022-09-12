package com.example.aiosbananesexport.order.config;

import com.example.aiosbananesexport.order.domain.OrderQuantityConfig;
import com.example.aiosbananesexport.order.domain.PricePerKilogram;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
class OrderQuantityConfigurationsShould {
    @Autowired
    private OrderConfigurationProperties orderConfigurationProperties;
    @Autowired
    private OrderQuantityConfig orderQuantityConfig;
    @Autowired
    private PricePerKilogram pricePerKilogram;

    @Test
    void loadOrderQuantityConfig() {
        double pricePerKg = 2.5;
        int minOrderQuantityKg = 0;
        int maxOrderQuantityKg = 10_000;
        int incrementOrderQuantityKg = 25;

        OrderConfigurationProperties expectedConfigProps = new OrderConfigurationProperties(pricePerKg, minOrderQuantityKg, maxOrderQuantityKg, incrementOrderQuantityKg);
        OrderQuantityConfig expectedConfig = new OrderQuantityConfig(minOrderQuantityKg, maxOrderQuantityKg, incrementOrderQuantityKg);

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(orderConfigurationProperties)
                          .usingRecursiveComparison()
                          .isEqualTo(expectedConfigProps);

            softAssertions.assertThat(orderQuantityConfig)
                          .usingRecursiveComparison()
                          .isEqualTo(expectedConfig);

            softAssertions.assertThat(pricePerKilogram).isEqualTo(new PricePerKilogram(2.5));
        });
    }
}
