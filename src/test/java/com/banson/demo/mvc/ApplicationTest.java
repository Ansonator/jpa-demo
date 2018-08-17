package com.banson.demo.mvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.banson.demo.config.Application;
import com.banson.demo.model.entity.CollectionNode;
import com.banson.demo.model.entity.Node;
import com.banson.demo.model.entity.NodeId;
import com.banson.demo.model.repo.NodeRepo;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {Application.class})
@Slf4j
public class ApplicationTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private NodeRepo people;

    @Test
    public void contextLoads() throws Exception {
    }
    
    @Transactional
    @Test
    public void peopleTest() {
        Node a = Node.of("a");
        Node b = Node.of("b");
        Node c = Node.of("c");
        a.linkTo(b);
        a.linkTo(c);
        a = people.save(a);
        b = people.save(b);
        c = people.save(c);
        people.flush();
        log.info("{}", Stream.of(a, b, c).map(e -> em.contains(e)).collect(Collectors.toList()));
        b = people.findById(NodeId.of('a')).get();
        people.delete(b);
        people.flush();
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
        log.info("{}", Stream.of(a, b, c).map(e -> em.contains(e)).collect(Collectors.toList()));
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
        log.info("{}", Stream.of(a, b, c).map(e -> em.contains(e)).collect(Collectors.toList()));
        em.remove(a);
    }

}