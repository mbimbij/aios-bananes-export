package com.example.aiosbananesexport.recipient.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Recipient {
    private final String recipientId;

    private final Name name;
    private final Address address;

    public Recipient(String recipientId, Name name, Address address) {
        this.recipientId = recipientId;
        this.name = name;
        this.address = address;
    }

}
