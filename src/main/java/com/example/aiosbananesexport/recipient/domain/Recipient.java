package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.common.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Recipient {
    private final String recipientId;

    private final Name name;
    private final String address;
    private final String postalCode;
    private final String city;
    private final String country;

    public Recipient(String recipientId, Name name, String address, String postalCode, String city, String country) {
        this.recipientId = recipientId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

}
