package com.banson.demo.client.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.banson.demo.client.config.Config;
import com.banson.demo.client.model.Person;
import com.banson.demo.client.model.Person2;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.RestTemplateConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Config.class })
public class ClientTest {
    private static final Faker faker;
    private static final Configuration config;
    private static final ClientFactory factory;
    private static final Client<Person> personRepo;
    private static RestTemplate restTemplate;

    static {
        faker = new Faker();
        config = Configuration.builder().setBaseUri("http://localhost:8080/").setRestTemplateConfigurer(new RestTemplateConfigurer() {
            @Override
            public void configure(RestTemplate restTemplate) {
                ClientTest.restTemplate = restTemplate;
            }
        }).build();
        factory = config.buildClientFactory();
        personRepo = factory.create(Person.class);
    }

    public static Person newRandom(Configuration config) {
        Name name = faker.name();
        int age = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return new Person(config, name.firstName(), name.lastName(), age);
    }

//    @Data(staticConstructor = "of")
//    public static class PersonPost implements PostPayload<Person> {
//        @Data(staticConstructor = "of")
//        public static class Id {
//            @NonNull
//            String firstName;
//            @NonNull
//            String lastName;
//
//            @Override
//            public String toString() {
//                return firstName + ":" + lastName;
//            }
//        }
//
//        @NonNull
//        private Id id;
//        @NonNull
//        private Integer age;
//
//        @Override
//        public URI getUri() {
//            String path = Person.class.getAnnotation(RemoteResource.class).value();
//            return UriComponentsBuilder.fromUri(config.getBaseUri()).path(path).build().toUri();
//        }
//
//        @Override
//        public Person getEntity() {
//            return new Person()
//        }
//    }
    
//    private <T> void deleteAll(Class<T> entityType) {
//        final Client<T> repo = factory.create(entityType);
//        repo.getAll().forEach(entity -> repo.delete(entity.getId()));
//    }
    
    @Test
    public void testPost() {
        final Client<Person2> person2Repo = factory.create(Person2.class);
        
        Person2 expected1 = new Person2(config.getBaseUri(), "Bryce", "Anson", 10);
        URI uri = expected1.post(config.getBaseUri(), restTemplate);
        assertThat(uri.getPath()).isEqualTo("/people/Bryce:Anson");

//        Person2 expected2 = new Person2(config.getBaseUri(), "Joe", "Doe", 5);
//        ResponseEntity<Person2> response = expected2.post(config.getBaseUri(), restTemplate);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody()).isEqualTo(expected2);
        
        List<Person2> all = new ArrayList<>();
        person2Repo.getAll().forEach(all::add);
        assertThat(all).containsExactly(expected1);
    }
    
    @Test
    public void deleteNonExistantEntity() {
        final Client<Person2> person2Repo = factory.create(Person2.class);
        assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> {
            Person2 notPresent = new Person2(config.getBaseUri(), "John", "Doe", 81);
            person2Repo.delete(notPresent.getUri());
        });
    }

    @Test
    public void testGet2() {
        final Client<Person2> person2Repo = factory.create(Person2.class);
        Person2 expected = new Person2(config.getBaseUri(), "Bryce", "Anson", 10);
        
        person2Repo.delete(expected.getUri());
        person2Repo.post(expected);
        
        List<Person2> all = new ArrayList<>();
        person2Repo.getAll().forEach(all::add);
        assertThat(all).contains(expected);
    }

    @Test
    public void testGet() {
        Person expected = new Person(config, "Bryce", "Anson", 10);

        List<Person> all = new ArrayList<>();
        personRepo.getAll().forEach(all::add);
        assertThat(all).containsExactly(expected);
        {
            URI uri = expected.getUri(config, "Bryce:Anson");
            Person actual = personRepo.get(uri);
            assertThat(actual).isEqualTo(expected);
        }
    }

    /**
     * I don't understand the purpose of this method. It hits {@link http://localhost:8080/people} (equivalent to {@link Client#getAll()})
     * and places the response in a Person proxy. That ends up yielding odd behavior from the proxy. This test is capturing some of the odd
     * behaviors.
     */
    @Test
    public void testInvalidGet() {
        Person actual = personRepo.get(); //
        assertThat(actual).isNotNull();
        // getAge() returns null because this field doesn't exist in the top-level result
        assertThat(actual.getAge()).isNull();
        // getId() returns the result's self link
        assertThatIllegalArgumentException().isThrownBy(() -> actual.getId())
            .withMessage("Illegal character in path at index 28: http://localhost:8080/people{?page,size,sort}");
    }
}
