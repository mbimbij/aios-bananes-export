package com.example.aiosbananesexport;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationShould {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
    }

    @Test
    void create_an_order__in_nominal_case() {
        new CreateOrderRequestDto("firstName", "lastName", "address", 75019, "paris", "france", LocalDate.now().plusWeeks(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 50);
        webTestClient.post()
                .uri("/order")
                .exchange()
                .expectStatus()
                .isCreated();
    }
}
