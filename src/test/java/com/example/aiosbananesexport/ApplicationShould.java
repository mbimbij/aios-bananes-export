package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.*;
import com.example.aiosbananesexport.infra.in.*;
import com.example.aiosbananesexport.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.infra.out.MockDomainEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ApplicationTestConfiguration.class
)
class ApplicationShould {

    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String address = "address";
    private final int postalCode = 75019;
    private final String city = "paris";
    private final String country = "france";
    private final LocalDate deliveryDate = LocalDate.now().plusWeeks(1);
    private final String deliveryDateString = deliveryDate.format(DateTimeFormat.DATE_FORMATTER);
    private final int quantityKg = 25;
    private final String orderId = "someOrderId";
    private final String anyOrderCreatedEventId = "anyOrderCreatedEventId";
    private final ZonedDateTime eventDateTime = ZonedDateTime.now();
    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private OrderFactory orderFactory;

    @SpyBean
    private ApplicationRestController applicationRestController;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;
    private CreateOrderRequestDto requestDto;
    private Order order;

    @BeforeEach
    void setUp() {
        requestDto = new CreateOrderRequestDto(firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg);
        Mockito.doReturn(orderId).when(orderFactory).generateId();
        DomainEvent.setIdGenerator(() -> anyOrderCreatedEventId);
        DomainEvent.setEventDateTimeSupplier(() -> eventDateTime);
        order = new Order(orderId, firstName, lastName, address, postalCode, city, country, deliveryDate, quantityKg, 62.5);
    }

    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN expected outcomes
        CreateOrderResponseDto expectedResponseDto = new CreateOrderResponseDto(orderId, firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg, "62.50");
        OrderCreatedEvent expectedEvent = new OrderCreatedEvent(anyOrderCreatedEventId, DomainEvent.getEventDateTime(), order);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is correct
        responseSpec.expectStatus()
                    .isCreated()
                    .expectBody(CreateOrderResponseDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(expectedResponseDto));

        // AND the order is saved to the repository
        assertThat(((InMemoryOrderRepository) orderRepository).getOrders()).anySatisfy(order -> assertThat(order).usingRecursiveComparison().isEqualTo(order));

        // AND an "OrderCreatedEvent" is published
        List<DomainEvent> domainEvents = ((MockDomainEventPublisher) domainEventPublisher).getDomainEvents();
        assertThat(domainEvents).anySatisfy(domainEvent -> assertThat(domainEvent).usingRecursiveComparison().isEqualTo(expectedEvent));
    }


    @Test
    void return_an_error__when_delivery_date_too_early() {
        // GIVEN
        LocalDate errorDeliveryDate = deliveryDate.minusDays(1);
        String deliveryDateString = errorDeliveryDate.format(DateTimeFormat.DATE_FORMATTER);
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto(firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg);
        Order errorOrder = order.withDeliveryDate(errorDeliveryDate);
        OrderDeliveryTooEarlyException exception = new OrderDeliveryTooEarlyException(errorOrder);
        ZonedDateTime exceptionTimestamp = ZonedDateTime.now();
        Mockito.doReturn(exceptionTimestamp)
               .when(applicationRestController)
               .currentTimestamp();
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(exception.getMessage(), exception, exceptionTimestamp);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));
    }
}
