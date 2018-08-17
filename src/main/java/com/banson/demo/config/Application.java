package com.banson.demo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.banson.demo.model.entity.CollectionNode;
import com.banson.demo.model.repo.NodeRepo;

@SpringBootApplication
@EntityScan(basePackageClasses = { CollectionNode.class })
@EnableJpaRepositories(basePackageClasses = { NodeRepo.class })
//@ComponentScan(basePackageClasses = { Application.class, Node.class, People.class })
public class Application {// extends SpringBootServletInitializer {
//    @PersistenceContext
//    private EntityManager em;
    
    @SpringBootConfiguration
    public static class Configuration {
        
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }

//    @Bean
//    @Transactional
//    public CommandLineRunner commandLineRunner(NodeRepo nodeRepo) {
//        return args -> {
//            Node a = Node.of("a");
//            Node b = Node.of("b");
//            Node c = Node.of("c");
//            a.linkTo(b);
//            a.linkTo(c);
//            nodeRepo.save(a);
//        };
//    }
    
//    @Bean
//    @Transactional
//    public CommandLineRunner nodePkInit(NodePkRepo nodes) {
//        return args -> {
//            NodePk a = NodePk.of("a");
//            NodePk b = NodePk.of("b");
//            NodePk c = NodePk.of("c");
//            a.linkTo(b);
//            a.linkTo(c);
//            assert(!em.contains(a));
//            EdgePk edge = a.getOutgoing().iterator().next();
//            assert(!em.contains(edge));
//            nodes.save(a);
//        };
//    }
}
