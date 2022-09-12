package com.example.aiosbananesexport.order.config;

import com.example.aiosbananesexport.order.domain.OrderQuantityConfig;
import com.example.aiosbananesexport.order.domain.OrderRepository;
import com.example.aiosbananesexport.order.domain.OrderService;
import com.example.aiosbananesexport.order.domain.PricePerKilogram;
import com.example.aiosbananesexport.order.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.recipient.domain.RecipientRepository;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderSpringConfiguration {

    @Bean
    public OrderService orderService(OrderRepository orderRepository,
                                     RecipientRepository recipientRepository,
                                     PricePerKilogram pricePerKilogram,
                                     OrderQuantityConfig orderQuantityConfig) {
        return new OrderService(orderRepository, recipientRepository, pricePerKilogram, orderQuantityConfig);
    }

    @Bean
    public OrderRepository inMemoryOrderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public RecipientRepository inMemoryRecipientRepository() {
        return new InMemoryRecipientRepository();
    }

    @Bean
    public PricePerKilogram pricePerKilogram(OrderConfigurationProperties orderConfigurationProperties) {
        return new PricePerKilogram(orderConfigurationProperties.getPricePerKilogram());
    }

    @Bean
    public OrderQuantityConfig orderQuantityConfig(OrderConfigurationProperties orderConfigurationProperties){
        return new OrderQuantityConfig(orderConfigurationProperties.getMinOrderQuantityKg(),
                                       orderConfigurationProperties.getMaxOrderQuantityKg(),
                                       orderConfigurationProperties.getIncrementOrderQuantityKg());
    }
}
