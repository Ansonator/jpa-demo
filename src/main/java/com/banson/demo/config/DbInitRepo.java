package com.banson.demo.config;
//package com.banson.demo.mvc;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.banson.demo.model.entity.Node;
//import com.banson.demo.model.repo.People;
//
//@Component
//public class DbInitRepo implements CommandLineRunner {
//    @Autowired
//    private People people;
//
//    @Transactional
//    @Override
//    public void run(String... args) throws Exception {
//        Node a = Node.of("a");
//        Node b = Node.of("b");
//        Node c = Node.of("c");
//        a.linkTo(b);
//        a.linkTo(c);
//        people.save(a);
//    }
//}
