package com.banson.demo.client.model;

import java.net.URI;

import org.springframework.web.client.RestTemplate;

import com.banson.demo.client.model.Person2.Id;

import uk.co.blackpepper.bowman.annotation.RemoteResource;

public interface Entity<ID extends CompositeId<ID>> {
    URI getUri();

    URI post(URI baseUri, RestTemplate restTemplate);

    default String getPath() {
        return getClass().getAnnotation(RemoteResource.class).value();
    }

    default Id getId() {
        String path = getUri().getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        return getId(idStr);
    }

    Id getId(String idStr);

    void setId(URI baseUri, Id id);
}
