package com.banson.demo.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(staticName = "of")
public class CollectionEdge {
    @NonNull
    private String type;

    @ManyToOne(cascade=CascadeType.ALL)
    @NonNull
    private CollectionNode target;
}
