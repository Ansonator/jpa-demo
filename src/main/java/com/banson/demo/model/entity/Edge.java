package com.banson.demo.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * This maintains a surrogate id as the PK. It has the disadvantage of exposing an extra, unnecessary id. The advantage is that it avoids
 * composite keys (which can work better with libraries like spring-data).
 * 
 * @author BA030483
 *
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(staticName = "of")
public class Edge {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String type;

    @ManyToOne
    @NonNull
    private Node source;

    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    private Node target;

    @Override
    public String toString() {
        return "Edge [" + type + ":" + source.getName() + ":" + target.getName() + "]";
    }

}
