package com.example.aiosbananesexport;

import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;
import com.example.aiosbananesexport.infra.in.CreateOrderResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationShould {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void load_context_with_no_problem() {
    }

    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN
        String firstName = "firstName";
        String lastName = "lastName";
        String address = "address";
        int postalCode = 75019;
        String city = "paris";
        String country = "france";
        String deliveryDate = LocalDate.now()
                                       .plusWeeks(1)
                                       .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int quantityKg = 50;
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto(firstName, lastName, address, postalCode, city, country, deliveryDate, quantityKg);
        CreateOrderResponseDto expectedResponseDto = new CreateOrderResponseDto("any", firstName, lastName, address, postalCode, city, country, deliveryDate, quantityKg);

        // WHEN
        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                                                               .uri("/order")
                                                               .bodyValue(requestDto)
                                                               .exchange();
        // THEN the REST response is correct
        responseSpec
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(result -> {
                    try {
                        byte[] responseBody = result.getResponseBody();
                        CreateOrderResponseDto actualResponseDto = objectMapper.readValue(responseBody, CreateOrderResponseDto.class);
                        assertSoftly(softAssertions -> {
                            softAssertions.assertThat(actualResponseDto)
                                          .usingRecursiveComparison()
                                          .ignoringFields("id")
                                          .isEqualTo(expectedResponseDto);
                            softAssertions.assertThat(actualResponseDto.getId())
                                          .isNotBlank();
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
