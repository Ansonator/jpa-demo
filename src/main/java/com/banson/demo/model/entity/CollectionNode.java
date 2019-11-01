package com.banson.demo.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This demonstrates mapping a graph with references going in only one direction. The advantage is a much simpler mapping. The disadvantage
 * is that custom find and delete methods / queries must be provided.
 * 
 * @author BA030483
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectionNode {
    @EmbeddedId
    private PersonId id;

    public static CollectionNode of(String fullName) {
        CollectionNode node = new CollectionNode();
        node.setId(PersonId.of(fullName));
        return node;
    }

    @ElementCollection
    private Set<CollectionEdge> children = new HashSet<>();

    public void linkTo(CollectionNode node) {
        CollectionEdge edge = CollectionEdge.of("family", node);
        children.add(edge);
    }
}
