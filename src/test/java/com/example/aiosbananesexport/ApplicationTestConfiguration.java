package com.example.aiosbananesexport;

import com.example.aiosbananesexport.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.infra.out.MockDomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Application.class)
public class ApplicationTestConfiguration {
    @Bean
    public MockDomainEventPublisher domainEventPublisher() {
        return new MockDomainEventPublisher();
    }

    @Bean
    public InMemoryOrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }
}
