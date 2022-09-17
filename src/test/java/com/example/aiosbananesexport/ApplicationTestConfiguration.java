package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Application.class)
public class ApplicationTestConfiguration {
    @Bean
    public DomainEventPublisher domainEventSender() {
        return new MockDomainEventPublisher();
    }
}
