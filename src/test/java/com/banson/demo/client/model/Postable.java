package com.banson.demo.client.model;

import java.net.URI;

import org.springframework.web.client.RestTemplate;

import uk.co.blackpepper.bowman.Configuration;

public interface Postable<ID extends CompositeId<ID>> {
    URI post(Configuration config, RestTemplate restTemplate);
    URI getId();
    URI getBaseUri(Configuration config);
    ID getNativeId();
}
