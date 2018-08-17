package com.banson.demo.model.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://github.cerner.com/concept-mapping/concept-mapping-chef-tomcat/blob/latest_working/test-dao/src/main/java/com/cerner/concept/mapping/support/entity/IdentityCodeRelationship.java
 * https://github.cerner.com/concept-mapping/concept-mapping-chef-tomcat/blob/latest_working/test-dao/src/main/java/com/cerner/concept/mapping/support/entity/IdentityCode.java
 * 
 * @author BA030483
 *
 */
@Entity
@Data
public class EdgePk {

    public EdgePk(String type, NodePk source, NodePk target) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.id = new Id(source.getId(), target.getId());
    }

    public static EdgePk of(String type, NodePk source, NodePk target) {
        return new EdgePk(type, source, target);
    }

    @EmbeddedId
    @NonNull
    private Id id;

    @Embeddable
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Id implements Serializable {
        @AttributeOverrides({
            @AttributeOverride(name = "pk1", column = @Column(name = "SOURCE_PK1")),
            @AttributeOverride(name = "pk2", column = @Column(name = "SOURCE_PK2")) })
        @NonNull
        private NodeId sourceId;

        @AttributeOverrides({
            @AttributeOverride(name = "pk1", column = @Column(name = "TARGET_PK1")),
            @AttributeOverride(name = "pk2", column = @Column(name = "TARGET_PK2")) })
        @NonNull
        private NodeId targetId;
    }

    @NonNull
    private String type;

    @MapsId("sourceId")
    @ManyToOne
    @PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "source_pk1", referencedColumnName = "pk1"),
        @PrimaryKeyJoinColumn(name = "source_pk2", referencedColumnName = "pk2") })
    @NonNull
    private NodePk source;

    @MapsId("targetId")
    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "target_pk1", referencedColumnName = "pk1"),
        @PrimaryKeyJoinColumn(name = "target_pk2", referencedColumnName = "pk2") })
    @NonNull
    private NodePk target;

    @Override
    public String toString() {
        return "Edge [" + type + ":" + source.getName() + ":" + target.getName() + "]";
    }

}
