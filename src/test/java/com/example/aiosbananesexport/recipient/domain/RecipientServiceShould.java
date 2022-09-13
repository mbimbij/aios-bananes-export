package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.recipient.domain.entity.*;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientAlreadyExistsException;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientNotFoundException;
import com.example.aiosbananesexport.recipient.domain.usecase.CreateRecipient;
import com.example.aiosbananesexport.recipient.domain.usecase.RecipientService;
import com.example.aiosbananesexport.recipient.domain.usecase.RenameRecipient;
import com.example.aiosbananesexport.recipient.infra.out.InMemoryRecipientRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RecipientServiceShould {

    private final RecipientId recipientId = new RecipientId("id");
    private Name name;
    private Address address;
    private InMemoryRecipientRepository recipientRepository;
    private RecipientService recipientService;

    private CreateRecipient createRecipient;
    private RenameRecipient renameRecipient;

    @BeforeEach
    void setUp() {
        name = new Name(new Name.FirstName("firstName"),
                        new Name.LastName("lastName"));
        address = new Address(new Address.Street("address"),
                              new Address.PostalCode(75019),
                              new Address.City("Paris"),
                              new Address.Country("France"));

        RecipientFactory recipientFactory = Mockito.spy(new RecipientFactory());
        Mockito.doReturn(recipientId)
               .when(recipientFactory)
               .generateRecipientId();
        recipientRepository = new InMemoryRecipientRepository();

        recipientService = new RecipientService(recipientRepository);
        createRecipient = new CreateRecipient(recipientFactory, recipientRepository);
        renameRecipient = new RenameRecipient(recipientRepository);
    }

    @Test
    void create_a_recipient__in_nominal_case() {
        // GIVEN
        Recipient expectedRecipient = new Recipient(recipientId, name, address);

        // WHEN
        createRecipient.createRecipient(name, address);

        // THEN
        Optional<Recipient> actualRecipient = recipientRepository.getById(recipientId);
        assertThat(actualRecipient).hasValue(expectedRecipient);
    }

    @Test
    void throw_an_exception__when_trying_to_create_a_recipient__but_user_exists_with_same_attributes() {
        // GIVEN
        createRecipient.createRecipient(name, address);

        // WHEN
        ThrowableAssert.ThrowingCallable throwingCallable = () -> createRecipient.createRecipient(name, address);

        // THEN
        assertThatThrownBy(throwingCallable).isInstanceOf(RecipientAlreadyExistsException.class);
    }

    @Test
    void rename_an_existing_recipient() {
        // GIVEN
        createRecipient.createRecipient(name, address);
        Name newName = new Name(new Name.FirstName("newFirstName"),
                                new Name.LastName("lastName"));

        Recipient expectedRecipient = new Recipient(recipientId, newName, address);

        // WHEN
        renameRecipient.renameRecipient(recipientId, newName);

        // THEN
        Optional<Recipient> actualRecipient = recipientRepository.getById(recipientId);
        assertThat(actualRecipient).hasValue(expectedRecipient);
    }

    @Test
    void throw_an_exception__when_renaming_a_non_existing_recipient() {
        // GIVEN
        createRecipient.createRecipient(name, address);
        Name newName = new Name(new Name.FirstName("newFirstName"),
                                new Name.LastName("lastName"));

        // WHEN
        ThrowableAssert.ThrowingCallable throwingCallable = () -> renameRecipient.renameRecipient(new RecipientId("nonExistingId"), newName);

        // THEN
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(RecipientNotFoundException.class);
    }

    @Test
    void delete_an_existing_recipient() {
        // GIVEN
        createRecipient.createRecipient(name, address);

        // WHEN
        recipientService.deleteRecipient(recipientId);

        // THEN
        Optional<Recipient> actualRecipient = recipientRepository.getById(recipientId);
        assertThat(actualRecipient).isEmpty();
    }

    @Test
    void throw_an_exception__when_trying_to_delete_a_non_existing_recipient() {
        // GIVEN
        createRecipient.createRecipient(name, address);

        // WHEN
        ThrowableAssert.ThrowingCallable throwingCallable = () -> recipientService.deleteRecipient(new RecipientId("nonExistingId"));

        // THEN
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(RecipientNotFoundException.class);
    }
}
