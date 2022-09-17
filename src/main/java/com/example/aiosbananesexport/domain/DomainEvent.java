package com.example.aiosbananesexport.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.function.Supplier;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class DomainEvent {
    private final String id;

    private static Supplier<String> idGenerator = () -> UUID.randomUUID().toString();
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
}
