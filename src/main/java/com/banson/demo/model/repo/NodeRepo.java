package com.banson.demo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.banson.demo.model.entity.Node;
import com.banson.demo.model.entity.NodeId;

@RepositoryRestResource(path = "nodes")
public interface NodeRepo extends JpaRepository<Node, NodeId> {

}