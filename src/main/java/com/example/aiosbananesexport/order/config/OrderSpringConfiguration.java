package com.example.aiosbananesexport.order.config;

import com.example.aiosbananesexport.order.domain.entity.OrderFactory;
import com.example.aiosbananesexport.order.domain.entity.OrderQuantityConfig;
import com.example.aiosbananesexport.order.domain.entity.OrderRepository;
import com.example.aiosbananesexport.order.domain.entity.PricePerKilogram;
import com.example.aiosbananesexport.order.domain.usecase.PlaceOrder;
import com.example.aiosbananesexport.order.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.recipient.domain.entity.RecipientRepository;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderSpringConfiguration {

    @Bean
    public PlaceOrder placeOrder(OrderFactory orderFactory,
                                 OrderRepository orderRepository,
                                 RecipientRepository recipientRepository,
                                 PricePerKilogram pricePerKilogram) {
        return new PlaceOrder(orderFactory, orderRepository, recipientRepository, pricePerKilogram);
    }

    @Bean
    public OrderFactory orderFactory() {
        return new OrderFactory();
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
    public OrderQuantityConfig orderQuantityConfig(OrderConfigurationProperties orderConfigurationProperties) {
        return new OrderQuantityConfig(orderConfigurationProperties.getMinOrderQuantityKg(),
                                       orderConfigurationProperties.getMaxOrderQuantityKg(),
                                       orderConfigurationProperties.getIncrementOrderQuantityKg());
    }
}
