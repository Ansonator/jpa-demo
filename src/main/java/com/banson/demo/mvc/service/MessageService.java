package com.banson.demo.mvc.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public Map<String, String> getAll() {
        return Collections.singletonMap("message", "Hello World");
    }
}
