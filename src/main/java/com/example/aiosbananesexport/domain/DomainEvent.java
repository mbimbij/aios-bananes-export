package com.example.aiosbananesexport.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class DomainEvent {
    private final String id;
    private final ZonedDateTime eventDateTime;

    private static Supplier<String> idGenerator = () -> UUID.randomUUID().toString();
    private static Supplier<ZonedDateTime> eventDateTimeSupplier = ZonedDateTime::now;

    public static String generateId(){
        return idGenerator.get();
    }

    /**
     * Used for tests
     * @param idGenerator
     */
    public static void setIdGenerator(Supplier<String> idGenerator){
        DomainEvent.idGenerator = idGenerator;
    }

    public static ZonedDateTime getEventDateTime(){
        return eventDateTimeSupplier.get();
    }

    public static void setEventDateTimeSupplier(Supplier<ZonedDateTime> eventDateTimeSupplier) {
        DomainEvent.eventDateTimeSupplier = eventDateTimeSupplier;
    }
}
