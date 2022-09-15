package com.example.aiosbananesexport.infra.in;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ApplicationRestController {

    @PostMapping(value = "/order")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<CreateOrderResponseDto> createOrder() {
        CreateOrderResponseDto responseDto = new CreateOrderResponseDto("id",
                                                                        "firstName",
                                                                        "lastName",
                                                                        "address",
                                                                        75019,
                                                                        "paris",
                                                                        "france",
                                                                        "somedate",
                                                                        50);
        return Mono.just(responseDto);
    }
}