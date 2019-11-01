package com.banson.demo.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.banson.demo.model.entity.Person;
import com.banson.demo.model.repo.People;

@Component
public class DbInitEm implements CommandLineRunner {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private People people;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
//        CollectionNode a = CollectionNode.of("a");
//        CollectionNode b = CollectionNode.of("b");
//        CollectionNode c = CollectionNode.of("c");
//        a.linkTo(b);
//        a.linkTo(c);
//        em.persist(a);

        Person bryce = Person.of("Bryce Anson", 10);
        Person yasir = Person.of("Yasir M", 10);
        Person jay = Person.of("Jay Harkar", 11);
        Person travis = Person.of("Travis Bean", 12);
        jay.manages(bryce);
        jay.manages(yasir);
        travis.manages(jay);
        em.persist(bryce);

//        List<Person> personList = IntStream.range(0, 10).mapToObj(i -> Person.newRandom()).collect(Collectors.toList());
//        Person boss = personList.get(0);
//        personList.subList(1, 4).forEach(person -> person.reportsTo(personList.get(4)));
//        personList.get(4).reportsTo(boss);
//        personList.subList(5, 10).forEach(person -> person.reportsTo(boss));
//        personList.forEach(people::save);
    }
}
