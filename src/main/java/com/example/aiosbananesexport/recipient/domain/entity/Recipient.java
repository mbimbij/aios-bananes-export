package com.example.aiosbananesexport.recipient.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Recipient {
    private final RecipientId recipientId;

    private Name name;
    private Address address;

    public Recipient(RecipientId recipientId, Name name, Address address) {
        this.recipientId = recipientId;
        this.name = name;
        this.address = address;
    }

    public void rename(Name newName) {
        name = newName;
    }
}
