package io.undertree.rabbitmqpublish;


import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class CustomerEvent implements Serializable {
    private static Fairy fairy = Fairy.create();

    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDate dateOfBirth;
    private final OffsetDateTime createdTimestamp;

    public CustomerEvent() {
        Person p = fairy.person();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.email = p.getEmail();
        this.dateOfBirth = p.getDateOfBirth();
        this.createdTimestamp = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public OffsetDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getFirstName() {
        return firstName;
    }
}
