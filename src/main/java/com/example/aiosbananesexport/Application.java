package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.DomainEventPublisher;
import com.example.aiosbananesexport.domain.OrderFactory;
import com.example.aiosbananesexport.domain.OrderRepository;
import com.example.aiosbananesexport.domain.PlaceOrder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PlaceOrder placeOrder(OrderFactory orderFactory, OrderRepository orderRepository, DomainEventPublisher domainEventPublisher) {
        return new PlaceOrder(orderFactory, orderRepository, domainEventPublisher);
    }

    @Bean
    public OrderFactory orderFactory(){
        return new OrderFactory();
    }

}
