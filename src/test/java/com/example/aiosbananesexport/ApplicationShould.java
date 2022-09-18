package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.*;
import com.example.aiosbananesexport.infra.in.*;
import com.example.aiosbananesexport.infra.out.InMemoryOrderRepository;
import com.example.aiosbananesexport.infra.out.MockDomainEventPublisher;
import com.example.aiosbananesexport.utils.IdGenerator;
import com.example.aiosbananesexport.utils.TimestampGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

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
    private final String fixedId = "fixedId";
    private final ZonedDateTime fixedTimestamp = ZonedDateTime.now();
    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private OrderFactory orderFactory;

    @SpyBean
    private ApplicationRestController applicationRestController;

    @Autowired
    private InMemoryOrderRepository orderRepository;

    @Autowired
    private MockDomainEventPublisher domainEventPublisher;
    private PlaceOrderRequestDto requestDto;
    private Order order;

    @BeforeEach
    void setUp() {
        requestDto = new PlaceOrderRequestDto(firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg);
        doReturn(fixedId).when(orderFactory).generateId();
        order = new Order(fixedId, firstName, lastName, address, postalCode, city, country, deliveryDate, quantityKg, 62.5);

        TimestampGenerator.useFixedClockAt(fixedTimestamp);
        IdGenerator.useFixedId(fixedId);

        domainEventPublisher.clear();
        orderRepository.clear();
    }

    @Test
    void create_an_order__in_nominal_case() {
        // GIVEN expected outcomes
        PlaceOrderResponseDto expectedResponseDto = new PlaceOrderResponseDto(fixedId, firstName, lastName, address, postalCode, city, country, deliveryDateString, quantityKg, "62.50");

        OrderCreatedEvent expectedEvent = new OrderCreatedEvent(fixedId, fixedTimestamp, order);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is correct
        responseSpec.expectStatus()
                    .isCreated()
                    .expectBody(PlaceOrderResponseDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(expectedResponseDto));

        // AND the order is saved to the repository
        assertThat(orderRepository.getOrders()).anySatisfy(order -> assertThat(order).usingRecursiveComparison().isEqualTo(order));

        // AND an "OrderCreatedEvent" is published
        List<DomainEvent> domainEvents = domainEventPublisher.getDomainEvents();
        assertThat(domainEvents).anySatisfy(domainEvent -> assertThat(domainEvent).usingRecursiveComparison().isEqualTo(expectedEvent));
    }

    @Test
    void return_an_error__when_delivery_date_too_early() {
        // GIVEN
        LocalDate earlyDeliveryDate = deliveryDate.minusDays(1);
        String earlyDeliveryDateString = earlyDeliveryDate.format(DateTimeFormat.DATE_FORMATTER);
        PlaceOrderRequestDto requestDto = new PlaceOrderRequestDto(firstName, lastName, address, postalCode, city, country, earlyDeliveryDateString, quantityKg);


        Order expectedOrderInError = order.withDeliveryDate(earlyDeliveryDate);
        OrderDeliveryTooEarlyException exception = new OrderDeliveryTooEarlyException(expectedOrderInError);
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(exception.getMessage(), exception, fixedTimestamp);

        OrderFailedDeliveryDateTooEarlyEvent expectedEvent = new OrderFailedDeliveryDateTooEarlyEvent(fixedId, fixedTimestamp, expectedOrderInError);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));

        // AND an error event is published
        assertThat(domainEventPublisher.getDomainEvents())
                .filteredOn(event -> event instanceof OrderFailedDeliveryDateTooEarlyEvent)
                .hasSize(1)
                .allSatisfy(domainEvent -> assertThat(domainEvent).usingRecursiveComparison().isEqualTo(expectedEvent));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10_001})
    void return_an_error__when_quantity_not_in_allowed_range(int quantityKg) {
        // GIVEN
        PlaceOrderRequestDto requestDto = this.requestDto.withQuantityKg(quantityKg);
        Order expectedOrderInError = order.withQuantityKg(quantityKg);
        OrderQuantityNotInRangeException expectedException = new OrderQuantityNotInRangeException(expectedOrderInError);
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(expectedException.getMessage(), expectedException, fixedTimestamp);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));
    }

    @Test
    void return_an_error__when_quantity_not_multiple_of_allowed_hincrement() {
        // GIVEN
        int quantityKg = 30;
        PlaceOrderRequestDto requestDto = this.requestDto.withQuantityKg(quantityKg);
        Order expectedOrderInError = order.withQuantityKg(quantityKg);
        OrderQuantityNotMultipleOfIncrementException expectedException = new OrderQuantityNotMultipleOfIncrementException(expectedOrderInError);
        BusinessErrorDto BusinessErrorDto = new BusinessErrorDto(expectedException.getMessage(), expectedException, fixedTimestamp);

        // WHEN performing the REST request
        WebTestClient.ResponseSpec responseSpec = webTestClient.post().uri("/order").bodyValue(requestDto).exchange();

        // THEN the REST response is http 409 with error response
        responseSpec.expectStatus()
                    .isEqualTo(409)
                    .expectBody(BusinessErrorDto.class)
                    .value(actualResponseDto -> assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(BusinessErrorDto));
    }
}
