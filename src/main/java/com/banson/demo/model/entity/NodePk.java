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
public class NodePk {
    @EmbeddedId
    @NonNull
    private NodeId id;

    public static NodePk of(String name) {
        NodePk node = new NodePk();
        node.setId(NodeId.of(name.charAt(0), 1L));
        node.setName(name);
        return node;
    }

    @Column(unique = true, nullable = false)
    @NonNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.sourceId", cascade = CascadeType.ALL)
    private Set<EdgePk> outgoing = new HashSet<>();

    public void linkTo(NodePk node) {
        EdgePk edge = EdgePk.of("1", this, node);
        outgoing.add(edge);
        node.getIncoming().add(edge);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.targetId", cascade = CascadeType.ALL)
    private Set<EdgePk> incoming = new HashSet<>();

    public void linkFrom(NodePk node) {
        EdgePk edge = EdgePk.of("2", node, this);
        incoming.add(edge);
        node.getOutgoing().add(edge);
    }
}
