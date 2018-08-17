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
@ToString(exclude = { "outgoing" })
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectionNode {
    @EmbeddedId
    private NodeId id;

    private String name = "whatever";

    public static CollectionNode of(String name) {
        CollectionNode node = new CollectionNode();
        node.setId(NodeId.of(name.charAt(0), 1L));
        node.setName(name);
        return node;
    }

    @ElementCollection
    private Set<CollectionEdge> outgoing = new HashSet<>();

    public void linkTo(CollectionNode node) {
        CollectionEdge edge = CollectionEdge.of("type1", node);
        outgoing.add(edge);
    }
}
