package com.banson.demo.mvc.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.banson.demo.mvc.service.MessageService;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class) // for testing the controller layer -- scans only provided controller
@ContextConfiguration(classes = { HomeControllerTest.Configuration.class })
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class HomeControllerTest {

    @SpringBootConfiguration
    @ComponentScan(basePackages = { "com.banson.demo.mvc" })
    public static class Configuration {

    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService service;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonMap("message", "Hello Mock"));
        this.mockMvc.perform(get("/greeting"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello Mock")))
            .andDo(document("home", responseFields(fieldWithPath("message").description("The welcome message for the user."))));
    }
}
