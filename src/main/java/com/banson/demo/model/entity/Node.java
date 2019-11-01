package com.banson.demo.model.entity;

import java.util.HashSet;
import java.util.Set;

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
 * Equivalent to {@link Person}, but with an Edge which introduces a surrogate id.
 * 
 * @author BA030483
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Node {
    @EmbeddedId
    private PersonId id;
    
    private static Faker faker = new Faker();

    public static Node of(String name) {
        Node node = new Node();
        node.setId(PersonId.of(name));
        node.setName(name);
        return node;
    }
    
    public static Node newRandom() {
        return of(faker.name().fullName());
    }

    @Column(unique = true, nullable = false)
    @NonNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source", cascade = CascadeType.ALL)
    private Set<Edge> outgoing = new HashSet<>();

    public void linkTo(Node node) {
        Edge edge = Edge.of("1", this, node);
        outgoing.add(edge);
        node.getIncoming().add(edge);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target", cascade = CascadeType.ALL)
    private Set<Edge> incoming = new HashSet<>();

    public void linkFrom(Node node) {
        Edge edge = Edge.of("2", node, this);
        incoming.add(edge);
        node.getOutgoing().add(edge);
    }
}
