package com.banson.demo.client.model;

import java.net.URI;
import java.util.function.BiFunction;

import org.springframework.web.util.UriComponentsBuilder;

public interface CompositeId<ID> {
    String toString();

    default URI toUri(URI baseUri, String path) {
        return UriComponentsBuilder.fromUri(baseUri).path(path).pathSegment(toString()).build().toUri();
    }
    
    static <ID> ID fromString(String str, BiFunction<String, String, ID> constructor) {
        String idStr = str.substring(str.lastIndexOf('/') + 1);
        String[] names = idStr.split(":");
        return constructor.apply(names[0], names[1]);
    }
}
