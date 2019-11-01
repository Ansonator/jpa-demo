package com.banson.demo.client.model;

import java.net.URI;

import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.blackpepper.bowman.annotation.RemoteResource;
import uk.co.blackpepper.bowman.annotation.ResourceId;

@RemoteResource("/people")
@Data
public class Person2 implements Entity<Person2.Id> {
    @Data
    @RequiredArgsConstructor(staticName = "of")
    public static class Id implements CompositeId<Id> {
        @NonNull
        String firstName;
        @NonNull
        String lastName;

        @Override
        public String toString() {
            return firstName + ":" + lastName;
        }

        public static Id of(String idStr) {
            String[] names = idStr.split(":");
//            firstName = names[0];
//            lastName = names[1];
            return new Id(names[0], names[1]);
        }
    }

    private URI uri;

    @ResourceId
    public URI getUri() {
        return uri;
    }

    @Override
    public Id getId(String idStr) {
        return Id.of(idStr);
    }

    @Override
    public void setId(URI baseUri, Id id) {
        this.uri = UriComponentsBuilder.fromUri(baseUri).path(getPath()).pathSegment(id.toString()).build().toUri();
    }

//    @NonNull
//    private URI baseUri;

//    @ResourceId
//    public URI getUri() {
//        String path = uri.getPath();
//        Id id = Id.fromString()
//        return id.toUri(baseUri, getPath());
//    }
//    public void setUri(URI uri) {
//        this.uri = uri;
//    }

    @NonNull
    private Integer age;

    protected Person2() {}

    public Person2(URI baseUri, String firstName, String lastName, int age) {
        setId(baseUri, Id.of(firstName, lastName));
        setAge(age);
    }

//    @Data
//    public static class Post {
//
//        @NonNull
//        private Id id;
//        @NonNull
//        private Integer age;
//
//        public Post(Person2 person) {
//            String path = person.getUri().getPath();
//            String idStr = path.substring(path.lastIndexOf('/') + 1);
//            String[] names = idStr.split(":");
//            this.id = Id.of(names[0], names[1]);
//            this.age = person.getAge();
//        }
//    }

//    public Person2(URI baseUri, Post post) {
//        URI uri = UriComponentsBuilder.fromUri(baseUri).path(post.getId().toString()).build().toUri();
//        setUri(uri);
//        setAge(post.getAge());
//    }

//    @Override
//    public URI getBaseUri(Configuration config) {
//        return UriComponentsBuilder.fromUri(config.getBaseUri()).path(getPath()).build().toUri();
//    }

//    public ResponseEntity<Post> post2(URI uri, RestTemplate restTemplate) {
//        Post payload = new Post(this);
//        HttpEntity<Post> request = new HttpEntity<>(payload);
//        ResponseEntity<Post> response = restTemplate.exchange(uri, HttpMethod.POST, request, Post.class);
//        return response;
////        return restTemplate.postForEntity(uri, request, Post.class);
//    }

    @Override
    public URI post(URI baseUri, RestTemplate restTemplate) {
//        Post payload = new Post(this);
        URI uri = UriComponentsBuilder.fromUri(baseUri).path(getPath()).build().toUri();
        return restTemplate.postForLocation(uri, this);
    }

//
//    public ResponseEntity<Person2> post(URI baseUri, RestTemplate restTemplate) {
//        Post payload = new Post(this);
//        String path = Person.class.getAnnotation(RemoteResource.class).value();
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(baseUri).path(path);
//        ResponseEntity<Post> postResponse = restTemplate.postForEntity(uriBuilder.build().toUri(), payload, Post.class);
//        URI uri = uriBuilder.pathSegment(payload.getId().toString()).build().toUri();
//        setUri(uri);
//        return new ResponseEntity<Person2>(this, postResponse.getStatusCode());
//    }
}
