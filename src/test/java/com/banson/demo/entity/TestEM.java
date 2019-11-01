package com.banson.demo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.banson.demo.Application;
import com.banson.demo.model.entity.Person;
import com.banson.demo.model.entity.PersonId;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class TestEM {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PlatformTransactionManager txnMgr;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void testManualTxn() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(txnMgr);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Person bryce = em.find(Person.class, PersonId.of("Bryce", "Anson"));
                assertThat(bryce).isNotNull();
                assertThat(bryce.getAge()).isGreaterThan(0);
            }
        });
    }

    @Test
    public void testManualTxn2() {
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction txn1 = em2.getTransaction();
        txn1.begin();
        Person joe = Person.of("Joe Blow", 5);
        assertThat(!em2.contains(joe));
        em2.persist(joe);
        assertThat(em2.contains(joe));
        txn1.commit();
        assertThat(em2.contains(joe));
        em2.close();
        final Person finalJoe = joe;
        assertThatThrownBy(() -> em2.contains(finalJoe)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("EntityManager is closed");

        EntityManager em3 = emf.createEntityManager();
        em3.getTransaction().begin();
        assertThatIllegalArgumentException().isThrownBy(() -> em3.remove(finalJoe)).withMessageStartingWith("Removing a detached instance");
        assertThatIllegalArgumentException().isThrownBy(() -> em3.refresh(finalJoe)).withMessage("Entity not managed");
        joe = em3.find(Person.class, finalJoe.getId());
        assertThat(em3.contains(joe));
        em3.remove(joe);
        final EntityTransaction txn2 = em3.getTransaction();
        assertThatThrownBy(() -> txn2.commit()).isInstanceOf(RollbackException.class);
        em3.close();

        EntityManager em4 = emf.createEntityManager();
        em4.getTransaction().begin();
        joe = em4.find(Person.class, finalJoe.getId());
        em4.remove(joe);
        em4.getTransaction().commit();
        em4.close();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.select(root);
        Stream<Person> all = em.createQuery(cq).getResultList().stream();
        assertThat(all.filter(p -> p.getId().getFirstName().equals("Joe") && p.getId().getLastName().equals("Blow")).count()).isEqualTo(0L);
    }

    @Test(expected = IllegalStateException.class)
    public void testManualTxnWrong() {
        EntityTransaction txn = em.getTransaction();
        txn.begin();
        txn.commit();
    }
}
