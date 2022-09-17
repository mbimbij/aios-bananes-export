package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.DomainEventSender;
import com.example.aiosbananesexport.domain.Order;
import com.example.aiosbananesexport.domain.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class ApplicationRestController {

    @Autowired
    DomainEventSender domainEventSender;

    @PostMapping(value = "/order")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        Order order = new Order("anyid",
                                requestDto.getFirstName(),
                                requestDto.getLastName(),
                                requestDto.getAddress(),
                                requestDto.getPostalCode(),
                                requestDto.getCity(),
                                requestDto.getCountry(),
                                LocalDate.parse(requestDto.getDeliveryDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                requestDto.getQuantityKg(),
                                requestDto.getQuantityKg() * 2.5);

        domainEventSender.send(new OrderCreatedEvent("anyid", order));

        CreateOrderResponseDto responseDto = new CreateOrderResponseDto("anyid",
                                                                        requestDto.getFirstName(),
                                                                        requestDto.getLastName(),
                                                                        requestDto.getAddress(),
                                                                        requestDto.getPostalCode(),
                                                                        requestDto.getCity(),
                                                                        requestDto.getCountry(),
                                                                        requestDto.getDeliveryDate(),
                                                                        requestDto.getQuantityKg());
        return Mono.just(responseDto);
    }
}
