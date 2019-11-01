package com.banson.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.banson.demo.model.entity.Person;
import com.banson.demo.model.repo.People;

@SpringBootApplication
public class Application {// extends SpringBootServletInitializer {
    @SpringBootConfiguration
    @EntityScan(basePackageClasses = { Person.class })
    @EnableJpaRepositories(basePackageClasses = { People.class })
//    @EnableSwagger2
//    @Import(SpringDataRestConfiguration.class)
    public static class Configuration {
//        @Bean
//        public Docket docket() {
//          return new Docket(DocumentationType.SWAGGER_2)
//            .tags(...)
//            .apiInfo(...)
//            ...
//        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
