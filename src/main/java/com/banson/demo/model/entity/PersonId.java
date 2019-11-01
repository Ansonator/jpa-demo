package com.banson.demo.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

import lombok.Data;

@Embeddable
@Data(staticConstructor = "of")
public class PersonId implements Serializable {
    @Column(nullable = false)
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @NonNull
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public static PersonId of(String fullName) {
        String[] names = fullName.split("\\s+");
        if (names.length > 1) {
            return new PersonId(names[0], names[1]);
        } else {
            return new PersonId(names[0], "Doe");
        }
    }
}
