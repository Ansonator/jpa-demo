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

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Equivalent to {@link NodePk}, but with an Edge which introduces a surrogate id.
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
    private NodeId id;

    public static Node of(String name) {
        Node node = new Node();
        node.setId(NodeId.of(name.charAt(0), 1L));
        node.setName(name);
        return node;
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
