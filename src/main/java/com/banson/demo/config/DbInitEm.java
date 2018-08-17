package com.banson.demo.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.banson.demo.model.entity.NodePk;

@Component
public class DbInitEm implements CommandLineRunner {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
//        CollectionNode a = CollectionNode.of("a");
//        CollectionNode b = CollectionNode.of("b");
//        CollectionNode c = CollectionNode.of("c");
//        a.linkTo(b);
//        a.linkTo(c);
//        em.persist(a);
        
        NodePk a = NodePk.of("a");
        NodePk b = NodePk.of("b");
        NodePk c = NodePk.of("c");
        a.linkTo(b);
        a.linkTo(c);
        em.persist(a);
    }
}
