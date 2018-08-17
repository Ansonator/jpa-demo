package com.banson.demo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.banson.demo.model.entity.CollectionNode;
import com.banson.demo.model.entity.NodeId;

@RepositoryRestResource(path = "collectionNodes")
public interface CollectionNodeRepo extends JpaRepository<CollectionNode, NodeId> {

}