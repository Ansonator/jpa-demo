package com.banson.demo.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

import lombok.Data;

@Embeddable
@Data(staticConstructor = "of")
public class NodeId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(nullable = false)
    @NonNull
    private Character pk1;
    
    @Column(nullable = false)
    @NonNull
    private Long pk2;

    public static NodeId of(char pk1) {
        return new NodeId(pk1, 1L);
    }
}
