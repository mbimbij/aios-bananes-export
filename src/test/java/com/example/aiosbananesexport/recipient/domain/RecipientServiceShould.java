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
        Name name = new Name(new Name.FirstName("firstName"), new Name.LastName("lastName"));
        Address address = new Address(new Address.Street("address"), new Address.PostalCode(75019), new Address.City("Paris"), new Address.Country("France"));
        Recipient expectedRecipient = new Recipient(recipientId, name, address);

        InMemoryRecipientRepository recipientRepository = Mockito.spy(new InMemoryRecipientRepository());
        Mockito.doReturn(recipientId).when(recipientRepository).generateRecipientId();
        RecipientService recipientService = new RecipientService(recipientRepository);

        // WHEN
        recipientService.createRecipient(name, address);

        // THEN
        StepVerifier.create(recipientRepository.getById(recipientId))
                    .expectNext(expectedRecipient)
                    .verifyComplete();
    }
}
