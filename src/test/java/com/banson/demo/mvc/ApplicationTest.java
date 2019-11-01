package com.banson.demo.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.banson.demo.Application;
import com.banson.demo.model.entity.CollectionNode;
import com.banson.demo.model.entity.Node;
import com.banson.demo.model.entity.Person;
import com.banson.demo.model.repo.People;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class ApplicationTest {
//    @EntityScan(basePackageClasses = { CollectionNode.class })
//    @EnableJpaRepositories(basePackageClasses = { People.class })
//    @SpringBootConfiguration
//    public static class Configuration {}

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private People people;

    @Test
    public void contextLoads() throws Exception {}

    @Transactional
    @Test
    public void peopleTest() {
        List<Person> personList = IntStream.range(0, 10).mapToObj(i -> Person.newRandom()).collect(Collectors.toList());
        Person boss = personList.get(0);
        personList.subList(1, 4).forEach(person -> person.reportsTo(personList.get(4)));
        personList.get(4).reportsTo(boss);
        personList.subList(5, 10).forEach(person -> person.reportsTo(boss));
        personList.forEach(people::save);
        people.flush();
        assertThat(people.count()).isEqualTo(personList.size());
        Person bigBoss = people.findById(boss.getId()).get();
        people.delete(bigBoss);
        people.flush();
        assertThat(people.count()).isEqualTo(0);
    }

    @Transactional
    @Test
    public void emTest() {
        CollectionNode a = CollectionNode.of("a");
        CollectionNode b = CollectionNode.of("b");
        CollectionNode c = CollectionNode.of("c");
        a.linkTo(b);
        a.linkTo(c);
        em.persist(a);
        assertThat(Stream.of(a, b, c)).allMatch(em::contains);
        em.remove(a);
    }

    @Transactional
    @Test
    public void nodeTest() {
        Node a = Node.of("a");
        Node b = Node.of("b");
        Node c = Node.of("c");
        a.linkTo(b);
        a.linkTo(c);
        em.persist(a);
        assertThat(Stream.of(a, b, c)).allMatch(em::contains);
        em.remove(a);
    }

}
