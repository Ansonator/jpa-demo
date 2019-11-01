package com.banson.demo.client.model;

import java.net.URI;

import org.springframework.lang.NonNull;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

@RemoteResource("/people")
@Data(staticConstructor = "of")
public class Person {
    @NonNull
    private URI id;

//    private PersonId id;

    @NonNull
    private Integer age;

    public Person() {}

//    private Set<Relationship> reports = new HashSet<>();
//    private Set<Relationship> managers = new HashSet<>();

//    public static class PersonId {
//        
//    }

    public URI getUri(Configuration configuration, String idStr) {
        String path = getClass().getAnnotation(RemoteResource.class).value();
        return UriComponentsBuilder.fromUri(configuration.getBaseUri()).path(path).pathSegment(idStr).build().toUri();
    }

    public Person(Configuration config, String firstName, String lastName, int age) {
        setId(getUri(config, firstName + ":" + lastName));
//        setId(URI.create("{\"firstName\": \"" + firstName + "\", \"lastName\": \"" + lastName + "\"}"));
        this.age = age;
    }

    @ResourceId
    public URI getId() {
        return id;
    }

//    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
//    public Set<Relationship> getReports() {
//        return reports;
//    }
//
//    @JsonDeserialize(contentUsing = InlineAssociationDeserializer.class)
//    public Set<Relationship> getManagers() {
//        return managers;
//    }

}
