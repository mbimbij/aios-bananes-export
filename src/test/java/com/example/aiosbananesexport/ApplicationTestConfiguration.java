package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.DomainEventSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Application.class)
public class ApplicationTestConfiguration {
    @Bean
    public DomainEventSender domainEventSender() {
        return new MockDomainEventSender();
    }
}
