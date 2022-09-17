package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.*;
import com.example.aiosbananesexport.infra.in.CreateOrderRequestDto;
import com.example.aiosbananesexport.infra.in.CreateOrderResponseDto;
import com.example.aiosbananesexport.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.infra.out.MockDomainEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.aiosbananesexport.utils.Uncheck.uncheck;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                ApplicationTestConfiguration.class
        }
)
class ApplicationShould {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private OrderFactory orderFactory;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

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
        LocalDate deliveryDate = LocalDate.now()
                                          .plusWeeks(1);
        String deliveryDateString = deliveryDate
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int quantityKg = 25;
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto(firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg);
        CreateOrderResponseDto expectedResponseDto = new CreateOrderResponseDto("anyCreateOrderResponseDtoId", firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg);

        double expectedPrice = 62.5;
        String orderId = "someOrderId";
        Mockito.doReturn(orderId).when(orderFactory).generateId();
        Order expectedOrder = new Order(orderId, firstName, lastName, address, postalCode, city, country, deliveryDate, quantityKg, expectedPrice);
        String anyOrderCreatedEventId = "anyOrderCreatedEventId";
        DomainEvent.setIdGenerator(() -> anyOrderCreatedEventId);
        ZonedDateTime eventDateTime = ZonedDateTime.now();
        DomainEvent.setEventDateTimeSupplier(() -> eventDateTime);
        OrderCreatedEvent expectedEvent = new OrderCreatedEvent(anyOrderCreatedEventId, DomainEvent.getEventDateTime(), expectedOrder);

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
                    byte[] responseBody = result.getResponseBody();
                    CreateOrderResponseDto actualResponseDto = uncheck((byte[] r) -> objectMapper.readValue(r, CreateOrderResponseDto.class)).apply(responseBody);
                    assertSoftly(softAssertions -> {
                        softAssertions.assertThat(actualResponseDto)
                                      .usingRecursiveComparison()
                                      .ignoringFields("id")
                                      .isEqualTo(expectedResponseDto);
                        softAssertions.assertThat(actualResponseDto.getId())
                                      .isNotBlank();
                    });
                });

        // AND the order is saved to the repository
        assertThat(((InMemoryOrderRepository)orderRepository).getOrders()).anySatisfy(order -> assertThat(order)
                .usingRecursiveComparison()
                .isEqualTo(expectedOrder));

        // AND an "OrderCreatedEvent" is produced and sent
        List<DomainEvent> domainEvents = ((MockDomainEventPublisher) domainEventPublisher).getDomainEvents();
        assertThat(domainEvents).anySatisfy(domainEvent -> assertThat(domainEvent)
                .usingRecursiveComparison()
                .isEqualTo(expectedEvent));
    }

}
