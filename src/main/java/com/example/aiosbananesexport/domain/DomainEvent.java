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
    private final String id;
    private final ZonedDateTime eventDateTime;
    private final Order order;

    protected DomainEvent(Order order) {
        this.id = IdGenerator.newId();
        this.eventDateTime = TimestampGenerator.now();
        this.order = order;
    }
}
