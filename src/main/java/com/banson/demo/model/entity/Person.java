package com.banson.demo.model.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

import com.github.javafaker.Faker;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This shows how to map this with bidirectional links. Each node maintains references to both incoming and outgoing links. Links maintain
 * references to both source and target nodes. This has advantages in that spring-data can create all repo methods and queries. The
 * disadvantage is in the complexity in the mapping due to the bidirectional relationships.
 * 
 * @author BA030483
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {
    @EmbeddedId
    @NonNull
    private PersonId id;

    private static Faker faker = new Faker();

    public static Person of(String fullName, int age) {
        Person node = new Person();
        String[] names = fullName.split("\\s+");
        node.setId(PersonId.of(names[0], names[1]));
        node.setAge(age);
        return node;
    }

    public static Person newRandom() {
        int age = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return of(faker.name().fullName(), age);
    }

    @Column(nullable = false)
    @NonNull
    private Integer age;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source", cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.sourceId", cascade = CascadeType.ALL)
    private Set<Relationship> reports = new HashSet<>();

    public void manages(Person employee) {
        Relationship edge = Relationship.of("work", employee, this);
        reports.add(edge);
        employee.getManagers().add(edge);
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.targetId")
    private Set<Relationship> managers = new HashSet<>();

    public void reportsTo(Person boss) {
        Relationship edge = Relationship.of("work", this, boss);
        managers.add(edge);
        boss.getReports().add(edge);
    }
}
