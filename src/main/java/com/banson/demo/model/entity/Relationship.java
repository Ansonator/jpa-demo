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
public class Relationship {

    public Relationship(String type, Person source, Person target) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.id = new Id(source.getId(), target.getId());
    }

    public static Relationship of(String type, Person source, Person target) {
        return new Relationship(type, source, target);
    }

    @EmbeddedId
    @NonNull
    private Id id;

    @Embeddable
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Id implements Serializable {
        @AttributeOverrides({ @AttributeOverride(name = "firstName", column = @Column(name = "SOURCE_FIRSTNAME")),
            @AttributeOverride(name = "lastName", column = @Column(name = "SOURCE_LASTNAME")) })
        @NonNull
        private PersonId sourceId;

        @AttributeOverrides({ @AttributeOverride(name = "firstName", column = @Column(name = "TARGET_FIRSTNAME")),
            @AttributeOverride(name = "lastName", column = @Column(name = "TARGET_LASTNAME")) })
        @NonNull
        private PersonId targetId;
    }

    @NonNull
    private String type;

    @MapsId("sourceId")
    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SOURCE_FIRSTNAME", referencedColumnName = "firstName"),
        @PrimaryKeyJoinColumn(name = "SOURCE_LASTNAME", referencedColumnName = "lastName") })
    @NonNull
    private Person source;

    @MapsId("targetId")
    @ManyToOne
    @PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "TARGET_FIRSTNAME", referencedColumnName = "firstName"),
        @PrimaryKeyJoinColumn(name = "TARGET_LASTNAME", referencedColumnName = "lastName") })
    @NonNull
    private Person target;

    @Override
    public String toString() {
        return "[" + type + " : " + source.getId().toString() + " -> " + target.getId().toString() + "]";
    }

}
