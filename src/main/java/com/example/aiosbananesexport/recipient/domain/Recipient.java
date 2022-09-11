package com.example.aiosbananesexport.recipient.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Recipient {
    private final String recipientId;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String postalCode;
    private final String city;
    private final String country;

    public Recipient(String recipientId, String firstName, String lastName, String address, String postalCode, String city, String country) {
        this.recipientId = recipientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }
}
