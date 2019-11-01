package com.banson.demo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.banson.demo.model.entity.Person;
import com.banson.demo.model.entity.PersonId;

@RepositoryRestResource(path = "people")
public interface People extends JpaRepository<Person, PersonId> {

}