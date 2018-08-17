package com.banson.demo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.banson.demo.model.entity.NodeId;
import com.banson.demo.model.entity.NodePk;

@RepositoryRestResource(path = "nodePks")
public interface NodePkRepo extends JpaRepository<NodePk, NodeId> {

}