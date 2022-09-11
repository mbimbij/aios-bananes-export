package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

public class RecipientServiceShould {
    @Test
    void create_a_recipient__when_no_existing_user_with_same_attributes() {
        // GIVEN
        String recipientId = "id";
        String firstName = "firstName";
        String lastName = "lastName";
        String address = "address";
        String postalCode = "postalCode";
        String city = "city";
        String country = "country";
        Recipient expectedRecipient = new Recipient(recipientId,firstName, lastName, address, postalCode, city, country);

        InMemoryRecipientRepository recipientRepository = Mockito.spy(new InMemoryRecipientRepository());
        Mockito.doReturn(recipientId).when(recipientRepository).generateRecipientId();
        RecipientService recipientService = new RecipientService(recipientRepository);

        // WHEN
        recipientService.createRecipient(firstName, lastName, address, postalCode, city, country);

        // THEN
        StepVerifier.create(recipientRepository.getById(recipientId))
                .expectNext(expectedRecipient)
                .verifyComplete();
    }
}
