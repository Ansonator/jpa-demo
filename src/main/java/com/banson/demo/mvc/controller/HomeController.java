package com.banson.demo.mvc.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banson.demo.mvc.service.MessageService;

@RestController
@RequestMapping("/greeting")
public class HomeController {

    private final MessageService service;

    public HomeController(MessageService service) {
        this.service = service;
    }
    
    @GetMapping
    public Map<String, String> greeting() {
        return service.getAll();
    }

}