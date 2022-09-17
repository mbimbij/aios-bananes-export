package com.example.aiosbananesexport.infra.in;

import com.example.aiosbananesexport.domain.Order;
import com.example.aiosbananesexport.domain.PlaceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ApplicationRestController {

    @Autowired
    private PlaceOrder placeOrder;

    @PostMapping(value = "/order")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        Order order = placeOrder.placeOrder(requestDto);
        CreateOrderResponseDto responseDto = new CreateOrderResponseDto(order.getId(),
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
