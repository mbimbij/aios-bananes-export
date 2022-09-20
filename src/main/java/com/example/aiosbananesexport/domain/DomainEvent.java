package com.example.aiosbananesexport.domain;

import com.example.aiosbananesexport.utils.IdGenerator;
import com.example.aiosbananesexport.utils.TimestampGenerator;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public abstract class DomainEvent {
    private final String eventId;
    private final String orderId;
    private final ZonedDateTime eventDateTime;

    protected DomainEvent(String orderId) {
        this.orderId = orderId;
        this.eventId = IdGenerator.newId();
        this.eventDateTime = TimestampGenerator.now();
    }
}
